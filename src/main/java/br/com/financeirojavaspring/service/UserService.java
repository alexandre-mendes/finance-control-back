package br.com.financeirojavaspring.service;

import br.com.financeirojavaspring.dto.MailSenderDTO;
import br.com.financeirojavaspring.dto.UserActivationDTO;
import br.com.financeirojavaspring.dto.UserDTO;
import br.com.financeirojavaspring.exception.EntityNotFoundException;
import br.com.financeirojavaspring.exception.InvalidInvitationException;
import br.com.financeirojavaspring.entity.Account;
import br.com.financeirojavaspring.entity.Invitation;
import br.com.financeirojavaspring.entity.User;
import br.com.financeirojavaspring.repository.AccountRepository;
import br.com.financeirojavaspring.repository.InvitationRepository;
import br.com.financeirojavaspring.repository.UserRepository;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    public UserDTO save(UserDTO userDTO) {
        var account = accountRepository.save(
            Account.builder()
                .uuid(UUID.randomUUID().toString())
                .build()
        );

        var user = modelMapper.map(userDTO, User.class);
        user.setUuid(UUID.randomUUID().toString());
        user.setPasswd(new BCryptPasswordEncoder().encode(userDTO.getPasswd()));
        user.setAccount(account);
        user.setActivationCode(String.valueOf(ThreadLocalRandom.current().nextInt(12345678, 87654321)));
        user = userRepository.save(user);

        senderMailService.submit(
            MailSenderDTO.builder()
                .emailDestiny(userDTO.getUsername())
                .subject("Confirmação de Conta")
                .message("Olá, " + userDTO.getName() + " este é o código de confirmação para sua conta " + user.getActivationCode() + ".")
                .build());

        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO saveWithInvitation(UserDTO userDTO, String invitationCode) {
        var invitationOpt = invitationRepository.findOne(
            Example.of(
                Invitation.builder()
                    .uuid(invitationCode)
                    .build()));

        if (invitationOpt.isEmpty()) {
            throw new InvalidInvitationException(invitationCode);
        }
        var user = modelMapper.map(userDTO, User.class);
        user.setAccount(invitationOpt.get().getUserInvited().getAccount());
        user.setUuid(UUID.randomUUID().toString());
        user = userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO find(String uuid) {
        var user = userRepository.findOne(
            Example.of(
                User.builder()
                    .uuid(uuid)
                    .build())
        ).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(user, UserDTO.class);
    }

    public void delete(String uuid) {
        var user = userRepository.findOne(
            Example.of(
                User.builder()
                    .uuid(uuid)
                    .build())
        ).orElseThrow(EntityNotFoundException::new);
        userRepository.deleteById(user.getId());
    }

    public void activation(UserActivationDTO dto) {
        var user = userRepository.findOne(
            Example.of(
                User.builder()
                    .username(dto.getEmail())
                    .activationCode(dto.getActivationCode())
                    .build())
        ).orElseThrow(EntityNotFoundException::new);

        user.setActive(true);
        userRepository.save(user);
    }
}
