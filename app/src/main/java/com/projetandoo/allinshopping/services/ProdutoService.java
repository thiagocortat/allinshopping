package com.projetandoo.allinshopping.services;

import java.io.File;
import java.sql.SQLException;

import android.content.Context;

import com.projetandoo.allinshopping.models.Atributo;
import com.projetandoo.allinshopping.models.Imagem;
import com.projetandoo.allinshopping.models.Produto;
import com.projetandoo.allinshopping.repository.AtributoRepository;
import com.projetandoo.allinshopping.repository.ImagemRepository;
import com.projetandoo.allinshopping.repository.ProductRepository;
import com.projetandoo.allinshopping.utilities.Constante;

public class ProdutoService {
	private ProductRepository PRODUCT_REPOSITORY;
	private ImagemRepository IMAGEM_REPOSITORY;
	private AtributoRepository ATRIBUTO_REPOSITORY;

	public ProdutoService(Context context) {
		PRODUCT_REPOSITORY = new ProductRepository(context);
		IMAGEM_REPOSITORY = new ImagemRepository(context);
		ATRIBUTO_REPOSITORY = new AtributoRepository(context);
	}

	public void save(Produto produto) {
		try {
			if (PRODUCT_REPOSITORY.exists(produto)) {
				IMAGEM_REPOSITORY.delete(produto);
				ATRIBUTO_REPOSITORY.delete(produto);
				PRODUCT_REPOSITORY.delete(produto);
			}
			PRODUCT_REPOSITORY.insert(produto);

			for (Imagem imagem : produto.getImagens()) {
				IMAGEM_REPOSITORY.insert(imagem);
			}

			for (Atributo atributo : produto.getAtributos()) {
				ATRIBUTO_REPOSITORY.insert(atributo);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void removeAll() {
		try {
            PRODUCT_REPOSITORY.removeAll();
			File directory = new File(Constante.SDCARD_ALLINSHOPP_IMAGES);
			if (directory.exists()) {
				directory.delete();
			}
			directory.mkdir();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

}
