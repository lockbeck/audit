package com.lockbeck.entities.server_rooms;

import com.lockbeck.demo.Response;
import com.lockbeck.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QueryService {
    private final QueryRepository repository;
    public Response create(QueryRequest request) {

        QueryEntity build = QueryEntity.builder()
                .question(request.getQuestion())
                .yes(request.getYes())
                .no(request.getNo())
                .recommendation(request.getRecommendation())
                .build();
        repository.save(build);
        return new Response(200,"success");

    }

    public Response getList() {
        List<QueryRequest> list = new ArrayList<>();
        List<QueryEntity> entities = repository.findAll();
        entities.forEach(entity -> {
            QueryRequest dto = getDTO(entity);
            list.add(dto);
        });
        return new Response(200,"success",list);
    }
    public Response getById(Integer id) {
        QueryEntity queryEntity = get(id);
        QueryRequest dto = getDTO(queryEntity);
        return new Response(200,"success",dto);

    }

    public Response update(QueryRequest request) {
        QueryEntity queryEntity = get(request.getId());
        queryEntity.setQuestion(request.getQuestion());
        queryEntity.setYes(request.getYes());
        queryEntity.setNo(request.getNo());
        queryEntity.setRecommendation(request.getRecommendation());
        repository.save(queryEntity);
        return new Response(200,"success");
    }

    public Response delete(Integer id) {
        QueryEntity queryEntity = get(id);
        repository.delete(queryEntity);
        return new Response(200,"success");
    }

    private QueryRequest getDTO(QueryEntity entity) {
        return QueryRequest.builder()
                .id(entity.getId())
                .question(entity.getQuestion())
                .yes(entity.getYes())
                .no(entity.getNo())
                .recommendation(entity.getRecommendation())
                .build();

    }

    private QueryEntity get(Integer id) {
        Optional<QueryEntity> byId = repository.findById(id);
        if (byId.isEmpty()) {
            throw new NotFoundException("so'rov topilmadi");
        }
        return byId.get();
    }
    public Resource report( TestDto dto) throws IOException {
        XWPFDocument document = new XWPFDocument();

        paragraph(document,
                "4.6. Zavod server xonasining axborot xavfsizligi talablarga muvofiqligini oʻrganish natijalari",
                true,
                180,
                null,
                false
        );
        paragraph(document,
                "Audit jarayonida Zavod server xonasining Oʻz DSt 2875:2014 “Axborot texnologiyasi. Datamarkazlarga qo‘yiladigan talablar. Infratuzilma va axborot xavfsizligini ta’minlash” davlat standartiga muvofiqligi oʻrganildi.",
                true,
                180,
                null,
                false
        );

        QueryEntity queryEntity = get(dto.getQuestionId());
        String text;
        String recommendation;
        if(dto.getAnswer()){

            text=queryEntity.getYes();
        }else {
            text=queryEntity.getNo();
            recommendation=queryEntity.getRecommendation();
        }

        paragraph(document,
                text,
                true,
                180,
                null,
                false
        );

        // 4.3.1 bo'limi uchun default matn

        Path uploadsDir = Paths.get("reports");
        if (!uploadsDir.toFile().exists()) {
            uploadsDir.toFile().mkdirs(); // Papkani yaratish, agar mavjud bo'lmasa
        }

        String fileName = "reportServerRoom.docx";
        Path filePath = uploadsDir.resolve(fileName);

        try (FileOutputStream out = new FileOutputStream(filePath.toFile())) {
            document.write(out);
        }


        document.close();

        // FileSystemResource dan foydalanib, resursni qaytarish
        return new FileSystemResource(filePath.toFile());

    }
    private static void paragraph(XWPFDocument document, String text, boolean bold, int space, String color, boolean italic) {
        XWPFParagraph bigParagraph1 = document.createParagraph();
        XWPFRun run1_1 = bigParagraph1.createRun();
        run1_1.setText(text);
        run1_1.setBold(bold);
        run1_1.setFontSize(14);
        run1_1.setItalic(italic);
        run1_1.setFontFamily("Times New Roman");
        run1_1.setColor(color == null ? "000000" : color);
        bigParagraph1.setSpacingAfter(space);
    }
}
