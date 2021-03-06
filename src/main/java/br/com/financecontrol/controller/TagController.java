package br.com.financecontrol.controller;

import br.com.financecontrol.dto.TagDTO;
import br.com.financecontrol.entity.Tag;
import br.com.financecontrol.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/tags")
@Api(value = "Tag Controller")
public class TagController {

    private final TagService tagService;
    private final ModelMapper modelMapper;

    @Autowired
    public TagController(TagService tagService, ModelMapper modelMapper) {
        this.tagService = tagService;
        this.modelMapper = modelMapper;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @ApiOperation(value = "Cria uma tag.", authorizations = {@Authorization(value = "Bearer")})
    public TagDTO save(@RequestBody final TagDTO dto) {
        var entity = modelMapper.map(dto, Tag.class);
        entity = tagService.save(entity);
        return modelMapper.map(entity, TagDTO.class);
    }

    @PutMapping
    @ApiOperation(value = "Atualiza uma tag.", authorizations = {@Authorization(value = "Bearer")})
    public TagDTO update(@RequestBody final TagDTO dto) {
        var entity = modelMapper.map(dto, Tag.class);
        entity = tagService.update(entity);
        return modelMapper.map(entity, TagDTO.class);
    }

    @GetMapping
    @ApiOperation(value = "Obtem uma lista de tags.", authorizations = {@Authorization(value = "Bearer")})
    public Page<TagDTO> findAll(final Pageable pageable) {
        final var tags = tagService.findAll(pageable);
        final var dtos = tags.stream().map(tag -> modelMapper.map(tag, TagDTO.class)).collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, tags.getTotalElements());
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Deleta uma tag.", authorizations = {@Authorization(value = "Bearer")})
    public void remove(@PathVariable(name = "id") final String id) {
        tagService.remove(id);
    }
}
