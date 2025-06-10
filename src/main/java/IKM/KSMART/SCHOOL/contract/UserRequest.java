package IKM.KSMART.SCHOOL.contract;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
public class UserRequest {
    private Boolean countryType;
    @Pattern(regexp = "(^$|\\+?[0-9]{10})", message = "Invalid phone number")
    private String phoneNumber;

    @NotNull
    private String otp;
    private String email;
}
