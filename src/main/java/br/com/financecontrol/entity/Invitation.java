package br.com.financecontrol.entity;

import br.com.financecontrol.builder.InvitationBuilder;
import javax.persistence.*;
import java.io.Serializable;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
@Entity
@Table(name = "financeiro_invitation")
public class Invitation implements Serializable {

    private static final long serialVersionUID = -986073004038590811L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invitation_seq")
    @Column(name = "id_invitation")
    private Long id;
    @Column(unique = true)
    private String uuid;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User userInvited;

    public static InvitationBuilder builder() {
        return new InvitationBuilder();
    }
}
