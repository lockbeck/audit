package com.lockbeck.entities.report;

import com.lockbeck.demo.Response;
import com.lockbeck.entities.file.FileService;
import com.lockbeck.entities.letter.LetterService;
import com.lockbeck.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository repository;
    private final LetterService letterService;
    private final FileService fileService;

    public ReportEntity get(Integer reportId) {
        Optional<ReportEntity> byId = repository.findById(reportId);
        if (byId.isEmpty()) {
            throw new NotFoundException("Hisobot topilmadi id: "+reportId);
        }
        return byId.get();
    }

    public Response create(ReportCreateRequest request) {
        ReportEntity report = new ReportEntity();
        report.setNumber(request.getNumber());
        report.setCheckedComps(request.getCheckedComps());
        report.setCheckedServers(request.getCheckedServers());
        report.setLetter(letterService.get(request.getLetterId()));
        report.setReportFile(fileService.getFile(request.getReportFileId()));

        repository.save(report);

        return new Response(202,"Saqlandi", LocalDateTime.now());
    }

    public List<ReportDTO> list() {
        return null;
    }

    public ReportDTO getReport(ReportEntity report) {
        return ReportDTO.builder()
                .id(report.getId())
                .number(report.getNumber())
                .checkedComps(report.getCheckedComps())
                .checkedServers(report.getCheckedServers())
                .letter(letterService.getLetter(report.getLetter()))
                .reportFile(fileService.getFileDto(report.getReportFile()))
                .build();
    }
}
