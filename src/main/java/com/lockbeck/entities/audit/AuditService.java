package com.lockbeck.entities.audit;

import com.lockbeck.demo.Response;
import com.lockbeck.entities.auditor.AuditorEntity;
import com.lockbeck.entities.auditor.AuditorService;
import com.lockbeck.entities.contract.ContractService;
import com.lockbeck.entities.file.FileService;
import com.lockbeck.entities.letter.LetterService;
import com.lockbeck.entities.report.ReportService;
import com.lockbeck.entities.subject.SubjectService;
import com.lockbeck.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuditService {
    private final AuditRepository repository;
    private final SubjectService subjectService;
    private final LetterService letterService;
    private final ContractService contractService;
    private final AuditorService auditorService;
    private final ReportService reportService;
    private final FileService fileService;

    public Response create(AuditCreateRequest request) {
        AuditEntity entity = new AuditEntity();
        entity.setSubject(subjectService.get(request.getSubjectId()));
        entity.setInLetter(letterService.get(request.getInLetterId()));
        entity.setStatus(AuditStatus.REQUESTED);
        repository.save(entity);
        return new Response(202, "audit yaratildi", LocalDateTime.now());
    }

    public Response update(AuditUpdateRequest request) {
        AuditEntity audit = get(request.getId());
        switch (audit.getStatus()) {
            case REQUESTED:
                audit.setOutLetter(letterService.get(request.getOutLetterId()));
                audit.setStatus(AuditStatus.OFFERED);
                break;

            case OFFERED:
                audit.setContract(contractService.get(request.getContractId()));
                audit.setStatus(AuditStatus.CONTRACTED);
                break;

            case CONTRACTED:
                audit.setHalfPayment(request.getHalfPayment());
                audit.setHalfPaymentDate(request.getHalfPaymentDate());
                audit.setStatus(AuditStatus.HALF_PAID);
                break;

            case HALF_PAID:
                audit.setAuditStartDate(request.getAuditStartDate());
                audit.setLeader(auditorService.get(request.getLeaderId()));
                Set<AuditorEntity> set = new HashSet<>();
                request.getAuditorIds().forEach(integer -> {
                    AuditorEntity auditor = auditorService.get(integer);
                    set.add(auditor);
                });
                audit.setAuditors(set);
                audit.setListDoc(fileService.get(request.getListDocId()));
                audit.setStatus(AuditStatus.STARTED);
                break;

            case STARTED:
                audit.setAuditFinishDate(request.getAuditFinishDate());
                audit.setStatus(AuditStatus.REPORTING);
                break;

            case REPORTING:
                audit.setReport(reportService.get(request.getReportId()));
                audit.setStatus(AuditStatus.WAITING_REST_PAYMENT);
                break;

            case WAITING_REST_PAYMENT:
                audit.setRestPayment(request.getRestPayment());
                audit.setRestPaymentDate(request.getRestPaymentDate());
                audit.setAuditFinishDate(request.getAuditFinishDate());
                audit.setStatus(AuditStatus.FINISHED);
                break;

            default:
                audit.setStatus(AuditStatus.FINISHED);
                break;
        }
        repository.save(audit);

        return new Response(200, "audit o'zgartirildi", LocalDateTime.now());
    }

    private AuditEntity get(Integer id) {
        Optional<AuditEntity> byId = repository.findById(id);
        if (byId.isEmpty()) {
            throw new NotFoundException("Audit topilmadi id: " + id);
        }
        return byId.get();
    }

    public Response list() {
        List<AuditDTO> list = new ArrayList<>();
        repository.findAll().forEach(entity -> {
                    AuditDTO dto = getAudit(entity);

                    list.add(dto);

                }
        );

        return new Response(200, "success", LocalDateTime.now(), list);
    }

    public AuditDTO getById(Integer id) {
        AuditEntity auditEntity = get(id);
        return getAudit(auditEntity);
    }
    private AuditDTO getAudit(AuditEntity entity) {
        AuditDTO dto = new AuditDTO();
        dto.setId(entity.getId());
        dto.setSubject(subjectService.getSubject(entity.getSubject()));
        dto.setStatus(entity.getStatus());

        dto.setInLetter(letterService.getLetter(entity.getInLetter()));
        dto.setOutLetter(letterService.getLetter(entity.getOutLetter()));
        dto.setContract(contractService.getContract(entity.getContract()));

        dto.setHalfPayment(entity.getHalfPayment());
        dto.setHalfPaymentDate(entity.getHalfPaymentDate());
        dto.setAuditStartDate(entity.getAuditStartDate());

        dto.setLeader(auditorService.getAuditor(entity.getLeader()));
        dto.setAuditors(auditorService.getAuditors(entity.getAuditors()));
        //listdoc
        dto.setListDoc(fileService.getFileDto(entity.getListDoc()));
        dto.setAuditFinishDate(entity.getAuditFinishDate());
        dto.setRestPayment(entity.getRestPayment());
        dto.setRestPaymentDate(entity.getRestPaymentDate());

        dto.setReport(reportService.getReport(entity.getReport()));
        return dto;
    }
}
