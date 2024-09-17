package com.lockbeck.entities.server_rooms;

import com.lockbeck.demo.Response;
import com.lockbeck.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
