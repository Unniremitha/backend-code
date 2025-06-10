package IKM.KSMART.SCHOOL.service;

import IKM.KSMART.SCHOOL.contract.UserDocumentRequest;
import IKM.KSMART.SCHOOL.contract.UserDocumentResponse;
import IKM.KSMART.SCHOOL.model.User;
import IKM.KSMART.SCHOOL.model.UserDocument;
import IKM.KSMART.SCHOOL.repository.UserDocumentRepository;
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
public class UserDocumentService {
    private final UserDocumentRepository userDocumentRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final String UPLOAD_DIR = "D:\\uploads";

    public UserDocumentResponse saveDocument(UserDocumentRequest request) {
        Optional<User> userOpt = Optional.empty();

        if (request.getPhoneNumber() != null && !request.getPhoneNumber().isBlank()) {
            userOpt = userRepository.findByPhoneNumber(request.getPhoneNumber());
        } else if (request.getEmail() != null && !request.getEmail().isBlank()) {
            userOpt = userRepository.findByEmail(request.getEmail());
        }
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found ");
        }

        User user = userOpt.get();

        if (Boolean.TRUE.equals(user.getIsDocumentVerified())) {
            UserDocumentResponse response = new UserDocumentResponse();
            response.setMessage("Document already verified. Upload not allowed.");
            return response;
        }

        UserDocument userDocument = new UserDocument();
        userDocument.setDocumentName(request.getDocumentName());
        userDocument.setDocumentNumber(request.getDocumentNumber());
        userDocument.setDocumentType(request.getDocumentType());
        userDocument.setUser(user);
        user.setIsDocumentVerified(true);
        userRepository.save(user);
        userDocumentRepository.save(userDocument);
        UserDocumentResponse response = new UserDocumentResponse();
        response.setMessage("Document saved successfully");
        return response;
    }

    public String handleOtherDocumentUpload(MultipartFile file, String email, String phoneNumber) {
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
            Optional<User> userOpt = Optional.empty();

            if (email != null && !email.isBlank()) {
                userOpt = userRepository.findByEmail(email);
            } else if (phoneNumber != null && !phoneNumber.isBlank()) {
                userOpt = userRepository.findByPhoneNumber(phoneNumber);
            } else {
                return "Email or phone number is required.";
            }

            if (userOpt.isPresent()) {
                User user = userOpt.get();
                user.setIsDocumentVerified(true);
                user.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                userRepository.save(user);
            } else {
                return "User not found";
            }

            return "Document uploaded successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "File upload failed: " + e.getMessage();
        }
    }
}



