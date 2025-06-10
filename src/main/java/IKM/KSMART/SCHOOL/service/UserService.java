package IKM.KSMART.SCHOOL.service;

import IKM.KSMART.SCHOOL.contract.LoginResponse;
import IKM.KSMART.SCHOOL.contract.RegisterRequest;
import IKM.KSMART.SCHOOL.contract.UserRequest;
import IKM.KSMART.SCHOOL.contract.RegisterResponse;
import IKM.KSMART.SCHOOL.model.User;
import IKM.KSMART.SCHOOL.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final String OTP = "12345";

    public LoginResponse login(UserRequest request) {
        if (request.getPhoneNumber() != null && !request.getPhoneNumber().isBlank()) {
            Optional<User> userByPhone = userRepository.findByPhoneNumber(request.getPhoneNumber());
       
            if (userByPhone.isPresent()) {
                User user = userByPhone.get();

                if (!OTP.equals(request.getOtp())) {
                    return new LoginResponse("Invalid OTP. Access denied.");
                }

                if (Boolean.TRUE.equals(user.getIsKycVerified())) {
                    if (!Boolean.TRUE.equals(user.getIsFirstLogin())) {
                        user.setIsFirstLogin(true);
                        userRepository.save(user);
                    }
                    return new LoginResponse("Login successful with phone number");
                } else {
                    return new LoginResponse("KYC not completed. Please verify your Aadhaar first.");
                }
            } else {
                return new LoginResponse("User not found. Please register.");
            }
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            Optional<User> userByEmail = userRepository.findByEmail(request.getEmail());

            if (userByEmail.isPresent()) {
                User user = userByEmail.get();

                if (!OTP.equals(request.getOtp())) {
                    return new LoginResponse("Invalid OTP. Access denied.");
                }

                if (Boolean.TRUE.equals(user.getIsKycVerified())) {
                    if (!Boolean.TRUE.equals(user.getIsFirstLogin())) {
                        user.setIsFirstLogin(true);
                        userRepository.save(user);
                    }
                    return new LoginResponse("Login successful with email");
                } else {
                    return new LoginResponse("KYC not completed. Please verify your Aadhaar first.");
                }
            } else {
                return new LoginResponse("User not found. Please register.");
            }
        }

        return new LoginResponse("Phone number or email must be provided.");
    }


    public RegisterResponse register(RegisterRequest request) {

        boolean isUser = Boolean.TRUE.equals(request.getCountryType());
        String identifier = isUser ? request.getPhoneNumber() : request.getEmail();

        Optional<User> existingUser = isUser
                ? userRepository.findByPhoneNumber(request.getPhoneNumber())
                : userRepository.findByEmail(request.getEmail());

        if (existingUser.isPresent()) {
            return new RegisterResponse("User already registered", identifier);
        }

        if (!OTP.equals(request.getOtp())) {
            return new RegisterResponse("Invalid OTP. Access denied.", null);
        }

        User user = modelMapper.map(request, User.class);
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        userRepository.save(user);

        return new RegisterResponse("User Account Created", identifier);
    }

}

