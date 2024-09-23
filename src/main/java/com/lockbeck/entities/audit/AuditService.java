package com.lockbeck.entities.audit;

import com.lockbeck.demo.Response;
import com.lockbeck.entities.auditor.AuditorEntity;
import com.lockbeck.entities.auditor.AuditorService;
import com.lockbeck.entities.contract.ContractService;
import com.lockbeck.entities.file.FileService;
import com.lockbeck.entities.letter.LetterService;
import com.lockbeck.entities.report.ReportService;
import com.lockbeck.exceptions.NotFoundException;
import com.lockbeck.utils.DocumentCreator;
import com.lockbeck.utils.LocalDateFormatter;
import lombok.RequiredArgsConstructor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.lockbeck.utils.DocumentCreator.paragraph;

@Service
@RequiredArgsConstructor
public class AuditService {
    private final AuditRepository repository;
    private final LetterService letterService;
    private final ContractService contractService;
    private final AuditorService auditorService;
    private final ReportService reportService;
    private final FileService fileService;
    private final LocalDateFormatter localDateFormatter;

    public Response create(AuditCreateRequest request) {
        AuditEntity entity = new AuditEntity();
        entity.setInLetter(letterService.get(request.getInLetterId()));
        entity.setStatus(AuditStatus.REQUESTED);
        AuditEntity save = repository.save(entity);
        return new Response(200, "audit yaratildi" ,save.getId());
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
                audit.setHalfPaymentDate(localDateFormatter.getLocalDate(request.getHalfPaymentDate()));
                audit.setStatus(AuditStatus.HALF_PAID);
                break;

            case HALF_PAID:
                audit.setAuditStartDate(localDateFormatter.getLocalDate(request.getAuditStartDate()));
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
                audit.setAuditFinishDate(localDateFormatter.getLocalDate(request.getAuditFinishDate()));
                audit.setStatus(AuditStatus.REPORTING);
                break;

            case REPORTING:
                audit.setReport(reportService.get(request.getReportId()));
                audit.setStatus(AuditStatus.WAITING_REST_PAYMENT);
                break;

            case WAITING_REST_PAYMENT:
                audit.setRestPayment(request.getRestPayment());
                audit.setRestPaymentDate(localDateFormatter.getLocalDate(request.getRestPaymentDate()));
                audit.setStatus(AuditStatus.FINISHED);
                break;

            default:
                audit.setStatus(AuditStatus.FINISHED);
                break;
        }
        AuditEntity save = repository.save(audit);

