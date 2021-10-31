package br.com.financeirojavaspring.controller;

import br.com.financeirojavaspring.dto.TagDTO;
import br.com.financeirojavaspring.entity.Tag;
import br.com.financeirojavaspring.service.TagService;
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
@CrossOrigin(origins = "*")
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

    @DeleteMapping(value = "/{uuid}")
    @ApiOperation(value = "Deleta uma tag.", authorizations = {@Authorization(value = "Bearer")})
    public void remove(@PathVariable(name = "uuid") final String uuid) {
        tagService.remove(uuid);
    }
}
