package br.com.financeirojavaspring.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class AccountCredentialsDTO {

	private String username;
	private String password;

}
