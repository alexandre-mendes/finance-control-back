package br.com.financecontrol.service;

import br.com.financecontrol.entity.Invitation;
import br.com.financecontrol.exception.EntityNotFoundException;
import br.com.financecontrol.repository.InvitationRepository;
import br.com.financecontrol.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvitationService {

    private final InvitationRepository invitationRepository;
    private final UserRepository userRepository;

    @Autowired
    public InvitationService(InvitationRepository invitationRepository,
                             UserRepository userRepository) {
        this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
    }

    public Invitation createInvitation(String idUser) {
        var user = userRepository.findById(idUser)
                .orElseThrow(EntityNotFoundException::new);
        return invitationRepository.save(
            Invitation.builder()
                .userInvited(user)
                .build());
    }
}
