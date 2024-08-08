package com.lockbeck.entities.auditor;

import com.lockbeck.demo.Response;
import com.lockbeck.entities.audit.AuditDTO;
import com.lockbeck.entities.audit.AuditEntity;
import com.lockbeck.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuditorService {
    private final AuditorRepository repository;
    private final ModelMapper modelMapper;

    public Response create(AuditorCreateRequest request) {
        repository.save(modelMapper.map(request, AuditorEntity.class));
        return new Response();
    }

    public Response update(AuditorUpdateRequest request) {
        AuditorEntity auditor = get(request.getId());
        auditor.setName(request.getName());
        auditor.setEmail(request.getEmail());
        auditor.setPhone(request.getPhone());
        repository.save(auditor);

        return new Response();
    }

    public Response list() {
        List<AuditorDTO> list = new ArrayList<>();
        for (AuditorEntity auditorEntity : repository.findAll()) {
            list.add(modelMapper.map(auditorEntity, AuditorDTO.class));
        }
        return new Response(200,"success", list);
    }

    public Response getAudits(Integer id) {
        List<AuditDTO> list = new ArrayList<>();
        AuditorEntity auditor = get(id);
        for (AuditEntity audit : auditor.getAudits()) {
            AuditDTO dto = new AuditDTO();
            dto.setId(audit.getId());
            dto.setStatus(audit.getStatus());
            //to do rest
            list.add(dto);
        }
        return new Response(200,"success", list);
    }

    public Response getById(Integer id) {
        AuditorEntity auditor = get(id);
        AuditorDTO dto = getAuditor(auditor);
        return new Response(200,"success", dto);
    }

    public List<AuditorDTO> getAuditors(Set<AuditorEntity> auditors) {
        List<AuditorDTO> list = new ArrayList<>();
        auditors.forEach(auditorEntity -> list.add(getAuditor(auditorEntity)));
        return list;

    }

    public AuditorDTO getAuditor(AuditorEntity auditor) {
        return modelMapper.map(auditor, AuditorDTO.class);
    }

    public AuditorEntity get(Integer auditorId) {
        Optional<AuditorEntity> byId = repository.findById(auditorId);
        if (byId.isEmpty()) {
            throw new NotFoundException("Tashkilot topilmadi id: " + auditorId);
        }
        return byId.get();

    }
}
