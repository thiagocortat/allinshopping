package com.projetandoo.allinshopping.models;

import org.junit.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * Created by Thiago Cortat on 18/09/2014.
 */
public class PedidoTest {

    @Test
    public void adicionarItemDePedido() throws Exception {

        Produto produto = new Produto();
        produto.setId(1L);
        Field preco = produto.getClass().getDeclaredField("preco");
        preco.setAccessible(true);
        preco.set(produto,new BigDecimal("100"));

        Pedido pedido = new Pedido();
        pedido.adicionar(produto);

        assertEquals(new BigDecimal("100"),pedido.getTotalLiquido());
    }

    @Test
    public void adicionarDoisProdutosDiferentesNaLista() throws Exception {


        Produto produtoa = new Produto();
        produtoa.setId(1L);
        Field preco = produtoa.getClass().getDeclaredField("preco");
        preco.setAccessible(true);
        preco.set(produtoa,new BigDecimal("100"));

        Produto produtob = new Produto();
        produtob.setId(2L);
        Field preco2 = produtob.getClass().getDeclaredField("preco");
        preco2.setAccessible(true);
        preco2.set(produtob, new BigDecimal("100"));

        Pedido pedido = new Pedido();
        pedido.adicionar(produtoa);
        pedido.adicionar(produtob);

        assertEquals(2,pedido.getItens().size());
        assertEquals(new BigDecimal("200"),pedido.getTotalLiquido());
    }

    @Test
    public void deveAumentarAquantidadeDoItem() throws Exception {
        Produto produto = new Produto();
        produto.setId(1L);
        Field preco = produto.getClass().getDeclaredField("preco");
        preco.setAccessible(true);
        preco.set(produto,new BigDecimal("100"));

        Pedido pedido = new Pedido();
        pedido.adicionar(produto);
        pedido.adicionar(produto);

        assertEquals(1,pedido.getItens().size());
        assertEquals(new BigDecimal("200"),pedido.getTotalLiquido());
    }

    @Test
    public void adicionarProdutoComAtributo() throws Exception {

        Produto produto = new Produto();
        produto.setId(1L);
        Field preco = produto.getClass().getDeclaredField("preco");
        preco.setAccessible(true);
        preco.set(produto,new BigDecimal("100"));


        Atributo atributo = new Atributo();
        Field id = atributo.getClass().getDeclaredField("id");
        id.setAccessible(true);
        id.set(atributo,1L);

        atributo.setProduto(produto);

        Pedido pedido = new Pedido();
        pedido.adicionar(produto,atributo);

        assertEquals(new BigDecimal("100"),pedido.getTotalLiquido());

    }

    @Test
    public void adicionarDoisProdutosProdutoComAtributo() throws Exception {

        Produto produtoa = new Produto();
        produtoa.setId(1L);
        Field preco = produtoa.getClass().getDeclaredField("preco");
        preco.setAccessible(true);
        preco.set(produtoa,new BigDecimal("100"));

        Produto produtob = new Produto();
        produtob.setId(2L);
        Field precob = produtob.getClass().getDeclaredField("preco");
        precob.setAccessible(true);
        precob.set(produtob, new BigDecimal("100"));


        Atributo atributo = new Atributo();
        Field id = atributo.getClass().getDeclaredField("id");
        id.setAccessible(true);
        id.set(atributo,1L);

        Pedido pedido = new Pedido();

        pedido.adicionar(produtoa,atributo);
        pedido.adicionar(produtob,atributo);

        assertEquals(2,pedido.getItens().size());
        assertEquals(new BigDecimal("200"),pedido.getTotalLiquido());

    }

    @Test
    public void deveAumentarAQuantidadeSemRepetirNaLista() throws Exception {


        Produto produto = new Produto();
        produto.setId(1L);
        Field preco = produto.getClass().getDeclaredField("preco");
        preco.setAccessible(true);
        preco.set(produto,new BigDecimal("100"));


        Atributo atributo = new Atributo();
        Field id = atributo.getClass().getDeclaredField("id");
        id.setAccessible(true);
        id.set(atributo,1L);

        atributo.setProduto(produto);

        Pedido pedido = new Pedido();
        pedido.adicionar(produto,atributo);
        pedido.adicionar(produto,atributo);

        assertEquals(new BigDecimal("200"),pedido.getTotalLiquido());
        assertEquals(1,pedido.getItens().size());
    }
}
