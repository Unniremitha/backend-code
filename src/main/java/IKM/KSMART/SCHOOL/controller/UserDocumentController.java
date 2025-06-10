package IKM.KSMART.SCHOOL.controller;

import IKM.KSMART.SCHOOL.contract.UserDocumentRequest;
import IKM.KSMART.SCHOOL.contract.UserDocumentResponse;
import IKM.KSMART.SCHOOL.service.UserDocumentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/document")
public class UserDocumentController {

    private final UserDocumentService userDocumentService;

    @PostMapping("/save-user-document")
    public UserDocumentResponse saveDocument(@RequestBody UserDocumentRequest request){
        return userDocumentService.saveDocument(request);
    }

    @PostMapping("/upload-document")
    public ResponseEntity<String> uploadDocument(@RequestParam("file") MultipartFile file, String email,String phoneNumber) {
        String response = userDocumentService.handleOtherDocumentUpload(file,email,phoneNumber);
        return ResponseEntity.ok(response);
    }

}
