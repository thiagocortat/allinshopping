package com.projetandoo.allinshopping.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Secao
    implements Serializable
{

    private static final long serialVersionUID = 1L;

	public static final String ID_FIELD_NAME = "id";

	public static final String NOME_FIELD_NAME = "nome";

	public static final String SECAO_PAI_FIELD_NAME = "pai_id";
    
	@SerializedName("id")
    @DatabaseField(id=true,columnName=Secao.ID_FIELD_NAME)
    private Long id;
    
	@SerializedName("nome")
    @DatabaseField(columnName=Secao.NOME_FIELD_NAME)
    private String nome;
    
    @ForeignCollectionField
    private Collection<Produto> produtos = new ArrayList<Produto>();

	@SerializedName("pai")
    @DatabaseField(foreign=true,foreignAutoRefresh=true,columnName = Secao.SECAO_PAI_FIELD_NAME)
    private Secao secaoPai;
    
    @ForeignCollectionField(eager=true)
    private Collection<Secao> subsecoes = new ArrayList<Secao>();

    private Collection<Secao> subsecoesComProdutos;

	public Secao() {
		super();
	}

	public Secao(String nome) {
		this();
		this.id = 0L;
		this.nome = nome;
	}
    
    public boolean isVestuario() {
    	Secao secao = this;
    	boolean  isvestuario = "Vestuário".equalsIgnoreCase(secao.getNome());    	
    	if( !isvestuario ) {
    		secao = secao.getSecaoPai();
    		while( secao != null ) {
    			isvestuario = secao.isVestuario();
	            if(isvestuario){
	            	break;
	            }else{
	            	secao = secao.getSecaoPai();
	            }
    		}
    	}
                
        return isvestuario;
    }
    
    public boolean isCalcado() {
    	Secao secao = this;
    	boolean  isvestuario = "Calçados".equalsIgnoreCase(secao.getNome());    	
    	if( !isvestuario ) {
    		secao = secao.getSecaoPai();
    		while( secao != null ) {
    			isvestuario = secao.isCalcado();
	            if(isvestuario){
	            	break;
	            }else{
	            	secao = secao.getSecaoPai();
	            }
    		}
    	}
                                
        return isvestuario;
    }
    
	public void addProdutos(Collection<Produto> produtos)
    {
    	for(Produto produto : produtos) {
    		produto.setSecao(this);
    		this.produtos.add(produto);
    	}
	}

    public void addSecoes(Collection<Secao> secoes)
    {
        subsecoes.addAll(secoes);
    }

    public Long getId()
    {
        return id;
    }

    public String getNome()
    {
        return nome;
    }

    public List<Produto> getProdutos()
    {
		List<Produto> produtos = new ArrayList<Produto>();
		for (Secao secao : subsecoes) {
        	produtos.addAll(secao.getProdutos());
        }
        produtos.addAll(this.produtos);
        return produtos;
    }

    public Secao getSecaoPai()
    {
        return secaoPai;
    }

    public Collection<Secao> getSubSecoes()
    {
        if (subsecoesComProdutos != null)
            return subsecoesComProdutos;

        subsecoesComProdutos = new ArrayList<Secao>();
		for (Secao secao : this.subsecoes) {
    		if( secao.temProduto() ) {
                subsecoesComProdutos.add(secao);
    		}
    	}
        return subsecoesComProdutos;
//        return subsecoes;
//    	Collection<Secao> subsecoes = new ArrayList<Secao>();
//		for (Secao secao : this.subsecoes) {
//    		if( secao.temProduto() ) {
//    			subsecoes.add(secao);
//    		}
//    	}
//        return subsecoes;
    }

    public String getTitulo()
    {
//        String titulo = "";
//        if( this.getSecaoPai() != null )
//        {
//        	titulo += this.getSecaoPai().getTitulo();
//        }
//        titulo += "/" + this.nome;

        return this.nome;
    }

	public void setNome(String nome)
    {
		this.nome = nome;
    }

	public void setSecaoPai(Secao secao)
    {
		this.secaoPai = secao;
    }

	public boolean temProduto() {
		return !this.getProdutos().isEmpty();
	}
}
