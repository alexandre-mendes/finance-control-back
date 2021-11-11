package br.com.financeirojavaspring.dto;

import java.io.Serializable;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
public class TagDTO implements Serializable {

    private static final long serialVersionUID = 4141267503031460885L;

    private String uuid;
    private String title;
}
