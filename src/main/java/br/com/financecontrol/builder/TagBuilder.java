package br.com.financecontrol.builder;

import br.com.financecontrol.entity.Account;
import br.com.financecontrol.entity.Tag;

public class TagBuilder implements Builder {
    private Long id;
    private String uuid;
    private String titulo;
    private Account account;

    public TagBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public TagBuilder uuid(String uuid) {
        this.uuid = uuid;
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
        return new Tag(id, uuid, titulo, account);
    }
}
