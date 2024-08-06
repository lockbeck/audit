package com.lockbeck.entities.json.antivirus;

import com.lockbeck.demo.Response;
import com.lockbeck.entities.json.JsonEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AntivirusService {
    private final AntivirusRepository antivirusRepository;

    public void create(List<AntivirusCreateRequest> requests, JsonEntity save) {
        for (AntivirusCreateRequest request : requests) {
            Antivirus antivirus = new Antivirus();
            antivirus.setName(request.getName());
            antivirus.setStatus(request.getStatus());
            antivirus.setUpdatedAt(request.getUpdatedAt());
            antivirus.setJson(save);
            antivirusRepository.save(antivirus);
        }
    }
}
