package IKM.KSMART.SCHOOL.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name="user_document")
public class UserDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String documentNumber;
    private String documentName;
    private String documentType;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}

