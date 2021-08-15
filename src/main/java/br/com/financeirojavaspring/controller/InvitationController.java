package br.com.financeirojavaspring.controller;

import br.com.financeirojavaspring.dto.InvitationDTO;
import br.com.financeirojavaspring.service.InvitationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/invitation")
@Api(value = "Invitation Controller")
public class InvitationController {

    private final InvitationService invitationService;
    private final ModelMapper modelMapper;

    @Autowired
    public InvitationController(
            InvitationService invitationService, ModelMapper modelMapper) {
        this.invitationService = invitationService;
        this.modelMapper = modelMapper;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/{idUser}")
    @ApiOperation(value = "Cria um convite de usuário para acesso a conta", authorizations = {@Authorization(value = "Bearer")})
    public InvitationDTO createInvitation(@PathVariable String idUser) {
        final var invitation = invitationService.createInvitation(idUser);
        return modelMapper.map(invitation, InvitationDTO.class);
    }
}
