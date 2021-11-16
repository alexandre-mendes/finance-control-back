package br.com.financecontrol.service;

import br.com.financecontrol.entity.Tag;
import br.com.financecontrol.exception.EntityNotFoundException;
import br.com.financecontrol.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag save(final Tag tag) {
        return tagRepository.save(tag);
    }

    public Tag update(final Tag tag) {
        return tagRepository.save(tag);
    }

    public Page<Tag> findAll(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    public void remove(final String id) {
        tagRepository.deleteById(id);
    }
}
