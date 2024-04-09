package io.acuz.clamav.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("${clamav.api.base}/admin")
public interface AdminController {
    @GetMapping(value = "/reload", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Map<String, Object>> reload();

    @GetMapping(value = "/shutdown", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Map<String, Object>> shutdown();
}
