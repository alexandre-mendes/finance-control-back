package br.com.financeirojavaspring.controller;

import br.com.financeirojavaspring.dto.UserDTO;
import br.com.financeirojavaspring.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
@Api(value = "User Controller")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @ApiOperation(value = "Salva um usuário", authorizations = {@Authorization(value = "Bearer")})
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return userService.save(userDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{uuid}")
    @ApiOperation(value = "Obtem usuário por UUID", authorizations = {@Authorization(value = "Bearer")})
    public UserDTO findUser(@PathVariable String uuid) {
        return userService.find(uuid);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{uuid}")
    @ApiOperation(value = "Deleta usuário por UUID", authorizations = {@Authorization(value = "Bearer")})
    public void deleteUser(@PathVariable String uuid) {
        userService.delete(uuid);
    }
}
