package ar.com.ultrafibra.webultrafibra.controller;

import ar.com.ultrafibra.webultrafibra.response.ResponseRest;
import ar.com.ultrafibra.webultrafibra.service.serviceImpl.FormServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = {
    "*"})
@RequestMapping("/api/gr/form")
public class FormController {

    @Autowired
    private FormServiceImpl formService;

    @PostMapping(path = "/send")
    public ResponseEntity<ResponseRest> send(@RequestParam("affair") String affair, @RequestParam("body") String body) throws Exception {
        return formService.submitForm(affair, body);
    }

    @PostMapping(path = "/send-cv")
    public ResponseEntity<ResponseRest> sendCv(@RequestParam("affair") String affair, @RequestParam("body") String body, @RequestParam("file") MultipartFile file) throws Exception {
        return formService.submitForm(affair, body, file);
    }
}
