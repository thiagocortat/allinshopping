package com.projetandoo.allinshopping;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.projetandoo.allinshopping.adapters.SecaoAdapter;
import com.projetandoo.allinshopping.alerts.ErrorAlert;
import com.projetandoo.allinshopping.commandfactory.CommandFactory;
import com.projetandoo.allinshopping.models.Produto;
import com.projetandoo.allinshopping.models.Secao;
import com.projetandoo.allinshopping.utilities.Constante;

public class HomeActivity extends AbstractActivity implements AdapterView.OnItemClickListener {

	private Secao secao;
//    private TextView txPromocao;
    private ListView secoes;
    private GridView produtos;
    private ImageView boneca;

    @Override
	protected void onCreate(final Bundle bundle) {
		super.onCreate(bundle);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_main);

        findViews();

        produtos.setVisibility(View.GONE);

	}

    private void findViews() {
//        txPromocao = (TextView)findViewById( R.id.promocao );
        secoes = (ListView)findViewById( R.id.secoes );
        secoes.setOnItemClickListener(this);

        produtos = (GridView)findViewById( R.id.promocoes );
        produtos.setOnItemClickListener(this);

        boneca = (ImageView)findViewById( R.id.boneca );
    }

	public ListView getSecoes() {
		return this.secoes;
	}

    public void setSecaoAdapter(SecaoAdapter adapter) {
        AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(adapter);
        animationAdapter.setAbsListView(secoes);
        secoes.setAdapter(animationAdapter);
    }

	public GridView getProdutos() {
		return this.produtos;
	}

	@Override
    public void onStart() {
        try {
            super.onStart();
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);

            CommandFactory.getFactory(this).createCommand().execute();

        }catch( Exception e) {
            new ErrorAlert(this)
                    .setMessage(e.getMessage())
                    .setCancelable(true)
                    .setTitle("Catalogo Digital Ella S/A")
                    .show();
        }
	}


	@Override
	public void onBackPressed() {

		if (this.secao == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Deseja sair da Loja?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            HomeActivity.this.finish();
                        }
                    })
                    .setNegativeButton("Não", null);
            builder.show();
//            final Intent intent = new Intent(this,MainActivity.class);
//            startActivity(intent);
//            this.finish();
		} else {
			this.secao = secao.getSecaoPai();
			CommandFactory.getFactory(this)
	    	  .createCommand()
	    	  .execute();
		}
	}

//	@Override
//	public void onClick(View view) {
//		this.secao = (Secao) view.getTag();
//		CommandFactory.getFactory(this)
//			    	  .createCommand()
//			    	  .execute();
//	}

	public void setTitulo(String titulo) {
        setTitle(titulo);
//        txPromocao.setText(titulo);
	}
	
	public String getTitulo() {
		return getTitle().toString();
        //return txPromocao.getText().toString();
	}
	
	public Secao getSecao() {
		return this.secao;
	}

	public void setBoneca(int image) {
        boneca.setImageResource(image);
		
	}

	public void exibirBoneca(boolean show) {
		if(show) {
            boneca.setVisibility(View.VISIBLE);
            produtos.setVisibility(View.GONE);
		} else {
            boneca.setVisibility(View.GONE);
            produtos.setVisibility(View.VISIBLE);
		}
	}

	public void exibirListaProdutos(boolean show) {
		if(show) {
            boneca.setVisibility(View.GONE);
            produtos.setVisibility(View.VISIBLE);
		} else {
            boneca.setVisibility(View.VISIBLE);
            produtos.setVisibility(View.GONE);
		}
		
	}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (parent == secoes){
            this.secao = (Secao) parent.getItemAtPosition(position);
            CommandFactory.getFactory(this).createCommand().execute();
        }
        else {
            Produto produto = (Produto) parent.getItemAtPosition(position);
            Intent it = new Intent(this, DetailActivity.class);
            it.putExtra(Constante.Extra.PRODUTO, produto);
            startActivity(it);
        }

    }
}
