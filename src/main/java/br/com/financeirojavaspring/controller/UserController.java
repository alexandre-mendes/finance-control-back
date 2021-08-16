package br.com.financeirojavaspring.controller;

import br.com.financeirojavaspring.dto.UserActivationDTO;
import br.com.financeirojavaspring.dto.UserDTO;
import br.com.financeirojavaspring.entity.User;
import br.com.financeirojavaspring.service.SenderMailService;
import br.com.financeirojavaspring.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
@Api(value = "User Controller")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, SenderMailService mailSender) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @ApiOperation(value = "Salva um usuário")
    public UserDTO createUser(@RequestBody @Valid UserDTO dto) {
        var domain = modelMapper.map(dto, User.class);
        domain = userService.save(domain);
        return modelMapper.map(domain, UserDTO.class);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/uuid/{uuid}")
    @ApiOperation(value = "Obtem usuário por UUID", authorizations = {@Authorization(value = "Bearer")})
    public UserDTO findUser(@PathVariable String uuid) {
        return userService.find(uuid);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/uuid/{uuid}")
    @ApiOperation(value = "Deleta usuário por UUID", authorizations = {@Authorization(value = "Bearer")})
    public void deleteUser(@PathVariable String uuid) {
        userService.delete(uuid);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/invitation/{invitationCode}")
    @ApiOperation(value = "Cria usuário a partir de um codigo de convite.", authorizations = {@Authorization(value = "Bearer")})
    public UserDTO createUserByInvitation(@RequestBody @Valid UserDTO dto, @PathVariable String invitationCode) {
        var domain = modelMapper.map(dto, User.class);
        domain = userService.saveWithInvitation(domain, invitationCode);
        return modelMapper.map(domain, UserDTO.class);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/activation")
    @ApiOperation(value = "Cria usuário a partir de um codigo de convite.")
    public void userActivation(UserActivationDTO dto) {
        userService.activation(dto.getEmail(), dto.getActivationCode());
    }
}
