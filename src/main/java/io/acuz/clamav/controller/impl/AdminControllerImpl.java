package io.acuz.clamav.controller.impl;

import io.acuz.clamav.client.ClamAvClient;
import io.acuz.clamav.controller.AdminController;
import io.acuz.clamav.controller.UtilController;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Validated
@RestController
public class AdminControllerImpl implements AdminController {
    private final ClamAvClient client;

    public AdminControllerImpl(ClamAvClient client) {
        this.client = client;
    }

    @Override
    public ResponseEntity<Map<String, Object>> reload() {
        if (this.client.reload()) {
            return ResponseEntity.ok(Map.of("ClamAV", "Reloading database"));
        }

        return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<Map<String, Object>> shutdown() {
        if (this.client.shutdown()) {
            return ResponseEntity.ok(Map.of("ClamAV", "Shutting down"));
        }

        return ResponseEntity.badRequest().build();
    }
}
