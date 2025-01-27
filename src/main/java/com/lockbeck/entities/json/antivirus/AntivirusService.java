package com.lockbeck.entities.json.antivirus;

import com.lockbeck.demo.Response;
import com.lockbeck.entities.json.JsonEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.DTD;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AntivirusService {
    private final AntivirusRepository antivirusRepository;

    public void create(List<AntivirusCreateRequest> requests, JsonEntity save) {
        if(requests.isEmpty()) return;
        for (AntivirusCreateRequest request : requests) {
            Antivirus antivirus = new Antivirus();
            antivirus.setName(request.getName());
            antivirus.setStatus(request.getStatus());
            antivirus.setUpdatedAt(request.getUpdatedAt());
            antivirus.setJson(save);
            antivirusRepository.save(antivirus);
        }
    }

    public List<AntivirusDTO> getList(Integer jsonId ) {
        List<Antivirus> byJsonId = antivirusRepository.findByJsonId(jsonId);
        List<AntivirusDTO> dtos = new ArrayList<>();
        byJsonId.forEach(entity -> {
            AntivirusDTO build = AntivirusDTO.builder()
                    .name(entity.getName())
                    .status(entity.getStatus())
                    .updatedAt(entity.getUpdatedAt())
                    .id(entity.getId())
                    .build();
            dtos.add(build);
        });
        return dtos;
    }

    public void delete(Antivirus antivirus) {
        antivirus.setJson(null);
        antivirusRepository.delete(antivirus);
    }
}
