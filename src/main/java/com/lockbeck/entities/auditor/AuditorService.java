package com.lockbeck.entities.auditor;

import com.lockbeck.demo.Response;
import com.lockbeck.entities.audit.AuditDTO;
import com.lockbeck.entities.audit.AuditEntity;
import com.lockbeck.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuditorService {
    private final AuditorRepository repository;
    private final ModelMapper modelMapper;

    public AuditorEntity get(Integer auditorId) {
        Optional<AuditorEntity> byId = repository.findById(auditorId);
        if (byId.isEmpty()) {
            throw new NotFoundException("Tashkilot topilmadi id: " + auditorId);
        }
        return byId.get();

    }

    public Response create(AuditorCreateRequest request) {
        repository.save(modelMapper.map(request, AuditorEntity.class));
        return new Response();
    }

    public List<AuditorDTO> list() {
        List<AuditorDTO> list = new ArrayList<>();
        for (AuditorEntity auditorEntity : repository.findAll()) {
            list.add(modelMapper.map(auditorEntity, AuditorDTO.class));
        }
        return list;
    }

    public List<AuditDTO> getAudits(Integer id) {
        List<AuditDTO> list = new ArrayList<>();
        AuditorEntity auditor = get(id);
        for (AuditEntity audit : auditor.getAudits()) {
            AuditDTO dto = new AuditDTO();
            dto.setId(audit.getId());
            dto.setStatus(audit.getStatus());
            //to do rest
            list.add(dto);
        }
        return list;
    }

    public AuditorDTO getAuditor(AuditorEntity auditor) {
        return modelMapper.map(auditor, AuditorDTO.class);
    }
}
