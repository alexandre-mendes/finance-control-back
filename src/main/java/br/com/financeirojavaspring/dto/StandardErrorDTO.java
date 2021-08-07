package br.com.financeirojavaspring.dto;

import java.io.Serializable;
import java.time.Instant;

@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class StandardErrorDTO implements Serializable {

    private static final long serialVersionUID = 2030290299038420508L;

    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
