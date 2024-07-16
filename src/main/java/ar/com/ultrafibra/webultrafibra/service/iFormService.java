package ar.com.ultrafibra.webultrafibra.service;

import ar.com.ultrafibra.webultrafibra.response.ResponseRest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


public interface iFormService {
    
    public ResponseEntity<ResponseRest> submitForm(String affair, String Body);
    public ResponseEntity<ResponseRest> submitForm(String affair, String Body, MultipartFile file);
}
