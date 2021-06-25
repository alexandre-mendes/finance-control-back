package br.com.financeirojavaspring.service;

import br.com.financeirojavaspring.dto.InvitationDTO;
import br.com.financeirojavaspring.dto.UserDTO;
import br.com.financeirojavaspring.exception.InvalidInvitationException;
import br.com.financeirojavaspring.model.Account;
import br.com.financeirojavaspring.model.User;
import br.com.financeirojavaspring.repository.AccountRepository;
import br.com.financeirojavaspring.repository.InvitationRepository;
import br.com.financeirojavaspring.repository.UserRepository;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private AccountRepository accountRepository;
    private InvitationRepository invitationRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
    public UserService(AccountRepository accountRepository,
                       InvitationRepository invitationRepository,
                       UserRepository userRepository,
                       ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserDTO save(UserDTO userDTO) {
        var account = new Account();
        account.setUuid(UUID.randomUUID().toString());
        account = accountRepository.save(account);

        var user = modelMapper.map(userDTO, User.class);
        user.setUuid(UUID.randomUUID().toString());
        user.setPasswd(new BCryptPasswordEncoder().encode(userDTO.getPasswd()));
        user.setAccount(account);
        user = userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO saveWithInvitation(UserDTO userDTO, String invitationCode) {
        var invitationOpt = invitationRepository.findByUuid(invitationCode);
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
        var user = userRepository.findByUuid(uuid);
        return modelMapper.map(user, UserDTO.class);
    }

    public void delete(String uuid) {
        var user = userRepository.findByUuid(uuid);
        userRepository.deleteById(user.getId());
    }
}
