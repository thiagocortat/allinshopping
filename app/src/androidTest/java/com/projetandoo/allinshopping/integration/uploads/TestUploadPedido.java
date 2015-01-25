package com.projetandoo.allinshopping.integration.uploads;

import com.projetandoo.allinshopping.integration.upload.UploadPedido;
import com.projetandoo.allinshopping.models.*;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

/**
 * Created by Marceloo on 24/11/2014.
 */
@RunWith(RobolectricTestRunner.class)
public class TestUploadPedido {

    private final UploadPedido enviarPedido = new UploadPedido("elinaldo.mkt@hotmail.com","12345678");

    @Before
    public void setup() {
        Robolectric.getFakeHttpLayer()
                    .interceptHttpRequests(false);
    }

    @Test
    public void enviarPedido() throws Exception {
        Pedido pedido = criaPedido();
//        enviarPedido.send(pedido);
    }

    public Pedido criaPedido() {
        Pedido pedido = new Pedido();

        Produto produto = new Produto();
        produto.setId(762L);

        Cliente cliente = new Cliente();
        cliente.setPrimeiroNome("MARCELO");
        cliente.setUltimoNome("RODRIGUES");
        cliente.setEmail("marcelosrodrigues@globo.com");
        cliente.setDataNascimento(DateTime.now().toDate());

        Endereco endereco = new Endereco();
        Estado estado = new Estado();
        estado.setId(330L);
        endereco.setBairro("PECHINCHA");
        endereco.setCelular("21981363699");
        endereco.setCep("22743310");
        endereco.setCidade("RIO DE JANEIRO");
        endereco.setEstado(estado);
        endereco.setLogradouro("ESTRADA CAMPO DA AREIA, 84 APTO 206");
        endereco.setTelefone("2133926222");

        cliente.setEndereco(endereco);

        pedido.setCliente(cliente);

        DadosPagamento pagamento = new DadosPagamento();
        pagamento.setCPF("07032327702");
        pagamento.setCVV("123");
        pagamento.setDataValidade(DateTime.now().plusYears(1).toDate());
        pagamento.setNumero("4012001038443335");
        pagamento.setPortador("MARCELO DA SILVA RODRIGUES");

        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setId(2L);
        pagamento.setFormaPagamento(formaPagamento);

        pedido.setFormaPagamento(formaPagamento);
        pedido.setDadosPagamento(pagamento);

        pedido.adicionar(produto);

        return pedido;
    }
}
