package br.com.financecontrol.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
@lombok.Builder
@Entity
@Table(name = "financeiro_invitation")
public class Invitation implements Serializable {

    private static final long serialVersionUID = -986073004038590811L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User userInvited;

    public static InvitationBuilder builder() {
        return new InvitationBuilder();
    }
}
