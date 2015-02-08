package com.projetandoo.allinshopping.command.home;

import android.os.AsyncTask;

import java.sql.SQLException;
import java.util.List;

import com.projetandoo.allinshopping.HomeActivity;
import com.projetandoo.allinshopping.R;
import com.projetandoo.allinshopping.adapters.SecaoAdapter;
import com.projetandoo.allinshopping.command.Command;
import com.projetandoo.allinshopping.models.Secao;
import com.projetandoo.allinshopping.repository.SectionRepository;

public class HomeCommand implements Command {
	
	private HomeActivity home;
	private SectionRepository REPOSITORY;
    private static List<Secao> secaoList;

	public HomeCommand(HomeActivity home) {
		this.home = home;
		REPOSITORY = new SectionRepository(home);
	}

	@Override
	public Command execute() {
		try {

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected void onPreExecute() {
                    if(secaoList == null || secaoList.size() == 0)
                        home.showProgress();
                }

                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        if(secaoList == null || secaoList.size() == 0)
                            secaoList = REPOSITORY.getSections();
                    } catch (SQLException sqlexception) {
                        throw new RuntimeException(sqlexception);
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    home.getSecoes().setAdapter(new SecaoAdapter(home, secaoList));
                    home.exibirBoneca(true);
                    home.setBoneca(R.drawable.lista_secoes);
                    home.setTitle(R.string.app_name);

                    home.hideProgress();
                }
            }.execute();

			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
