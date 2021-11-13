package br.com.financecontrol.builder;

import br.com.financecontrol.entity.Transaction;
import br.com.financecontrol.enums.TypeTransaction;

public class TransactionBuilder implements Builder {

    private Long id;
    private String uuid;
    private String codeTransaction;
    private TypeTransaction typeTransaction;

    private TransactionBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public TransactionBuilder uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public TransactionBuilder codeTransaction(String codeTransaction) {
        this.codeTransaction = codeTransaction;
        return this;
    }

    public TransactionBuilder typeTransaction(TypeTransaction typeTransaction) {
        this.typeTransaction = typeTransaction;
        return this;
    }

    @Override
    public Transaction build() {
        return new Transaction(id, uuid, codeTransaction, typeTransaction);
    }
}
