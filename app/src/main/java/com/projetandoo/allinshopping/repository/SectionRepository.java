package com.projetandoo.allinshopping.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.projetandoo.allinshopping.models.Secao;

public class SectionRepository extends AbstractRepository<Secao, Long>
{

    public SectionRepository(final Context context)
    {
        super(context);
    }

    public List<Secao> getSections() throws SQLException {
    	
        final List<Secao> secoes = getDatabase().getSecaoDao()
					        .queryBuilder()					        
					        .where()
					        .isNull(Secao.SECAO_PAI_FIELD_NAME)		
					        .query();
        
        final List<Secao> comProduto = new ArrayList<Secao>();
        
        for( final Secao secao : secoes ){
        	if( secao.temProduto() ) {
        		comProduto.add(secao);
        	}
        }
        
        
        return comProduto;
    }

	@Override
	protected Dao<Secao, Long> getDao() throws SQLException {
		return getDatabase().getSecaoDao();
	}
}
