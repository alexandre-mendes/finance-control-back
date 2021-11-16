package br.com.financecontrol.service;

import br.com.financecontrol.dto.MailSenderDTO;
import br.com.financecontrol.dto.UserDTO;
import br.com.financecontrol.entity.Account;
import br.com.financecontrol.entity.Invitation;
import br.com.financecontrol.entity.User;
import br.com.financecontrol.exception.EntityNotFoundException;
import br.com.financecontrol.exception.InvalidInvitationException;
import br.com.financecontrol.repository.AccountRepository;
import br.com.financecontrol.repository.InvitationRepository;
import br.com.financecontrol.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class UserService {

    private final SenderMailService senderMailService;
    private final AccountRepository accountRepository;
    private final InvitationRepository invitationRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(SenderMailService senderMailService,
        AccountRepository accountRepository,
        InvitationRepository invitationRepository,
        UserRepository userRepository,
        ModelMapper modelMapper) {
        this.senderMailService = senderMailService;
        this.accountRepository = accountRepository;
        this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public User save(User user) {
        var account = accountRepository.save(new Account());

        user.setPasswd(new BCryptPasswordEncoder().encode(user.getPasswd()));
        user.setAccount(account);
        user.setActivationCode(String.valueOf(ThreadLocalRandom.current().nextInt(12345678, 87654321)));
        user = userRepository.save(user);

        senderMailService.submit(
            MailSenderDTO.builder()
                .emailDestiny(user.getUsername())
                .subject("Confirmação de Conta")
                .message("Olá, " + user.getName() + " este é o código de confirmação para sua conta " + user.getActivationCode() + ".")
                .build());

        return user;
    }

    public User saveWithInvitation(User user, String invitationCode) {
        var invitationOpt = invitationRepository.findOne(
            Example.of(
                Invitation.builder()
                    .id(invitationCode)
                    .build()));

        if (invitationOpt.isEmpty()) {
            throw new InvalidInvitationException(invitationCode);
        }
        user.setAccount(invitationOpt.get().getUserInvited().getAccount());
        return userRepository.save(user);
    }

    public UserDTO find(String id) {
        var user = userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(user, UserDTO.class);
    }

    public void delete(String id) {
        userRepository.deleteById(id);
    }

    public void activation(String email, String activationCode) {
        var user = userRepository.findOne(
            Example.of(
                User.builder()
                    .username(email)
                    .activationCode(activationCode)
                    .build())
        ).orElseThrow(EntityNotFoundException::new);

        user.setActive(true);
        userRepository.save(user);
    }
}
