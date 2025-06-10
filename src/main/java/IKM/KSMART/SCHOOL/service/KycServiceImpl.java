package IKM.KSMART.SCHOOL.service;

import org.springframework.web.multipart.MultipartFile;

public interface KycServiceImpl {
    String handlePassportUpload(MultipartFile file,String email);
}
