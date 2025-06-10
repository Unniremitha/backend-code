package IKM.KSMART.SCHOOL.contract;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Data
@Setter
@Getter
@AllArgsConstructor
public class RegisterResponse {

    private String message;
    private String userId;
}
