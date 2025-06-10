package IKM.KSMART.SCHOOL.contract;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class RegisterRequest {
    private Boolean countryType;
    @Pattern(regexp = "(^$|\\+?[0-9]{10})", message = "Invalid phone number")
    private String phoneNumber;

    @NotNull
    private String otp;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    private String aadhaarNo;
    private String name;
    private String dob;
    private String gender;
}
