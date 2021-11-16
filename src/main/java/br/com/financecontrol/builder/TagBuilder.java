package br.com.financecontrol.builder;

import br.com.financecontrol.entity.Account;
import br.com.financecontrol.entity.Tag;

public class TagBuilder implements Builder {
    private String id;
    private String titulo;
    private Account account;

    public TagBuilder id(String id) {
        this.id = id;
        return this;
    }

    public TagBuilder titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public TagBuilder account(Account account) {
        this.account = account;
        return this;
    }

    @Override
    public Tag build() {
        return new Tag(id, titulo, account);
    }
}
