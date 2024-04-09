package io.acuz.clamav.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.acuz.clamav.client.dto.ScanResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Base64;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

@SpringBootTest
@EnableWebMvc
@AutoConfigureMockMvc
class ScanControllerImplTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testUpload_BadStream() throws Exception {
        var file = new MockMultipartFile(
                "file",
                "text.txt",
                MediaType.TEXT_PLAIN_VALUE,
                new byte[]{});

        var result = this.mockMvc.perform(multipart("/scan").file(file)).andReturn();

        assertEquals(400, result.getResponse().getStatus());

        var r = objectMapper.readValue(result.getResponse().getContentAsByteArray(), ScanResult.class);
        assertEquals(ScanResult.ScanStatus.ERROR, r.getStatus());
        assertEquals(Map.of(), r.getResults());
    }

    @Test
    void testUpload_Virus_Found() throws Exception {
        var base64encoded = "WDVPIVAlQEFQWzRcUFpYNTQoUF4pN0NDKTd9JEVJQ0FSLVNUQU5EQVJELUFOVElWSVJVUy1URVNULUZJTEUhJEgrSCo=";

        var file = new MockMultipartFile(
                "file",
                "text.txt",
                MediaType.TEXT_PLAIN_VALUE,
                Base64.getDecoder().decode(base64encoded));

        var result = this.mockMvc.perform(multipart("/scan").file(file)).andReturn();

        assertEquals(200, result.getResponse().getStatus());

        var r = objectMapper.readValue(result.getResponse().getContentAsByteArray(), ScanResult.class);
        assertEquals(ScanResult.ScanStatus.VIRUS_FOUND, r.getStatus());
        Map<String, Set<String>> results = r.getResults();
        assertEquals(1, results.size());
        assertEquals(Set.of("Win.Test.EICAR_HDB-1"), results.get("stream"));
    }
}
