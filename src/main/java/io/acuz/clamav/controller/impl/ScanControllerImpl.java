package io.acuz.clamav.controller.impl;

import io.acuz.clamav.client.ClamAvClient;
import io.acuz.clamav.client.dto.ScanResult;
import io.acuz.clamav.controller.ScanController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Validated
@RestController
public class ScanControllerImpl implements ScanController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScanControllerImpl.class);

    private final ClamAvClient client;

    public ScanControllerImpl(ClamAvClient client) {
        this.client = client;
    }

    @Override
    public ResponseEntity<ScanResult> scan(@RequestParam("file") MultipartFile upload) {
        try {
            if (null == upload || upload.getInputStream().available() == 0)
                throw new IOException("Invalid request, empty payload");

            var inputStream = upload.getInputStream();

            return ResponseEntity.ok(client.scan(inputStream));
        } catch (IOException e) {
            LOGGER.error("Could not complete scan request", e);
            return ResponseEntity.badRequest().body(ScanResult.FAILED);
        }
    }
}
