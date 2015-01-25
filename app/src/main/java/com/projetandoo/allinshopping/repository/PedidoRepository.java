
package com.projetandoo.allinshopping.repository;

import android.content.Context;
import android.util.Log;
import com.j256.ormlite.dao.Dao;
import com.projetandoo.allinshopping.exceptions.InitializationDbException;
import com.projetandoo.allinshopping.models.ItemPedido;
import com.projetandoo.allinshopping.models.Pedido;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class PedidoRepository extends AbstractRepository<Pedido, Long> {
    private final Dao<Pedido, Long> PEDIDO_DAO;

    public PedidoRepository(Context context) {
        super(context);
        try {
            PEDIDO_DAO = getDatabase().getPedidoDao();
            return;
        } catch (SQLException sqlexception) {
            throw new InitializationDbException("Erro ao inicializar o repositorio ShoppingCartRepository " + sqlexception.getMessage(), sqlexception);
        }
    }


    @Override
    public void insert(Pedido pedido) {
        try {
            Log.i("com.projetandoo.allinshopping", String.format("Salvando o pedido %s", pedido));
            this.getDao().create(pedido);
            Log.i("com.projetandoo.allinshopping",
                    String.format("Pedido %s salvo", pedido));

        } catch (SQLException sqlexception) {
            Log.e("com.projetandoo.allinshopping", "erro ao salvar o pedido " + sqlexception.getMessage(), sqlexception);
            throw new RuntimeException(sqlexception);
        }
    }

    public List<Pedido> findPedidoNotSent() {
        try {
            Log.i("com.projetandoo.allinshopping", "Listando os pedidos para envio para o backoffice");
            List<Pedido> pedidos = PEDIDO_DAO.queryBuilder().where().isNull("dataEnvio").query();
            for (Pedido pedido : pedidos) {

                List<ItemPedido> itens = new ArrayList<ItemPedido>();
                itens.addAll(pedido.getItens());
                //TODO:Resolver esse Bug Mandito
                //TODO: getIdLoja() sempre retorna null
//                Collections.sort(itens, new Comparator<ItemPedido>() {
//
//                    @Override
//                    public int compare(ItemPedido lhs, ItemPedido rhs) {
//                        return lhs.getProduto().getIdLoja().compareTo(rhs.getProduto().getIdLoja());
//                    }
//                });
                pedido.setItens(itens);

            }
            return pedidos;

        } catch (SQLException sqlexception) {
            Log.e("com.projetandoo.allinshopping", "erro na listagem dos pedidos " + sqlexception.getMessage(), sqlexception);
            throw new RuntimeException(sqlexception);
        }
    }

    @Override
    protected Dao<Pedido, Long> getDao() throws SQLException {
        return PEDIDO_DAO;
    }

}
