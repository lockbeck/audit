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
    public Response create(SubjectCreateRequest request) {

        SubjectEntity entity = modelMapper.map(request, SubjectEntity.class);
        repository.save(entity);
        return new Response();
    }

    public Response update(SubjectUpdateDTO request) {
        SubjectEntity subject = get(request.getId());
        subject.setName(request.getName());
        subject.setAddress(request.getAddress());
        subject.setEmail(request.getEmail());
        subject.setPhone(request.getPhone());
        repository.save(subject);
        return new Response(200,"Success");

    }

    public Response list() {
        List<SubjectDTO> list = new ArrayList<>();
        for (SubjectEntity subject : repository.findAll()) {
            list.add(getSubject(subject));
        }

        return new Response(200,"Success", list);
    }

    public Response getById(Integer id) {
        SubjectEntity subject = get(id);
        SubjectDTO dto = getSubject(subject);
        return new Response(200,"Success",dto);
    }

    public SubjectEntity get(Integer subjectId) {
        Optional<SubjectEntity> byId = repository.findById(subjectId);
        if (byId.isEmpty()) {
            throw new NotFoundException("Tashkilot topilmadi id: " + subjectId);
        }
        return byId.get();
    }

    public SubjectDTO getSubject(SubjectEntity subject) {

        return SubjectDTO.builder()
                .id(subject.getId())
                .name(subject.getName())
                .phone(subject.getPhone())
                .address(subject.getAddress())
                .email(subject.getEmail())
                .build();
    }
}
