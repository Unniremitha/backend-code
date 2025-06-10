package IKM.KSMART.SCHOOL.service;

import IKM.KSMART.SCHOOL.contract.*;
import IKM.KSMART.SCHOOL.model.User;
import IKM.KSMART.SCHOOL.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
@Service
@AllArgsConstructor
public class KycService implements KycServiceImpl {
    private static final String OTP = "12345";
    private final UserRepository userRepository;
    private final String UPLOAD_DIR = "D:\\uploads";
    private final ModelMapper modelMapper;

    public KycResponse verifyKyc(KycRequest request) {

        Optional<User> userOpt = Optional.empty();

        if (request.getPhoneNumber() != null && !request.getPhoneNumber().isEmpty()) {
            userOpt = userRepository.findByPhoneNumber(request.getPhoneNumber());
        } else if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            userOpt = userRepository.findByEmail(request.getEmail());
        } else {
            return new KycResponse("Phone number or email is required", null);
        }

        if (userOpt.isEmpty()) {
            return new KycResponse("User not found", null);
        }

        User user = userOpt.get();

        if (Boolean.TRUE.equals(user.getIsKycVerified())) {
            return new KycResponse("KYC already completed", user.getAadhaarNo());
        }

        if (!OTP.equals(request.getOtp())) {
            return new KycResponse("Invalid OTP.", null);
        }

        user.setAadhaarNo(request.getAadhaarNo());
        user.setIsKycVerified(true);
        user.setIsActive(true);
        user.setIsFirstLogin(true);
        user.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        userRepository.save(user);

        return new KycResponse("KYC verification successful", user.getAadhaarNo());
    }

    public String handlePassportUpload(MultipartFile file,String email) {
        if (file == null || file.isEmpty()) {
            return "No file uploaded";
        }

        try {
            // Ensure upload directory exists
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String filePath = UPLOAD_DIR + file.getOriginalFilename();
            file.transferTo(new File(filePath));

            Optional<User> userOpt = userRepository.findByEmail(email);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                user.setIsDocumentVerified(true);
                user.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                userRepository.save(user);
            } else {
                return "User with email " + email + " not found";
            }

            return "Document uploaded successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "File upload failed: " + e.getMessage();
        }
    }

    public DocumentResponse saveDcoument(DocumentRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isPresent()) {
            User existingUser = userOpt.get();

            // Update specific document fields only (e.g., documentNo, passportFile, etc.)
            existingUser.setDocumentNo(request.getDocumentNo());
            existingUser.setName(request.getName());
            existingUser.setDob(request.getDob());
            existingUser.setGender(request.getGender());
            existingUser.setIsActive(true);
            existingUser.setIsKycVerified(true);
            userRepository.save(existingUser);

            return new DocumentResponse("Document saved successfully for user: " + request.getEmail());
        } else {
            return new DocumentResponse("No user found with the given email.");
        }
    }
}

