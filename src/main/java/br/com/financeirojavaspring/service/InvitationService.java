package br.com.financeirojavaspring.service;

import br.com.financeirojavaspring.dto.InvitationDTO;
import br.com.financeirojavaspring.exception.EntityNotFoundException;
import br.com.financeirojavaspring.entity.Invitation;
import br.com.financeirojavaspring.entity.User;
import br.com.financeirojavaspring.repository.InvitationRepository;
import br.com.financeirojavaspring.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class InvitationService {

    private final InvitationRepository invitationRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public InvitationService(InvitationRepository invitationRepository,
                             UserRepository userRepository,
                             ModelMapper modelMapper) {
                    this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public InvitationDTO createInvitation(String idUser) {
        var user = userRepository.findOne(
            Example.of(
                User.builder()
                    .uuid(idUser)
                    .build())
        ).orElseThrow(EntityNotFoundException::new);
        var invitation = invitationRepository.save(
            Invitation.builder()
                .uuid(UUID.randomUUID().toString())
                .userInvited(user)
                .build());
        return modelMapper.map(invitation, InvitationDTO.class);
    }
}
