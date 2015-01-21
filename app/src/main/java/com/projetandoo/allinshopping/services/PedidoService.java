package com.projetandoo.allinshopping.services;

import android.content.Context;
import com.projetandoo.allinshopping.enumations.IntegrationType;
import com.projetandoo.allinshopping.enumations.ResourceType;
import com.projetandoo.allinshopping.integration.IntegrationFactory;
import com.projetandoo.allinshopping.integration.upload.Upload;
import com.projetandoo.allinshopping.models.DadosPagamento;
import com.projetandoo.allinshopping.models.ItemPedido;
import com.projetandoo.allinshopping.models.Pedido;
import com.projetandoo.allinshopping.repository.DadosPagamentoRepository;
import com.projetandoo.allinshopping.repository.ItemPedidoRepository;
import com.projetandoo.allinshopping.repository.PedidoRepository;

import java.util.List;

public class PedidoService {

    private PedidoRepository PEDIDO_REPOSITORY;
    private ItemPedidoRepository ITEM_PEDIDO_REPOSITORY;
    private DadosPagamentoRepository DADO_PAGTO_REPOSITORY;

    public PedidoService(Context context) {
        PEDIDO_REPOSITORY = new PedidoRepository(context);
        ITEM_PEDIDO_REPOSITORY = new ItemPedidoRepository(context);
        DADO_PAGTO_REPOSITORY = new DadosPagamentoRepository(context);
    }

    public void save( DadosPagamento dadospagamento) {
        DADO_PAGTO_REPOSITORY.insert(dadospagamento);
        Pedido pedido = dadospagamento.getPedido();
        pedido.setDadosPagamento(dadospagamento);
        PEDIDO_REPOSITORY.update(dadospagamento.getPedido());

    }

    public void save( Pedido pedido) {

        if (pedido.getId() != null && pedido.getId() > 0L) {
            PEDIDO_REPOSITORY.update(pedido);
        } else {
            PEDIDO_REPOSITORY.insert(pedido);
            for (ItemPedido item : pedido.getItens()) {
                item.setPedido(pedido);
                ITEM_PEDIDO_REPOSITORY.insert(item);
            }
        }

    }

    public void send() throws Exception {
         List<Pedido> pedidos = PEDIDO_REPOSITORY.findPedidoNotSent();
         Upload<Pedido> upload = IntegrationFactory.getInstance()
                .getIntegration(IntegrationType.PRESTASHOP)
                .getUpload(ResourceType.PEDIDO);
        for ( Pedido pedido : pedidos) {
            upload.send(pedido);
            pedido.enviado();
            PEDIDO_REPOSITORY.update(pedido);
        }

    }


}
