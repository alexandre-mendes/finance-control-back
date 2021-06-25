package br.com.financeirojavaspring.controller;

import br.com.financeirojavaspring.dto.InvitationDTO;
import br.com.financeirojavaspring.model.Invitation;
import br.com.financeirojavaspring.service.InvitationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/invitation")
@Api(value = "Invitation Controller")
public class InvitationController {

    @Autowired
    private InvitationService invitationService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/{idUser}")
    @ApiOperation(value = "Cria um convite de usuário para acesso a conta", authorizations = {@Authorization(value = "Bearer")})
    public InvitationDTO createInvitation(@PathVariable String idUser) {
        return invitationService.createInvitation(idUser);
    }
}
