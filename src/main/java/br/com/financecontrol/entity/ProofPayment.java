package br.com.financecontrol.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@lombok.Setter
@lombok.Getter
@lombok.AllArgsConstructor
@Entity
@Table(name = "financeiro_proof_payment")
public class ProofPayment {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;

    private String type;

    @Lob
    private byte[] data;
}