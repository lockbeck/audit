package com.lockbeck.entities.contract;

import com.lockbeck.demo.Response;
import com.lockbeck.entities.file.FileEntity;
import com.lockbeck.entities.file.FileService;
import com.lockbeck.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository repository;
    private final FileService fileService;
    private final ModelMapper modelMapper;

    public ContractEntity get(Integer contractId) {
        Optional<ContractEntity> byId = repository.findById(contractId);
        if (byId.isEmpty()) {
            throw new NotFoundException("shartnoma topilmadi id: "+contractId);
        }
        return byId.get();
    }

    public Response create(ContractCreateRequest request) {

        ContractEntity entity = new ContractEntity();
        entity.setDate(request.getDate());
        entity.setNumber(request.getNumber());
        entity.setPrice(request.getPrice());
        entity.setCompNums(request.getCompNums());
        entity.setServerNums(request.getServerNums());
        entity.setObjectAddress(request.getObjectAddress());

        // Manually set the FileEntity
        entity.setFile(fileService.get(request.getFileId()));

        // Save contractEntity and return response
        repository.save(entity);

        return new Response(202,"Shartnoma saqlandi", LocalDateTime.now());

    }

    public List<ContractDTO> list() {
        List<ContractDTO> list = new ArrayList<>();
        for (ContractEntity entity : repository.findAll()) {
            ContractDTO map = modelMapper.map(entity, ContractDTO.class);
            map.setFile(fileService.getFileDto(entity.getFile()));
            list.add(map);
        }
        return list;
    }
}
