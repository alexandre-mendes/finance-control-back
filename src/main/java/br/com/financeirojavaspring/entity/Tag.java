package br.com.financeirojavaspring.entity;

import br.com.financeirojavaspring.builder.TagBuilder;

import javax.persistence.*;
import java.io.Serializable;

@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@Entity
@Table(name = "financeiro_tag")
public class Tag implements Serializable {

    private static final long serialVersionUID = -7351211972245308981L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_seq")
    private Long id;

    @Column(unique = true)
    private String uuid;

    private String title;

    @ManyToOne
    @JoinColumn(name = "id_account")
    private Account account;

    public static TagBuilder builder() {
        return new TagBuilder();
    }
}
