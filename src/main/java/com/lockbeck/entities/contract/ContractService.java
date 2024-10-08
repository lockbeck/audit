package com.lockbeck.entities.contract;

import com.lockbeck.demo.Response;
import com.lockbeck.entities.file.FileService;
import com.lockbeck.exceptions.NotFoundException;
import com.lockbeck.utils.LocalDateFormatter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository repository;
    private final FileService fileService;
    private final ModelMapper modelMapper;
    private final LocalDateFormatter localDateFormatter;

    public Response create(ContractCreateRequest request) {

        ContractEntity entity = new ContractEntity();
        entity.setDate(localDateFormatter.getLocalDate(request.getDate()));
        entity.setNumber(request.getNumber());
        entity.setPrice(request.getPrice());
        entity.setCompNums(request.getCompNums());
        entity.setServerNums(request.getServerNums());
        entity.setObjectAddress(request.getObjectAddress());

        // Manually set the FileEntity
        entity.setFile(fileService.get(request.getFileId()));

        // Save contractEntity and return response
        ContractEntity save = repository.save(entity);

        return new Response(200,"Shartnoma saqlandi",save.getId());

    }

    public Response update(ContractUpdateRequest request) {

        ContractEntity entity = get(request.getId());
        entity.setDate(localDateFormatter.getLocalDate(request.getDate()));
        entity.setNumber(request.getNumber());
        entity.setPrice(request.getPrice());
        entity.setCompNums(request.getCompNums());
        entity.setServerNums(request.getServerNums());
        entity.setObjectAddress(request.getObjectAddress());
        // Manually set the FileEntity
        entity.setFile(fileService.get(request.getFileId()));
        // Save contractEntity and return response
        ContractEntity save = repository.save(entity);
        return new Response(200,"success",save.getId());

    }

    public Response list() {
        List<ContractDTO> list = new ArrayList<>();
        for (ContractEntity entity : repository.findAll()) {
            ContractDTO map = modelMapper.map(entity, ContractDTO.class);
            map.setFile(fileService.getFileDto(entity.getFile()));
            list.add(map);
        }
        return new Response(200,"success",  list);
    }

    public Response getById(Integer id) {
        ContractEntity contractEntity = get(id);

        ContractDTO dto = getContract(contractEntity);

        return new Response(200,"success", dto);
    }

    public ContractDTO getContract(ContractEntity contract) {
        if (contract==null) {
            return null;
        }
        return ContractDTO.builder()
                .id(contract.getId())
                .date(localDateFormatter.getStringDate(contract.getDate()))
                .number(contract.getNumber())
                .price(contract.getPrice())
                .compNums(contract.getCompNums())
                .serverNums(contract.getServerNums())
                .objectAddress(contract.getObjectAddress())
                .file(fileService.getFileDto(contract.getFile()))
                .build();
    }

    public ContractEntity get(Integer contractId) {
        Optional<ContractEntity> byId = repository.findById(contractId);
        if (byId.isEmpty()) {
            throw new NotFoundException("shartnoma topilmadi id: "+contractId);
        }
        return byId.get();
    }

    public void delete(ContractEntity contract) {
        fileService.delete(contract.getFile());

        repository.delete(contract);
    }
}