        return new Response(200, "audit o'zgartirildi",save.getId());
    }

    public Response list() {
        List<AuditDTO> list = new ArrayList<>();
        repository.findAll().forEach(entity -> {
                    AuditDTO dto = getAudit(entity);

                    list.add(dto);

                }
        );

        return new Response(200, "success",  list);
    }

    public Response getById(Integer id) {
        AuditEntity auditEntity = get(id);
        AuditDTO dto = getAudit(auditEntity);
        return new Response(200, "success",  dto);
    }

    public AuditEntity get(Integer id) {
        Optional<AuditEntity> byId = repository.findById(id);
        if (byId.isEmpty()) {
            throw new NotFoundException("Audit topilmadi id: " + id);
        }
        return byId.get();
    }

    private AuditDTO getAudit(AuditEntity entity) {

        AuditDTO dto = new AuditDTO();
        dto.setId(entity.getId());
        dto.setSubject(entity.getInLetter().getSubject().getName());
        dto.setStatus(entity.getStatus());

        dto.setInLetter(letterService.getLetter(entity.getInLetter()));
        dto.setOutLetter(letterService.getLetter(entity.getOutLetter()));
        dto.setContract(contractService.getContract(entity.getContract()));

        dto.setHalfPayment(entity.getHalfPayment());
        dto.setHalfPaymentDate(localDateFormatter.getStringDate(entity.getHalfPaymentDate()));
        dto.setAuditStartDate(localDateFormatter.getStringDate(entity.getAuditStartDate()));

        dto.setLeader(auditorService.getAuditor(entity.getLeader()));
        dto.setAuditors(auditorService.getAuditors(entity.getAuditors()));
        //listdoc
        dto.setListDoc(fileService.getFileDto(entity.getListDoc()));
        dto.setAuditFinishDate(localDateFormatter.getStringDate(entity.getAuditFinishDate()));
        dto.setRestPayment(entity.getRestPayment());
        dto.setRestPaymentDate(localDateFormatter.getStringDate(entity.getRestPaymentDate()));

        dto.setReport(reportService.getReport(entity.getReport()));
        return dto;
    }

    public Response statistics() {
        int requested = 0;
        int process = 0;
        int finished = 0;
        for (AuditEntity auditEntity : repository.findAll()) {
            if (auditEntity.getStatus().equals(AuditStatus.REQUESTED)) {
                requested++;
            }else if (auditEntity.getStatus().equals(AuditStatus.FINISHED)) {
                finished++;
            }
            else {
                process++;
            }
        }

        return new Response(200,"success",new Statistics(requested,process,finished));

    }

    public Response delete(Integer id) {
        AuditEntity auditEntity = get(id);
        letterService.delete(auditEntity.getInLetter());
        if(auditEntity.getOutLetter() != null) {
            letterService.delete(auditEntity.getOutLetter());
        }
        if(auditEntity.getContract() != null) {
            contractService.delete(auditEntity.getContract());
        }
        if(auditEntity.getReport() != null) {
            reportService.delete(auditEntity.getReport());
        }

        if(auditEntity.getListDoc() != null) {
            fileService.delete(auditEntity.getListDoc());
        }
        auditEntity.setLeader(null);

        repository.delete(auditEntity);
        return new Response(200,"success");
    }

    public Resource report() throws IOException, InvalidFormatException {
//        AuditEntity auditEntity = get(auditId);
        XWPFDocument document = new DocumentCreator().createDocument();

        XWPFParagraph paragraph = document.createParagraph();

        // Align paragraph to the right
        paragraph.setAlignment(ParagraphAlignment.LEFT);



        // Create a run to add multiple lines of text
        XWPFRun run = paragraph.createRun();
        run.setFontFamily("Times New Roman");
        run.setFontSize(12);
        run.setText("\t\t\t\t\t\t\t\t“Kiberxavfsizlik markazi” DUKning");
        run.addBreak();  // Move to the next line
        run.setText("\t\t\t\t\t\t\t\t2024-yil “____”-fevraldagi");
        run.addBreak();  // Move to the next line
        run.setText("\t\t\t\t\t\t\t\t____________-son xatiga ilova");


        paragraph.setSpacingAfter(500);

        paragraph(document,
                "\"KIBERXAVFSIZLIK MARKAZI\" DUK",
                14,
                true,
                2000,
                null,
                false,
                ParagraphAlignment.CENTER);


        picture(document,300,250);

/*
        paragraph(document,

                )
*/


        Path uploadsDir = Paths.get("reports");
        if (!uploadsDir.toFile().exists()) {
            uploadsDir.toFile().mkdirs(); // Papkani yaratish, agar mavjud bo'lmasa
        }

        String fileName = "big_report.docx";
        Path filePath = uploadsDir.resolve(fileName);

        try (FileOutputStream out = new FileOutputStream(filePath.toFile())) {
            document.write(out);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        document.close();

        // FileSystemResource dan foydalanib, resursni qaytarish
        return new FileSystemResource(filePath.toFile());
    }

    private static void picture(XWPFDocument document,Integer width, Integer height) throws InvalidFormatException, IOException {
        XWPFParagraph paragraphForPicture = document.createParagraph();
        paragraphForPicture.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun runForPicture = paragraphForPicture.createRun();
        String imgFile = "pictures/img.png"; // Replace with your image path

        // Open the image file input stream
        FileInputStream fis = new FileInputStream(imgFile);

        // Add the image to the document
        runForPicture.addPicture(fis,
                XWPFDocument.PICTURE_TYPE_PNG,  // Image type
                imgFile,                        // Image file name
                Units.toEMU(width),               // Image width in EMUs (optional, adjust as needed)
                Units.toEMU(height));              // Image height in EMUs (optional, adjust as needed)
    }
}
