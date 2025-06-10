package IKM.KSMART.SCHOOL.contract;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserDocumentRequest {
    private String phoneNumber;
    private String email;
    private String documentNumber;
    private String documentName;
    private String documentType;
}
