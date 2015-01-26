package com.projetandoo.allinshopping.models;

import java.io.File;
import java.io.Serializable;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable
public class Imagem implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private static final String URL_FIELD_NAME = "url";

	private static final String FILENAME_FIELD_NAME = "filename";

	private static final String PRODUTO_FIELD_NAME = "produto_id";

    private static final String ID_FIELD_NAME = "id";

	@SerializedName(ID_FIELD_NAME)
    @DatabaseField(generatedId = true , columnName = Imagem.ID_FIELD_NAME)
	private Long id;
	
	@SerializedName("url")
	@DatabaseField(columnName = Imagem.URL_FIELD_NAME)
	private String url;
	
	@DatabaseField(columnName = Imagem.FILENAME_FIELD_NAME)
	private String fileName = null;
	
	@DatabaseField(columnName = Imagem.PRODUTO_FIELD_NAME, foreign = true, foreignAutoRefresh = true)
	private Produto produto;

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public String getURL() {
		return this.url;
	}

	public Produto getProduto() {
		return produto;
	}

	public Long getId() {
		return id;
	}

	public void setFileName(String imagepath) {
		this.fileName = imagepath;
	}

	public void apagar() {
		File imagem = new File(this.fileName);
		imagem.delete();
		
	}

	public String getFileName() {
		return this.fileName;
	}
}
