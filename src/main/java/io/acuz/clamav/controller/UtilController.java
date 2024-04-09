package io.acuz.clamav.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("${clamav.api.base}/util")
public interface UtilController {
    @GetMapping(value = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Map<String, Object>> ping();

    @GetMapping(value = "/version", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Map<String, Object>> version();

    @GetMapping(value = "/stats", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Map<String, Object>> stats();
}
