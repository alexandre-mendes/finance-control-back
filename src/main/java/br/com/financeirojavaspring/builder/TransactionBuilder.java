package br.com.financeirojavaspring.builder;

import br.com.financeirojavaspring.entity.RecordCreditor;
import br.com.financeirojavaspring.entity.Transaction;
import br.com.financeirojavaspring.enums.TypeTransaction;

public class TransactionBuilder implements Builder {

    private Long id;
    private String uuid;
    private String codeTransaction;
    private TypeTransaction typeTransaction;
    private RecordCreditor recordCreditor;

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

    public TransactionBuilder recordCreditor(RecordCreditor recordCreditor) {
        this.recordCreditor = recordCreditor;
        return this;
    }

    @Override
    public Transaction build() {
        return new Transaction(id, uuid, codeTransaction, typeTransaction, recordCreditor);
    }
}
