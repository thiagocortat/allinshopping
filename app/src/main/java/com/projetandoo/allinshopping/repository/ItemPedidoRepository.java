package com.projetandoo.allinshopping.repository;

import java.sql.SQLException;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.projetandoo.allinshopping.models.ItemPedido;

public class ItemPedidoRepository extends AbstractRepository<ItemPedido, Long> {

	public ItemPedidoRepository(Context context) {
		super(context);
	}

	@Override
	protected Dao<ItemPedido, Long> getDao() throws SQLException {
		return getDatabase().getItemDao();
	}

}
