package br.com.financecontrol.controller;

import br.com.financecontrol.entity.ProofPayment;
import br.com.financecontrol.repository.ProofPaymentRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/comprovantes")
@Api(value = "Comprovante Controller")
public class ProofPaymentController {

    private final ProofPaymentRepository proofPaymentRepository;

    public ProofPaymentController(ProofPaymentRepository proofPaymentRepository) {
        this.proofPaymentRepository = proofPaymentRepository;
    }

    @PostMapping
    @ApiOperation(value = "Realiza upload de um comprovante.", authorizations = {@Authorization(value = "Bearer")})
    public void upload(@RequestBody final MultipartFile file) throws IOException {
        final ProofPayment proofPayment = new ProofPayment(null, file.getOriginalFilename(), file.getContentType(), file.getBytes());
        proofPaymentRepository.save(proofPayment);
    }
}
