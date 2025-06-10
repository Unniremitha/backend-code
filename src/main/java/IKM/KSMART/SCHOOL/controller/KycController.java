package IKM.KSMART.SCHOOL.controller;

import IKM.KSMART.SCHOOL.contract.*;
import IKM.KSMART.SCHOOL.service.KycService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
public class KycController {

    private final KycService kycService;

    @PostMapping("/verify")
    public KycResponse verifyKyc(@RequestBody @Valid KycRequest request) {
        return kycService.verifyKyc(request);
    }

    @PostMapping("/upload-passport")
    public ResponseEntity<String> uploadPassport(@RequestParam("file") MultipartFile file,String email) {
        String response = kycService.handlePassportUpload(file,email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/save-document")
    public DocumentResponse saveDcoumet(@RequestBody DocumentRequest request){
        return kycService.saveDcoument(request);
    }


}
