package com.projetandoo.allinshopping.models;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Atributo implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String DESCRICAO_FIELD_NAME = "descricao";

    public static final String ID_PRESTASHOP_FIELD_NAME = "atributo_id";

    public static final String PRODUTO_FIELD_NAME = "produto_id";
	
	@DatabaseField(generatedId = true)
	private Long id_attribute;
	
	@SerializedName("id")
	@DatabaseField(columnName = Atributo.ID_PRESTASHOP_FIELD_NAME)
	private Long id;
	
	@SerializedName("descricao")
	@DatabaseField(columnName = Atributo.DESCRICAO_FIELD_NAME)
	private String descricao;
	
	@DatabaseField(columnName = Atributo.PRODUTO_FIELD_NAME, foreign = true, foreignAutoRefresh = true)
	private Produto produto;

	public void setProduto(Produto produto) {
		this.produto = produto;		
	}
	
	public String getDescricao() {
		return descricao;
	}

    @Override
    public boolean equals(Object o) {
        if( o instanceof Atributo ){
            Atributo other = (Atributo) o;
            return other.id.equals(this.id);
        }
        return false;
    }

    public int hashCode() {
        return this.id.hashCode();
    }
}
