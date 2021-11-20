package br.com.financecontrol.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@lombok.Setter
@lombok.Getter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@Entity
@Table(name = "proof_payment")
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
