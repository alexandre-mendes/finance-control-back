package br.com.financeirojavaspring.service;

import br.com.financeirojavaspring.dto.InvitationDTO;
import br.com.financeirojavaspring.model.Invitation;
import br.com.financeirojavaspring.repository.InvitationRepository;
import br.com.financeirojavaspring.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class InvitationService {

    private InvitationRepository invitationRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
    public InvitationService(InvitationRepository invitationRepository,
                             UserRepository userRepository,
                             ModelMapper modelMapper) {
                    this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public InvitationDTO createInvitation(String idUser) {
        var user = userRepository.findByUuid(idUser);
        var invitation = new Invitation();
        invitation.setUuid(UUID.randomUUID().toString());
        invitation.setUserInvited(user);
        invitation = invitationRepository.save(invitation);
        return modelMapper.map(invitation, InvitationDTO.class);
    }
}
