package br.com.financeirojavaspring.service;

import br.com.financeirojavaspring.dto.TagDTO;
import br.com.financeirojavaspring.entity.Tag;
import br.com.financeirojavaspring.exception.EntityNotFoundException;
import br.com.financeirojavaspring.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag save(final Tag tag) {
        tag.setUuid(UUID.randomUUID().toString());
        return tagRepository.save(tag);
    }

    public Tag update(final Tag tag) {
        final var tagSaved = tagRepository.findOne(Example.of(Tag.builder().uuid(tag.getUuid()).build()))
                .orElseThrow(EntityNotFoundException::new);
        tag.setId(tagSaved.getId());
        return tagRepository.save(tag);
    }

    public Page<Tag> findAll(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }
}
