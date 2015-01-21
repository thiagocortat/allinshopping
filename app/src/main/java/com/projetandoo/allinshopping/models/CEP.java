package com.projetandoo.allinshopping.models;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class CEP implements Serializable { 

	private static final long serialVersionUID = 1L;

	public static final String FIM_FIELD_NAME = "fim";

	public static final String ID_FIELD_NAME = "id";

	public static final String INICIO_FIELD_NAME = "inicio";

	public static final String UF_FIELD_NAME = "estado_id";

	@SerializedName("fim")
	@DatabaseField(columnName = CEP.FIM_FIELD_NAME)
	private Long fim;

	@SerializedName("id")
	@DatabaseField(id = true, columnName = CEP.ID_FIELD_NAME)
	private Long id; 

	@SerializedName("inicial")
	@DatabaseField(columnName = CEP.INICIO_FIELD_NAME)
	private Long inicio;

	@SerializedName("estado")
	@DatabaseField(columnName = CEP.UF_FIELD_NAME, foreign = true, foreignAutoRefresh = true )
	private Estado estado; 
	
	public Long getFim() {
		return fim;
	}

	public Long getId() {
		return id;
	}

	public Long getInicio() {
		return inicio;
	}

	public Estado getEstado() {
		return estado;
	}
	
	@Override
	public boolean equals(final Object obj) {
		boolean isEquals = false;
		if( obj instanceof CEP ){
			final CEP other = (CEP) obj;
			isEquals = other.id == this.id;
		}
		return isEquals;
	}
	
	@Override
	public int hashCode() {
		return ((this.inicio.hashCode() * 32) >> 4) + ((this.fim.hashCode() * 32) >> 4) >> 5;
	}
}
