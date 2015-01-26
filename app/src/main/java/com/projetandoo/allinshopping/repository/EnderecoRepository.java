package com.projetandoo.allinshopping.repository;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.projetandoo.allinshopping.models.Endereco;
import com.projetandoo.allinshopping.models.Estado;

public class EnderecoRepository extends AbstractRepository<Endereco, Long> {

	public EnderecoRepository(Context context) {
		super(context);
	}

    public List<Endereco> getAll()
    {
        try
        {
            return super.getDatabase().getEnderecoDao().queryForAll();
        }
        catch (SQLException sqlexception)
        {
            throw new RuntimeException(sqlexception);
        }

    }

	@Override
	protected Dao<Endereco, Long> getDao() throws SQLException {
		return super.getDatabase().getEnderecoDao();
	}

}
