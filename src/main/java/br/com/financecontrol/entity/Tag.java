package br.com.financecontrol.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.Builder
@Entity
@Table(name = "financeiro_tag")
public class Tag implements Serializable {

    private static final long serialVersionUID = -7351211972245308981L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "id_account")
    private Account account;

    public static TagBuilder builder() {
        return new TagBuilder();
    }
}
