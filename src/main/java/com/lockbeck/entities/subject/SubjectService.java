package com.lockbeck.entities.subject;

import com.lockbeck.demo.Response;
import com.lockbeck.entities.subject.type.SubjectTypeRepository;
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
        SubjectEntity save = repository.save(entity);
        return new Response(200,"success",save.getId());
    }

    public Response update(SubjectUpdateDTO request) {
        SubjectEntity subject = get(request.getId());
        subject.setName(request.getName());
        subject.setAddress(request.getAddress());
        subject.setEmail(request.getEmail());
        subject.setPhone(request.getPhone());
        SubjectEntity save = repository.save(subject);
        return new Response(200,"Success",save.getId());

    }

    public Response list() {
        List<SubjectDTO> list = new ArrayList<>();
        for (SubjectEntity subject : repository.findAll()) {
            list.add(getSubject(subject));
        }
        return new Response(200,"Success", list);
    }

    public Response getById(String id) {
        SubjectEntity subject = get(id);
        SubjectDTO dto = getSubject(subject);
        return new Response(200,"Success",dto);
    }

    public SubjectEntity get(String subjectId) {
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

    public Response delete(String id) {
        SubjectEntity subjectEntity = get(id);
        subjectEntity.setType(null);
        repository.delete(subjectEntity);

        return new Response(200,"success");
    }

   /* public void saveSubjectsFromJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<SubjectTest> subjects = objectMapper.readValue(new File("C:\\Users\\reestr\\Desktop\\audit\\tashkilotlar.json"), new TypeReference<List<SubjectTest>>() {});
        subjects.forEach(subject -> {

            SubjectTypeEntity type ;
            Optional<SubjectTypeEntity> byName = subjectTypeRepository.findByName(subject.getType());
            if (byName.isEmpty()) {
                SubjectTypeEntity build = SubjectTypeEntity.builder()

                        .name(subject.getType())
                        .build();
                type= subjectTypeRepository.save(build);
            }else {
                type= byName.get();
            }


            SubjectEntity subjectEntity = SubjectEntity.builder()
                    .id(subject.getId())
                    .name(subject.getName())
                    .type(type)
                    .build();
            repository.save(subjectEntity);
            System.out.println(subject.getId()+"   "+subject.getName());
        });
        System.out.println(subjects.size());

    }*/
}
