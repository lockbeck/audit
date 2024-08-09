package com.lockbeck.entities.letter;

import com.lockbeck.demo.Response;
import com.lockbeck.entities.auditor.AuditorService;
import com.lockbeck.entities.file.FileService;
import com.lockbeck.entities.stuff.StuffService;
import com.lockbeck.entities.subject.SubjectService;
import com.lockbeck.exceptions.NotFoundException;
import com.lockbeck.utils.LocalDateFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LetterService {
    private final LetterRepository repository;
    private final FileService fileService;
    private final AuditorService auditorService;
    private final StuffService stuffService;
    private final SubjectService subjectService;
    private final LocalDateFormatter localDateFormatter;

    public Response create(LetterCreateRequest request) {
        LetterEntity entity = new LetterEntity();
        entity.setDate(localDateFormatter.getLocalDate(request.getDate()));
        entity.setNumber(request.getNumber());
        entity.setIsOurLetter(request.getIsOurLetter());
        entity.setSubject(subjectService.get(request.getSubjectId()));
        entity.setFile(fileService.get(request.getFileId()));
        if(request.getIsOurLetter()){
            entity.setAuditor(auditorService.get(request.getAuditorId()));
        }else {
            entity.setStuff(stuffService.get(request.getStuffId()));
            entity.setEntryDate(localDateFormatter.getLocalDate(request.getEntryDate()));
            entity.setEntryNumber(request.getEntryNumber());
        }
        repository.save(entity);
        return new Response(200,"success");

    }

    public Response update(LetterUpdateRequest request) {
        LetterEntity entity = get(request.getId());
        entity.setDate(localDateFormatter.getLocalDate(request.getDate()));
        entity.setNumber(request.getNumber());
        entity.setIsOurLetter(request.getIsOurLetter());
        entity.setSubject(subjectService.get(request.getSubjectId()));
        entity.setFile(fileService.get(request.getFileId()));
        if(request.getIsOurLetter()){
            entity.setAuditor(auditorService.get(request.getAuditorId()));
        }else {
            entity.setStuff(stuffService.get(request.getStuffId()));
            entity.setEntryDate(localDateFormatter.getLocalDate(request.getEntryDate()));
            entity.setEntryNumber(request.getEntryNumber());
        }
        repository.save(entity);
        return new Response(200,"success");

    }


    public Response list(){
        List<LetterEntity> entities = repository.findAll();
        List<LetterDTO> list = new ArrayList<>();
        for (LetterEntity entity : entities) {
            LetterDTO dto = getLetter(entity);
            list.add(dto);
        }
        return new Response(200,"success",list);
    }



    public Response getById(Integer id) {
        LetterEntity letter = get(id);
        LetterDTO dto = getLetter(letter);
        return new Response(200,"success",dto);
    }

    public LetterEntity get(Integer inLetterId) {
        Optional<LetterEntity> byId = repository.findById(inLetterId);
        if (byId.isEmpty()) {
            throw new NotFoundException("Xat topilmadi id: " + inLetterId);
        }
        return byId.get();
    }

    public LetterDTO getLetter(LetterEntity inLetter) {
        if (inLetter==null) {
            return null;
        }
        LetterDTO dto= new LetterDTO();
        dto.setId(inLetter.getId());
        dto.setNumber(inLetter.getNumber());
        dto.setDate(localDateFormatter.getStringDate(inLetter.getDate()));
        dto.setIsOurLetter(inLetter.getIsOurLetter());
        dto.setSubject(subjectService.getSubject(inLetter.getSubject()));
        dto.setFile(fileService.getFileDto(inLetter.getFile()));
        if(inLetter.getIsOurLetter()){
            dto.setAuditor(auditorService.getAuditor(inLetter.getAuditor()));
        }else {
            dto.setEntryDate(localDateFormatter.getStringDate(inLetter.getEntryDate()));
            dto.setEntryNumber(inLetter.getEntryNumber());
            dto.setStuff(stuffService.getStuff(inLetter.getStuff()));
        }
        return dto;
    }
}
