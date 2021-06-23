package br.com.financeirojavaspring.service;

import br.com.financeirojavaspring.dto.UserDTO;
import br.com.financeirojavaspring.model.User;
import br.com.financeirojavaspring.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserDTO save(UserDTO userDTO) {
        var user = modelMapper.map(userDTO, User.class);
        user.setUuid(UUID.randomUUID().toString());
//        user.setPasswd(new BCryptPasswordEncoder().encode(userDTO.getPasswd()));
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
