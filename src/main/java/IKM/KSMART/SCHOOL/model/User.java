package IKM.KSMART.SCHOOL.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
@Data
@Entity
@Getter
@Setter
@Table(name="k_smart_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;

    private Integer tenantId;
    private String name;
    private Boolean isActive;
    private Boolean isKycVerified = Boolean.FALSE;
    private String aadhaarNo;
    private Boolean isFirstLogin;

    @Email(message = "Invalid email format")
    private String email;
    private String documentNo;
    private String whatsappNumber;
    private String userType;
    private Long regNo;
    private Timestamp createdAt;
    private Boolean isDocumentVerified;
    private Timestamp updatedAt;
    private String dob;
    private String gender;
    private Boolean countryType = Boolean.TRUE;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private UserDocument userDocument;
}
