package com.lockbeck.entities.report;

import com.lockbeck.demo.Response;
import com.lockbeck.entities.file.FileService;
import com.lockbeck.entities.letter.LetterService;
import com.lockbeck.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository repository;
    private final LetterService letterService;
    private final FileService fileService;

    public Response create(ReportCreateRequest request) {
        ReportEntity report = new ReportEntity();
        report.setNumber(request.getNumber());
        report.setCheckedComps(request.getCheckedComps());
        report.setCheckedServers(request.getCheckedServers());
        report.setLetter(letterService.get(request.getLetterId()));
        report.setReportFile(fileService.getFile(request.getReportFileId()));

        ReportEntity save = repository.save(report);

        return new Response(200,"Saqlandi",save.getId());
    }

    public Response update(ReportUpdateRequest request){
        ReportEntity report = get(request.getId());

        report.setNumber(request.getNumber());
        report.setCheckedComps(request.getCheckedComps());
        report.setCheckedServers(request.getCheckedServers());
        report.setLetter(letterService.get(request.getLetterId()));
        report.setReportFile(fileService.getFile(request.getReportFileId()));

        ReportEntity save = repository.save(report);

        return  new Response(200,"Hisobot o'zgartirildi",save.getId());
    }

    public Response list() {
        List<ReportDTO> dtos = new ArrayList<>();
        for (ReportEntity report : repository.findAll()) {
            ReportDTO dto = getReport(report);
            dtos.add(dto);
        }
        return new Response(200,"success",  dtos);
    }

    public Response getById(Integer id) {
        ReportEntity report = get(id);
        ReportDTO dto = getReport(report);
        return new Response(200,"success", dto);
    }

    public ReportDTO getReport(ReportEntity report) {
        if (report==null) {
            return null;
        }
        return ReportDTO.builder()
                .id(report.getId())
                .number(report.getNumber())
                .checkedComps(report.getCheckedComps())
                .checkedServers(report.getCheckedServers())
                .letter(letterService.getLetter(report.getLetter()))
                .reportFile(fileService.getFileDto(report.getReportFile()))
                .build();
    }

    public ReportEntity get(Integer reportId) {
        Optional<ReportEntity> byId = repository.findById(reportId);
        if (byId.isEmpty()) {
            throw new NotFoundException("Hisobot topilmadi id: "+reportId);
        }
        return byId.get();
    }
}
