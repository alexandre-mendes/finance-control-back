package br.com.financecontrol.dto;

import br.com.financecontrol.builder.AccountCredentialsDTOBuilder;

import java.io.Serializable;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
public class AccountCredentialsDTO implements Serializable {

	private static final long serialVersionUID = -6080526832143482193L;

	private String username;
	private String password;

	public static AccountCredentialsDTOBuilder builder() {
		return new AccountCredentialsDTOBuilder();
	}
}
