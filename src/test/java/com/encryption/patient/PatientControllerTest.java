package com.encryption.patient;

import com.encryption.controller.EncryptionController;
import com.encryption.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class PatientControllerTest {

    public EncryptionController patientController = new EncryptionController();
    private final Logger logger = LoggerFactory.getLogger(EncryptionController.class);

    @Test
    public void testForInterview() {


    }

}