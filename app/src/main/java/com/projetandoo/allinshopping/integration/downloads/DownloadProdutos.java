package com.projetandoo.allinshopping.integration.downloads;

import java.util.List;

import com.projetandoo.allinshopping.exceptions.IntegrationException;
import com.projetandoo.allinshopping.models.Atributo;
import com.projetandoo.allinshopping.models.Imagem;
import com.projetandoo.allinshopping.models.Produto;

public class DownloadProdutos extends AbstractDownload<Produto> {
	
	public DownloadProdutos(String username, String password) {
		super(username,password);
	}

	@Override
	public List<Produto> list() throws IntegrationException { //NOPMD
		
			List<Produto> produtos =  super.list();
			for(Produto produto : produtos){
				for( Imagem imagem : produto.getImagens() ) {
					imagem.setProduto(produto);
				}
				for(Atributo atributo : produto.getAtributos() ){
					atributo.setProduto(produto);
				}
			}
			
			return produtos;

	}

}
