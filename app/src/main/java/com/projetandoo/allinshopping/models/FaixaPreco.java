package com.projetandoo.allinshopping.models;

import java.io.Serializable;
import java.math.BigDecimal;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable
public class FaixaPreco
    implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	public static final String ID_FIELD = "id";
	public static final String PESO_INICIAL_FIELD = "pesoinicial";
	public static final String PESO_FINAL_FIELD = "pesofinal";
	public static final String PRECO_FIELD = "preco";
	public static final String DESTINO_FIELD_NAME = "destino";
	public static final String ORIGEM_FIELD_NAME = "origem";
	
    
    @DatabaseField(id=true,columnName=FaixaPreco.ID_FIELD)
    private Long id;

	@SerializedName("preco")
    @DatabaseField(columnName=FaixaPreco.PRECO_FIELD)
    private BigDecimal preco;
	
	@SerializedName("destino")
	@DatabaseField(columnName = FaixaPreco.DESTINO_FIELD_NAME, foreign = true, foreignAutoRefresh = true)
	private Estado destino; 
	
	@SerializedName("origem")
	@DatabaseField(columnName = FaixaPreco.ORIGEM_FIELD_NAME, foreign = true, foreignAutoRefresh = true)
	private Estado origem; 
   
    public Long getId()
    {
        return id;
    }

    public BigDecimal getPreco()
    {
        return preco;
    }

	public Estado getDestino() {
		return destino;
	}
	
	public Estado getOrigem() {
		return origem;
	}
	
}
