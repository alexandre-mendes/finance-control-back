package br.com.financeirojavaspring.dto;

import br.com.financeirojavaspring.builder.dto.AccountCredentialsDTOBuilder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
public class AccountCredentialsDTO {

	private String username;
	private String password;

	public static AccountCredentialsDTOBuilder builder() {
		return new AccountCredentialsDTOBuilder();
	}
}
