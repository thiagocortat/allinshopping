package com.projetandoo.allinshopping.repository;

import java.sql.SQLException;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.projetandoo.allinshopping.models.DadosPagamento;

public class DadosPagamentoRepository
		extends
			AbstractRepository<DadosPagamento, Long> {

	public DadosPagamentoRepository(Context context) {
		super(context);
	}

	@Override
	protected Dao<DadosPagamento, Long> getDao() throws SQLException {
		return super.getDatabase().getDadosPagamento();
	}

}
