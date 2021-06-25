package br.com.financeirojavaspring.model;

import javax.persistence.*;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
@Entity
@Table(name = "financeiro_invitation")
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invitation_seq")
    @Column(name = "id_invitation")
    private Long id;
    @Column(unique = true)
    private String uuid;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User userInvited;
}
