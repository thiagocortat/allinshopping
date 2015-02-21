package com.projetandoo.allinshopping.command.home;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import com.projetandoo.allinshopping.HomeActivity;
import com.projetandoo.allinshopping.R;
import com.projetandoo.allinshopping.adapters.SecaoAdapter;
import com.projetandoo.allinshopping.command.Command;
import com.projetandoo.allinshopping.models.Secao;

public class ProdutosParaSecaoComFilhosCommand implements Command {
	
	private Secao TODOS = new Secao("Todos");
	
	private HomeActivity home;

    private static long lastSecaoId = -1;

	public ProdutosParaSecaoComFilhosCommand(HomeActivity home) {
		this.home = home;
	}

	@Override
	public Command execute() {

        new AsyncTask<Void, Void, List<Secao>>() {
            @Override
            protected List<Secao> doInBackground(Void... params) {

                List<Secao> secoes = null;
                if (lastSecaoId == -1 || (lastSecaoId != home.getSecao().getId())) {
                    secoes = new ArrayList<>(home.getSecao().getSubSecoes());
                    TODOS.setSecaoPai(home.getSecao());
                    TODOS.addProdutos(home.getSecao().getProdutos());
                    secoes.add(TODOS);
                    lastSecaoId = home.getSecao().getId();
                }

                return secoes;
            }

            @Override
            protected void onPostExecute(List<Secao> secoes) {
//                home.getSecoes().setAdapter(new SecaoAdapter(home, secoes));

                if (secoes != null) {
                    home.setSecaoAdapter(new SecaoAdapter(home, secoes));
                }

                home.exibirBoneca(true);
                home.setBoneca(R.drawable.lista_produtos);
            }
        }.execute();

		return null;
	}

}
