package com.email.emailGenerator.Controller;

import com.email.emailGenerator.EmailRequest;
import com.email.emailGenerator.Services.emailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@CrossOrigin(origins = "*")
public class Controller {
@Autowired
    private emailService emailservice;
    @PostMapping("/generate")
    public ResponseEntity<?>generate(@RequestBody EmailRequest emailreq){
        String response= emailservice.generateReply(emailreq);
return ResponseEntity.ok(response);
    }
}
