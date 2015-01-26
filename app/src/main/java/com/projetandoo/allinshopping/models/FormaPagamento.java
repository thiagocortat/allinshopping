package com.projetandoo.allinshopping.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable
public class FormaPagamento
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String ID_FIELD = "id";
    public static final String NOME_FIELD = "nome";
    public static final String PAYMENT_METHOD_FIELD = "metodo";

    @Expose
    @SerializedName(ID_FIELD)
    @DatabaseField(id = true, columnName = FormaPagamento.ID_FIELD)
    private Long id;

    @SerializedName("descricao")
    @DatabaseField(columnName = FormaPagamento.NOME_FIELD)
    private String nome;

    public FormaPagamento() {
    }

    public FormaPagamento(Long id, String nome) {
        this();
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
