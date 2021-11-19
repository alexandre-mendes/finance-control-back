package br.com.financecontrol.controller;

import br.com.financecontrol.entity.ProofPayment;
import br.com.financecontrol.service.ProofPaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/comprovantes")
@Api(value = "Comprovante Controller")
public class ProofPaymentController {

    private final ProofPaymentService proofPaymentService;

    public ProofPaymentController(ProofPaymentService proofPaymentService) {
        this.proofPaymentService = proofPaymentService;
    }

    @PostMapping
    @ApiOperation(value = "Realiza upload de um comprovante.", authorizations = {@Authorization(value = "Bearer")})
    public void upload(@RequestBody final MultipartFile file) throws IOException {
        proofPaymentService.save(file);
    }

    @GetMapping(value = "/{fileId}")
    @ApiOperation(value = "Realiza download de um comprovante.", authorizations = {@Authorization(value = "Bearer")})
    public ResponseEntity<byte[]> download(@PathVariable final String fileId) {
        final ProofPayment proof = proofPaymentService.find(fileId);
        return ResponseEntity.ok().
                header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + proof.getName() + "\"").
                body(proof.getData());
    }
}
