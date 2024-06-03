package com.encryption.controller;

import com.encryption.service.EncryptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EncryptionController {
    private final Logger logger = LoggerFactory.getLogger(EncryptionController.class);

    public EncryptionController() {
    }

    @RequestMapping(value="/encrypt", method= RequestMethod.POST)
    public ResponseEntity<String> encryptString(@RequestBody String toEncrypt, @RequestHeader String password) {
        try {
            return new ResponseEntity<String>(EncryptionUtil.encrypt(toEncrypt, password), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value="/decrypt", method= RequestMethod.POST)
    public ResponseEntity<String> decryptString(@RequestBody String toDecrypt, @RequestHeader String password) {
        try {
            return new ResponseEntity<String>(EncryptionUtil.decrypt(toDecrypt, password), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
