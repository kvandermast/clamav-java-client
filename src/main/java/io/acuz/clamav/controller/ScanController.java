package io.acuz.clamav.controller;

import io.acuz.clamav.client.dto.ScanResult;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("${clamav.api.base}/scan")
public interface ScanController {
    @PostMapping(value = { "", "/" }, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ScanResult> scan(@RequestParam("file") MultipartFile upload);
}
