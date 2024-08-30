package com.lockbeck.entities.stuff;

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
public class StuffService {
    private final StuffRepository repository;
    private final ModelMapper modelMapper;

    public Response create(StuffCreateRequest request) {
        StuffEntity save = repository.save(modelMapper.map(request, StuffEntity.class));

        return new Response(202,"success",save.getId());
    }

    public Response update(StuffUpdateDTO dto){
        StuffEntity stuff = get(dto.getId());
        stuff.setName(dto.getName());
        stuff.setEmail(dto.getEmail());
        stuff.setPhone(dto.getPhone());
        StuffEntity save = repository.save(stuff);
        return new Response(200,"success",save.getId());

    }

    public Response list() {
        List<StuffDTO> list = new ArrayList<>();
        for (StuffEntity stuffEntity : repository.findAll()) {
            list.add(modelMapper.map(stuffEntity, StuffDTO.class));
        }
        return new Response(200,"success",list);
    }

    public Response getById(Integer id) {
        StuffEntity stuff = get(id);
        return new Response(200,"success",getStuff(stuff));
    }

    public StuffDTO getStuff(StuffEntity stuff) {
        return modelMapper.map(stuff, StuffDTO.class);
    }

    public StuffEntity get(Integer stuffId) {
        Optional<StuffEntity> byId = repository.findById(stuffId);
        if (byId.isEmpty()) {
            throw new NotFoundException("Xodim topilmadi id: " + stuffId);
        }
        return byId.get();
    }

    public Response delete(Integer id) {
        StuffEntity stuffEntity = get(id);
        repository.delete(stuffEntity);
        return new Response(200,"success");
    }
}
