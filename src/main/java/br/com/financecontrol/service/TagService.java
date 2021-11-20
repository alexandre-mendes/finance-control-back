package br.com.financecontrol.service;

import br.com.financecontrol.entity.Account;
import br.com.financecontrol.entity.Tag;
import br.com.financecontrol.entity.User;
import br.com.financecontrol.exception.ExclusionNotAllowedException;
import br.com.financecontrol.repository.TagRepository;
import br.com.financecontrol.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final AuthenticationService authenticationService;

    @Autowired
    public TagService(TagRepository tagRepository, AuthenticationService authenticationService) {
        this.tagRepository = tagRepository;
        this.authenticationService = authenticationService;
    }

    public Tag save(final Tag tag) {
        final Account account = authenticationService.getUser().getAccount();
        tag.setAccount(account);
        return tagRepository.save(tag);
    }

    public Tag update(final Tag tag) {
        return tagRepository.save(tag);
    }

    public Page<Tag> findAll(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    public void remove(final String id) {
        try {
            tagRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new ExclusionNotAllowedException("Não é possível excluir uma tag em uso.");
        }
    }
}
