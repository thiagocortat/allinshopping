package com.projetandoo.allinshopping.repository;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.projetandoo.allinshopping.database.DbHelper;
import com.projetandoo.allinshopping.database.DbHelperFactory;
import com.projetandoo.allinshopping.exceptions.DatabaseOperationException;

public abstract class AbstractRepository<E,T>
{

    private final DbHelper helper;
    private int count;

    public AbstractRepository(final Context context)
    {
        this.helper = DbHelperFactory.getDbHelper(context);
    }

    public ConnectionSource getConnectionSource()
    {
        return helper.getConnectionSource();
    }

    protected DbHelper getDatabase()
    {
        return helper;
    }

    public void insert(final E e) {    	    	
    	try {
			this.getDao().create(e);
		} catch (SQLException sqlexp) {
			throw new DatabaseOperationException("Falha ao inserir o valor " + e + " no banco de dados", sqlexp);
		}
    }
    
    public void update(final E e){
    	try {
			this.getDao().update(e);
		} catch (SQLException sqlexp) {
			throw new DatabaseOperationException("Falha ao atualizar o valor " + e + " no banco de dados", sqlexp);
		}
    }
    
    public void delete(final E e){
    	try {
			this.getDao().delete(e);
		} catch (SQLException sqlexp) {
			throw new DatabaseOperationException("Falha ao excluir o valor " + e + " no banco de dados", sqlexp);
		}
    }
    
    public E getById(final T id) {
    	try {

    		if( id == null ) {
    			return null;    			
    		} else {
    			return this.getDao().queryForId(id);
    		}
    		
		} catch (SQLException sqlexp) {
			throw new DatabaseOperationException("Falha ao pesquisar o id " + id + " no banco de dados", sqlexp);
		}
    }
    
    public List<E> list() {
    	try {
			return this.getDao().queryForAll();
		} catch (SQLException sqlexp) {
			throw new DatabaseOperationException("Falha ao listar os valores do banco de dados", sqlexp);
		}
    }
    
    public long count() {
		try {
			return this.getDao().countOf();
		} catch (SQLException e) {
			throw new DatabaseOperationException("Falha ao listar os valores do banco de dados", e);
		}
	}
    
    protected abstract Dao<E,T> getDao() throws SQLException;

}
