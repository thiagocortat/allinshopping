package com.projetandoo.allinshopping.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.projetandoo.allinshopping.utilities.Constante;
import com.projetandoo.allinshopping.utilities.PriceUtilities;

@DatabaseTable
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String ID_LOJA_PRESTASHOP_FIELD_NAME = "id_loja"; 
	private static final String NOME_FIELD_NAME = "nome";
	private static final String PRECO_FIELD_NAME = "preco";
	private static final String SECAO_FIELD_NAME = "secao";
	private static final String DESCRICAO_FIELD_NAME = "descricao"; 
	private static final String DESCRICAO_BREVE_FIELD_NAME = "descricaobreve"; 
	private static final String PESO_FIELD_NAME = "peso";


    @Expose
	@SerializedName("id")
	@DatabaseField(id = true)
	private Long id; 

	@SerializedName("shop_id")
	@DatabaseField(columnName = Produto.ID_LOJA_PRESTASHOP_FIELD_NAME)
	private Long idLoja;


	@SerializedName("nome")
	@DatabaseField(columnName = Produto.NOME_FIELD_NAME)
	private String nome;

    @SerializedName("preco")
	@DatabaseField(columnName = Produto.PRECO_FIELD_NAME)
	private BigDecimal preco;

    @SerializedName("peso")
	@DatabaseField(columnName = Produto.PESO_FIELD_NAME)
	private BigDecimal peso;

    @SerializedName("secao")
	@DatabaseField(columnName = Produto.SECAO_FIELD_NAME, foreign = true, foreignAutoRefresh = true)
	private Secao secao;

    @SerializedName("descricao")
	@DatabaseField(columnName = Produto.DESCRICAO_FIELD_NAME)
	private String descricao;

    @SerializedName("descricaoBreve")
	@DatabaseField(columnName = Produto.DESCRICAO_BREVE_FIELD_NAME)
	private String descricaoCurto;

    @SerializedName("imagens")
	@ForeignCollectionField(eager=true)
    private Collection<Imagem> imagens = new ArrayList<Imagem>();

    @SerializedName("atributos")
	@ForeignCollectionField(eager=true,orderColumnName = Atributo.DESCRICAO_FIELD_NAME)
	private Collection<Atributo> atributos = new ArrayList<Atributo>();

	public Produto() {
		super();
	}


	@Override
	public boolean equals(Object obj) {
		boolean flag = false;
		if (obj instanceof Produto) {
			final Produto other = (Produto)obj;
			flag =(this.id == other.id);
		}
		return flag;
	}



	public Long getId() {
		return id;
	}

	public Long getIdLoja() {
		return idLoja;
	}

	public String getDefaultImage() {
		final List<Imagem> imagens = new ArrayList<Imagem>();
		imagens.addAll(this.imagens);
		return imagens.get(0).getFileName();
	}

	public BigDecimal getMargemGestor() {
		return preco
				.divide(Constante.PERCENTUAL_GESTOR, 2, BigDecimal.ROUND_UP)
				.subtract(preco);
	}

	public BigDecimal getMargemLider() {
		return preco.divide(Constante.PERCENTUAL_LIDER, 2, BigDecimal.ROUND_UP)
				.subtract(preco);
	}

	public BigDecimal getMargemTI() {
		return preco.divide(Constante.PERCENTUAL_TI, 2, BigDecimal.ROUND_UP)
				.subtract(preco);
	}

	public BigDecimal getMargemVendedoras() {
		return preco.divide(Constante.PERCENTUAL_VENDEDORAS, 2,
				BigDecimal.ROUND_UP).subtract(preco);
	}

	public BigDecimal getMargemProjetandoo() {
		return preco.divide(Constante.PERCENTUAL_PROJETANDOO, 2,
				BigDecimal.ROUND_UP).subtract(preco);
	}

	public Long getPeso() {
		return Long.valueOf(0L);
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public BigDecimal getPrecoVenda() {
		return preco
				.add(this.getMargemProjetandoo())
				.add(getMargemTI())
				.add(getMargemGestor())
				.add(getMargemVendedoras())
				.add(getMargemLider())
				.add(getFrete())
				.divide(Constante.PERCENTUAL_GATEWAY_PAGAMENTO, 2, BigDecimal.ROUND_UP)
				.add(Constante.TAXA_GATEWAY_PAGAMENTO);
	}

	public String getTitulo() {
		return nome;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	public void setId( Long id) {
		this.id = id;
	}
	
	public void setSecao(Secao secao) {
		this.secao = secao;
	}

	public BigDecimal getFrete() {

		return PriceUtilities.getFrete();
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setDescricaoBreve(String descricaoCurto) {
		this.descricaoCurto = descricaoCurto;
	}

	public String getDescricaoBreve() {
		return descricaoCurto;
	}

	public String getDescricao() {
		return descricao;
	}

	public Secao getSecao() {
		return secao;
	}

	public void apagarImagem() {
		for( final Imagem imagem : this.imagens ){
			imagem.apagar();
		}
	}

	public Collection<Imagem> getImagens() {
		return this.imagens;
	}

	public Collection<Atributo> getAtributos() {
		return this.atributos;
	}
	
	@Override
	public String toString() {
		return String.format("[id:%s nome:%s]",this.id,this.nome);
	}

    public boolean temAtributos() {
        return this.atributos != null && !this.atributos.isEmpty();
    }
}
