package io.acuz.clamav.controller.impl;

import io.acuz.clamav.client.ClamAvClient;
import io.acuz.clamav.controller.UtilController;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Validated
@RestController
public class UtilControllerImpl implements UtilController {
    private final ClamAvClient client;

    public UtilControllerImpl(ClamAvClient client) {
        this.client = client;
    }

    @Override
    public ResponseEntity<Map<String, Object>> ping() {
        if (this.client.ping()) {
            return ResponseEntity.ok(Map.of("ClamAV", "Alive"));
        }

        return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<Map<String, Object>> version() {
        return ResponseEntity.ok(Map.of("ClamAV", this.client.version()));
    }

    @Override
    public ResponseEntity<Map<String, Object>> stats() {
        return ResponseEntity.ok(
                Map.of("ClamAV", this.client.stats())
        );
    }
}
