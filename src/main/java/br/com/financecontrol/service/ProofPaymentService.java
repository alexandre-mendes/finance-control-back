package br.com.financecontrol.service;

import br.com.financecontrol.entity.ProofPayment;
import br.com.financecontrol.exception.EntityNotFoundException;
import br.com.financecontrol.repository.ProofPaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ProofPaymentService {

    private final ProofPaymentRepository proofPaymentRepository;

    public ProofPaymentService(ProofPaymentRepository proofPaymentRepository) {
        this.proofPaymentRepository = proofPaymentRepository;
    }

    public ProofPayment save(final MultipartFile file) throws IOException {
        final ProofPayment proofPayment = new ProofPayment(null, file.getOriginalFilename(), file.getContentType(), file.getBytes());
        return proofPaymentRepository.save(proofPayment);
    }

    public ProofPayment find(final String id) {
        return proofPaymentRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
