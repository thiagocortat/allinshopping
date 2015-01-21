package com.projetandoo.allinshopping.repository;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.projetandoo.allinshopping.models.Produto;
import com.projetandoo.allinshopping.models.Secao;

public class ProductRepository extends AbstractRepository<Produto, Long> {

	public ProductRepository(Context context) {
		super(context);
	}

	public List<Produto> getBySales() throws SQLException {
		return getDatabase().getProdutoDao().queryForAll();
	}

	public List<Produto> getBySection(Secao secao) throws SQLException {
		Log.i("com.projetandoo.allinshopping",
				"Listando os produtos cadastrados na secao " + secao);
		return getDatabase().getSecaoDao().queryForId(secao.getId())
				.getProdutos();
	}

	public void remove(Produto produto) throws SQLException {
		produto.apagarImagem();
		getDatabase().getProdutoDao().delete(produto);
	}

	public void removeAll() throws SQLException {

		this.getDatabase().getProdutoDao()
				.executeRawNoArgs("DELETE from produto");
	}

	@Override
	protected Dao<Produto, Long> getDao() throws SQLException {
		return getDatabase().getProdutoDao();
	}

	public boolean exists(Produto produto) throws SQLException {
		return (getDatabase().getProdutoDao().queryForId(produto.getId()) != null);
	}

}
