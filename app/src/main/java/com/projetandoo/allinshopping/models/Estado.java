package com.projetandoo.allinshopping.models;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Estado implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String UF_FIELD_NAME = "uf";
	public static final String NOME_FIELD_NAME = "nome";
	public static final String ID_FIELD_NAME = "id";

    @Expose
	@DatabaseField(id = true  , columnName = Estado.ID_FIELD_NAME )
	private Long id;

	@DatabaseField(columnName = Estado.NOME_FIELD_NAME)
	private String nome;
	
	@DatabaseField(columnName = Estado.UF_FIELD_NAME)
	private String uf;

	public Estado() {
	}

	public Estado(String uf, String nome) {
		this();
		this.uf = uf;
		this.nome = nome;
	}
	
	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getUf() {
		return uf;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	@Override
	public String toString() {
		return nome;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Estado) {
			Estado estado = (Estado) o;
			return this.uf.equalsIgnoreCase(estado.uf);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (this.uf.hashCode() * 32) >> 4;
	}

	public void setId(long id) {
		this.id = id;
	}

	
}
