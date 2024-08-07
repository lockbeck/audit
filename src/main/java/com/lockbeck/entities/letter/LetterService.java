package com.lockbeck.entities.letter;

import com.lockbeck.entities.auditor.AuditorService;
import com.lockbeck.entities.file.FileService;
import com.lockbeck.entities.stuff.StuffService;
import com.lockbeck.entities.subject.SubjectService;
import com.lockbeck.exceptions.NotFoundException;
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

    public LetterEntity get(Integer inLetterId) {
        Optional<LetterEntity> byId = repository.findById(inLetterId);
        if (byId.isEmpty()) {
            throw new NotFoundException("Xat topilmadi id: " + inLetterId);
        }
        return byId.get();
    }

    public Integer create(LetterCreateRequest request) {
        LetterEntity entity = new LetterEntity();
        entity.setDate(request.getDate());
        entity.setNumber(request.getNumber());
        entity.setIsOurLetter(request.getIsOurLetter());
        entity.setSubject(subjectService.get(request.getSubjectId()));
        entity.setFile(fileService.get(request.getFileId()));
        if(request.getIsOurLetter()){
            entity.setAuditor(auditorService.get(request.getAuditorId()));
        }else {
            entity.setStuff(stuffService.get(request.getStuffId()));
            entity.setEntryDate(request.getEntryDate());
            entity.setEntryNumber(request.getEntryNumber());
        }
        return repository.save(entity).getId();

    }

    public List<LetterDTO> list(){
        List<LetterEntity> entities = repository.findAll();
        List<LetterDTO> list = new ArrayList<>();
        for (LetterEntity entity : entities) {
            LetterDTO dto = new LetterDTO();
            dto.setId(entity.getId());
            dto.setNumber(entity.getNumber());
            dto.setDate(entity.getDate());
            dto.setIsOurLetter(entity.getIsOurLetter());
            dto.setSubject(subjectService.getSubject(entity.getSubject()));
            dto.setFile(fileService.getFileDto(entity.getFile()));
            if (entity.getIsOurLetter()) {
                dto.setAuditor(auditorService.getAuditor(entity.getAuditor()));
            }
            else {
                dto.setEntryDate(entity.getEntryDate());
                dto.setEntryNumber(entity.getEntryNumber());
                dto.setStuff(stuffService.getStuff(entity.getStuff()));
            }
            list.add(dto);

        }
        return list;
    }

    public LetterDTO getLetter(LetterEntity inLetter) {
        LetterDTO dto= new LetterDTO();
        dto.setId(inLetter.getId());
        dto.setNumber(inLetter.getNumber());
        dto.setDate(inLetter.getDate());
        dto.setIsOurLetter(inLetter.getIsOurLetter());
        dto.setSubject(subjectService.getSubject(inLetter.getSubject()));
        dto.setFile(fileService.getFileDto(inLetter.getFile()));
        if(inLetter.getIsOurLetter()){
            dto.setAuditor(auditorService.getAuditor(inLetter.getAuditor()));
        }else {
            dto.setEntryDate(inLetter.getEntryDate());
            dto.setEntryNumber(inLetter.getEntryNumber());
            dto.setStuff(stuffService.getStuff(inLetter.getStuff()));
        }
        return dto;
    }
}
