package com.projetandoo.allinshopping.models;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import org.apache.commons.validator.GenericValidator;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.projetandoo.allinshopping.models.constraints.ValidationConstraint;

@DatabaseTable
public class Endereco extends ValidationConstraint implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String BAIRRO_FIELD_NAME = "bairro";

	public static final String CEP_FIELD_NAME = "cep";

	public static final String CIDADE_FIELD_NAME = "cidade";

	public static final String ESTADO_FIELD_NAME = "estado";

	public static final String LOGRADOURO_FIELD_NAME = "logradouro"; 

	public static final String TELEFONE_FIELD_NAME = "telefone"; 

	public static final String CELULAR_FIELD_NAME = "celular"; 

	private static final String BACKOFFICE_ID_FIELD_NAME = "backoffice_id";

	private static final String ID_FIELD_NAME = "id";

//    public static final String CLIENTE_FIELD_NAME = "cliente_id";
//
//    @DatabaseField(columnName = Endereco.CLIENTE_FIELD_NAME, foreign = true, foreignAutoRefresh = true)
//    private Endereco endereco;

	@DatabaseField(columnName = Endereco.ID_FIELD_NAME, generatedId = true)
	private Long internalId;

    @Expose
	@SerializedName("id")
	@DatabaseField(columnName = Endereco.BACKOFFICE_ID_FIELD_NAME)
	private Long backofficeId;

    @Expose
	@SerializedName("bairro")
	@DatabaseField(columnName = Endereco.BAIRRO_FIELD_NAME)
	private String bairro;

    @Expose
	@SerializedName("cep")
	@DatabaseField(columnName = Endereco.CEP_FIELD_NAME)
	private String cep;

    @Expose
	@SerializedName("cidade")
	@DatabaseField(columnName = Endereco.CIDADE_FIELD_NAME)
	private String cidade;

    @Expose
	@SerializedName("estado")
	@DatabaseField(columnName = Endereco.ESTADO_FIELD_NAME, foreign = true, foreignAutoRefresh = true)
	private Estado estado;

    @Expose
	@SerializedName("logradouro")
	@DatabaseField(columnName = Endereco.LOGRADOURO_FIELD_NAME)
	private String logradouro;

    @Expose
	@SerializedName("telefone")
	@DatabaseField(columnName = Endereco.TELEFONE_FIELD_NAME)
	private String telefone;

    @Expose
	@SerializedName("celular")
	@DatabaseField(columnName = Endereco.CELULAR_FIELD_NAME)
	private String celular;

	public String getBairro() {
		return bairro;
	}

	public String getCep() {
		return cep;
	}

	public String getCidade() {
		return cidade;
	}

	public Estado getEstado() {
		return estado;
	}

	public Long getId() {
		return internalId;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setEstado(final Estado estado) {
		this.estado = estado;
	}

	public void setId(final Long id) {
		this.internalId = id;
	}

	public String getCelular() {
		return this.celular;
	}

	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(final String telefone) {
		this.telefone = telefone;
	}

	public void setCelular(final String celular) {
		this.celular = celular;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	@Override
	protected void validate() {
		if (GenericValidator.isBlankOrNull(logradouro)) {
			add("Endereço é obrigatório");
		}
		if (GenericValidator.isBlankOrNull(bairro)) {
			add("Bairro é obrigatório");
		}
		if (GenericValidator.isBlankOrNull(cep)) {
			add("CEP é obrigatório");
		}
		if (estado == null || GenericValidator.isBlankOrNull(estado.getUf())) {
			add("Estado é obrigatório");
		}
		if (GenericValidator.isBlankOrNull(cidade)) {
			add("Cidade é obrigatório");
		}

	}

}
