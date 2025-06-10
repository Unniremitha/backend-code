package IKM.KSMART.SCHOOL.repository;

import IKM.KSMART.SCHOOL.model.UserDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface UserDocumentRepository extends JpaRepository<UserDocument, UUID> {
}
