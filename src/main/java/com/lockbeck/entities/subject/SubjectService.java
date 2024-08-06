package com.lockbeck.entities.subject;

import com.lockbeck.demo.Response;
import com.lockbeck.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository repository;
    private final ModelMapper modelMapper;
    public SubjectEntity get(Integer subjectId) {
        Optional<SubjectEntity> byId = repository.findById(subjectId);
        if (byId.isEmpty()) {
            throw new NotFoundException("Tashkilot topilmadi id: " + subjectId);
        }
        return byId.get();
    }

    public Response create(SubjectCreateRequest request) {

        SubjectEntity entity = modelMapper.map(request, SubjectEntity.class);
        repository.save(entity);
        return new Response();
    }

    public List<SubjectDTO> list() {
        List<SubjectDTO> list = new ArrayList<>();
        for (SubjectEntity subject : repository.findAll()) {
            list.add(modelMapper.map(subject, SubjectDTO.class));
        }

        return list;
    }

    public SubjectDTO getSubject(SubjectEntity subject) {
        return modelMapper.map(subject, SubjectDTO.class);
    }
}
