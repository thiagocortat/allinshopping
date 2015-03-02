package com.projetandoo.allinshopping.command.home;

import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.projetandoo.allinshopping.HomeActivity;
import com.projetandoo.allinshopping.adapters.ProdutoAdapter;
import com.projetandoo.allinshopping.command.Command;

public class ProdutosParaSecaoSemFilhosCommand implements Command {

    private static final int INITIAL_DELAY_MILLIS = 300;
	private HomeActivity home;

	public ProdutosParaSecaoSemFilhosCommand(HomeActivity home) {
		this.home = home;
	}

	@Override
	public Command execute() {
		
		ProdutoAdapter produtoadapter = new ProdutoAdapter(home,
				home.getSecao().getProdutos());

        SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(produtoadapter);
        swingBottomInAnimationAdapter.setAbsListView(home.getProdutos());

        assert swingBottomInAnimationAdapter.getViewAnimator() != null;
        swingBottomInAnimationAdapter.getViewAnimator().setInitialDelayMillis(INITIAL_DELAY_MILLIS);

		home.getProdutos().setAdapter(swingBottomInAnimationAdapter);
		home.exibirBoneca(false);
		home.exibirListaProdutos(true);
		
		return null;
	}

}
