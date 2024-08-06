package com.lockbeck;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lockbeck.entities.json.JsonCreateRequest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor
class AuditApplicationTests {

    @Autowired
    private ObjectMapper objectMapper;

	@Test
	void contextLoads() throws JsonProcessingException {
		JsonCreateRequest jsonCreateRequest = objectMapper.readValue("jsons/JAHONGIR 00-E0-4C-36-0B-08 (4).json", JsonCreateRequest.class);
		System.out.println(jsonCreateRequest);

	}
}
