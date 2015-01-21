package com.projetandoo.allinshopping.repository;

import java.sql.SQLException;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.projetandoo.allinshopping.models.CEP;
import com.projetandoo.allinshopping.models.Estado;
import com.projetandoo.allinshopping.models.FaixaPreco;

public class FaixaPrecoRepository extends AbstractRepository<FaixaPreco, Long> {

	public FaixaPrecoRepository(final Context context) {
		super(context);
	}

	public FaixaPreco findFaixaPrecoByCEPAndPeso(final Estado origem , final CEP destino) throws SQLException {
		
		return getDao().queryBuilder()
				.where()
				.eq(FaixaPreco.ORIGEM_FIELD_NAME , origem.getId())
				.and()
				.eq(FaixaPreco.DESTINO_FIELD_NAME, destino.getEstado().getId())
				.queryForFirst();
	}
	
	

	public boolean isExist(final FaixaPreco faixapreco) {
		try {
			final FaixaPreco founded = findByFaixaPreco(faixapreco);
			return (founded != null);

		} catch (SQLException e) {
			Log.e("com.projetandoo.allinshopping", String.format(
					"Ocorreu um erro no salvamento do faixa de entrega %s",
					faixapreco), e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(final FaixaPreco faixapreco) {

		try {
			FaixaPreco founded = this.findByFaixaPreco(faixapreco);
			if (founded != null) {
				super.delete(founded);
			}
		} catch (SQLException e) {
			Log.e("com.projetandoo.allinshopping", String.format(
					"Ocorreu um erro no salvamento do faixa de entrega %s",
					faixapreco), e);
			throw new RuntimeException(e);
		}

	}

	public FaixaPreco findByFaixaPreco(final FaixaPreco faixapreco)
			throws SQLException {
		FaixaPreco founded = this.getById(faixapreco.getId());
		return founded;
	}

	@Override
	protected Dao<FaixaPreco, Long> getDao() throws SQLException {
		return this.getDatabase().getFaixaPrecoDao();
	}

	

}
