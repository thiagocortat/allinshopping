package com.projetandoo.allinshopping.utilities;

import java.math.BigDecimal;

import com.projetandoo.allinshopping.models.FaixaPreco;
import com.projetandoo.allinshopping.models.Pedido;

public final class PriceUtilities {
	
	private static Pedido pedido;
	
	private static FaixaPreco frete;
	
	private PriceUtilities() {}

	

	public static Pedido getPedido() {
		
		if( PriceUtilities.pedido == null ) {
			PriceUtilities.pedido = new Pedido();
		}
		return PriceUtilities.pedido;
	}



	public static void setFrete(final FaixaPreco frete) {
		PriceUtilities.frete = frete;
	}



	public static BigDecimal getFrete() {
		BigDecimal preco = BigDecimal.ZERO;
		if( frete !=null ) {		
			preco =	frete.getPreco();
		}
		return preco;
	}

    public static void novoPedido() {
        PriceUtilities.pedido = null;
    }
}
