
package com.projetandoo.allinshopping.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.projetandoo.allinshopping.exceptions.InitializationDbException;
import com.projetandoo.allinshopping.models.DadosPagamento;
import com.projetandoo.allinshopping.models.ItemPedido;
import com.projetandoo.allinshopping.models.Pedido;


public class ShoppingCartRepository extends AbstractRepository
{

    private final Dao<ItemPedido,Long> ITEM_DAO;
    private final Dao<DadosPagamento, Long> PAGTO_DAO;
    private final Dao<Pedido,Long> PEDIDO_DAO;

    public ShoppingCartRepository(Context context)
    {
        super(context);
        try
        {
            ITEM_DAO = getDatabase().getItemDao();
            PEDIDO_DAO = getDatabase().getPedidoDao();
            PAGTO_DAO = getDatabase().getDadosPagamento();
            return;
        }
        catch (SQLException sqlexception)
        {
            throw new InitializationDbException("Erro ao inicializar o repositorio ShoppingCartRepository " + sqlexception.getMessage() , sqlexception);
        }
    }

    public void insert(DadosPagamento dadospagamento)
    {
        try
        {
            Log.i("com.projetandoo.allinshopping", String.format("Salvando os dados de pagamento %s",dadospagamento));
            PAGTO_DAO.createIfNotExists(dadospagamento);
            Log.i("com.projetandoo.allinshopping", String.format("Dados de pagamento %s salvo com sucesso",dadospagamento));
        }
        catch (SQLException sqlexception)
        {
            Log.e("com.projetandoo.allinshopping", "erro ao salvar o pedido " + sqlexception.getMessage(), sqlexception);
            throw new RuntimeException(sqlexception);
        }
    }

    public void insert(ItemPedido itempedido)
    {
        try
        {
            Log.i("com.projetandoo.allinshopping", String.format("Salvando o item %s",itempedido));
            ITEM_DAO.createIfNotExists(itempedido);
            Log.i("com.projetandoo.allinshopping", String.format("Item %s salvo com sucesso",itempedido));
            return;
        }
        catch (SQLException sqlexception)
        {
            Log.e("com.projetandoo.allinshopping", "erro ao salvar o item " + sqlexception.getMessage(), sqlexception);
            throw new RuntimeException(sqlexception);
        }
    }

    public void insert(Pedido pedido)
    {
        try
        {
            Log.i("com.projetandoo.allinshopping", String.format("Salvando o pedido %s",pedido));
            PEDIDO_DAO.createIfNotExists(pedido);
            Log.i("com.projetandoo.allinshopping", String.format("Pedido %s salvo",pedido));
            for(ItemPedido item : pedido.getItens()) {
            	item.setPedido(pedido);
            	insert(item);
            }
            
        }
        catch (SQLException sqlexception)
        {
            Log.e("com.projetandoo.allinshopping", "erro ao salvar o pedido "+ sqlexception.getMessage(), sqlexception);
            throw new RuntimeException(sqlexception);
        }
    }

    public List<Pedido> listToBackoffice()
    {
        try
        {
            Log.i("com.projetandoo.allinshopping", "Listando os pedidos para envio para o backoffice");
            List<Pedido> pedidos = PEDIDO_DAO.queryBuilder().where().isNull("dataEnvio").query();
            for( Pedido pedido : pedidos ) {
            	
            	List<ItemPedido> itens = new ArrayList<ItemPedido>();
            	itens.addAll(pedido.getItens());
            	Collections.sort(itens, new Comparator<ItemPedido>() {

					@Override
					public int compare(ItemPedido lhs, ItemPedido rhs) {
						return lhs.getProduto().getIdLoja().compareTo(rhs.getProduto().getIdLoja());
					}
				});
            	pedido.setItens(itens);
            	
            }
            return pedidos;
            
        }
        catch (SQLException sqlexception)
        {
            Log.e("com.projetandoo.allinshopping", "erro na listagem dos pedidos "+ sqlexception.getMessage(), sqlexception);
            throw new RuntimeException(sqlexception);
        }
    }

    public void update(Pedido pedido)
    {
        try
        {
            PEDIDO_DAO.update(pedido);
            return;
        }
        catch (SQLException sqlexception)
        {
            throw new RuntimeException(sqlexception);
        }
    }

	@Override
	protected Dao getDao() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

    

}
