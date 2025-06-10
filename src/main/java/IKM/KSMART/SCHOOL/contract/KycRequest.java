package IKM.KSMART.SCHOOL.contract;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class KycRequest {
    private String email;
    private String phoneNumber;
    @NotNull
    @Pattern(
            regexp = "^[0-9]{12,16}$",
            message = "Aadhaar number must be a 12-digit numeric value/16-digit Virtual ID")
    private String aadhaarNo;

    @NotNull(message = "OTP value cannot be null")
    private String otp;
    private Boolean isKycVerified;

}
