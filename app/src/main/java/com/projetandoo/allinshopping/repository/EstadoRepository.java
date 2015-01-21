package com.projetandoo.allinshopping.repository;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.projetandoo.allinshopping.models.Estado;

public class EstadoRepository extends AbstractRepository<Estado,Long>
{

    public EstadoRepository(Context context)
    {
        super(context);
    }

    public Estado findByUF(String uf)
    {
        try
        {
           return super.getDatabase()
            			  .getEstadoDao()
            			  .queryBuilder()
            			  .where()
            			  .eq(Estado.UF_FIELD_NAME , uf)
            			  .queryForFirst();
        }catch (SQLException sqlexception){
            throw new RuntimeException(sqlexception);
        }
    }

    public List<Estado> getAll()
    {
        try
        {
            return super.getDatabase().getEstadoDao().queryForAll();
        }
        catch (SQLException sqlexception)
        {
            throw new RuntimeException(sqlexception);
        }
        
    }

	@Override
	protected Dao<Estado, Long> getDao() throws SQLException {
		return getDatabase().getEstadoDao();
	}
}
