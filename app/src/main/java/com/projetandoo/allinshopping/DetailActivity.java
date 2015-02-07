package com.projetandoo.allinshopping;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.projetandoo.allinshopping.activities.BaseActivity;
import com.projetandoo.allinshopping.adapters.ImagemAdapter;
import com.projetandoo.allinshopping.alerts.ErrorAlert;
import com.projetandoo.allinshopping.events.ChangeRadioButtonEventListener;
import com.projetandoo.allinshopping.interfaces.OnSelectedImagem;
import com.projetandoo.allinshopping.models.Atributo;
import com.projetandoo.allinshopping.models.Imagem;
import com.projetandoo.allinshopping.models.Pedido;
import com.projetandoo.allinshopping.models.Produto;
import com.projetandoo.allinshopping.utilities.Constante;
import com.projetandoo.allinshopping.utilities.ParseUtilities;
import com.projetandoo.allinshopping.utilities.PriceUtilities;
import com.projetandoo.allinshopping.utilities.StringUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;


public class DetailActivity extends BaseActivity  implements View.OnClickListener {


    @InjectView(R.id.imagemProduto) protected ImageView imagemProduto;
    @InjectView(R.id.titulo)        protected TextView titulo;
    @InjectView(R.id.descricao)     protected TextView descricao;
    @InjectView(R.id.preco)         protected TextView preco;
    @InjectView(R.id.adicionar)     protected Button adicionar;
    @InjectView(R.id.tamanho)     protected RadioGroup tamanho;
    @InjectView(R.id.recyclerView)  protected RecyclerView mRecyclerView;

    protected Produto mProduto;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected ImagemAdapter mAdapter;

    @Override
    public void onBeforeInjectViews(Bundle savedInstanceState) {
        Bundle args = getIntent().getExtras();
        if (args != null){
            if (args.containsKey(Constante.Extra.PRODUTO))
                mProduto = (Produto) args.getSerializable(Constante.Extra.PRODUTO);
        }
    }

    @Override
    public int getLayoutResource() { return R.layout.activity_detail; }

    @Override
    public void onAfterInjectViews(Bundle savedInstanceState) {

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        List<Imagem> list = new ArrayList<>(mProduto.getImagens());
        mAdapter = new ImagemAdapter(list);
        mAdapter.setListener(new OnSelectedImagem() {
            @Override
            public void selectedImagem(Imagem imagem) {
                Uri uri = Uri.fromFile(new File(imagem.getFileName()));
                Picasso.with(DetailActivity.this).load(uri).skipMemoryCache().into(imagemProduto);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        Uri uri = Uri.fromFile(new File(mProduto.getDefaultImage()));
        Picasso.with(this).load(uri).skipMemoryCache().into(imagemProduto);

        titulo.setText(mProduto.getTitulo());

        String strDecricao = mProduto.getDescricao();
        if (StringUtils.isEmpty(strDecricao))
            strDecricao = getString(R.string.item_wihout_description);
        descricao.setText(strDecricao);

        preco.setText(ParseUtilities.formatMoney(mProduto.getPrecoVenda()));


        if (mProduto.temAtributos()) {
            tamanho.setOnCheckedChangeListener(new ChangeRadioButtonEventListener());

            for (Atributo atributo : mProduto.getAtributos()) {
                RadioButton radiobutton = new RadioButton(this);
                radiobutton.setTag(atributo);
                radiobutton.setHint(atributo.getDescricao());
                tamanho.addView(radiobutton);
            }
            tamanho.setVisibility(View.VISIBLE);
        }
        else {
            tamanho.setVisibility(View.GONE);
        }

        adicionar.setTag(mProduto);
        adicionar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Produto produto = (Produto) view.getTag();
        Intent intent = new Intent(this, ShoppingCartActivity.class);
        Pedido pedido = PriceUtilities.getPedido();

        Atributo atributo = getAtributo((View) view.getParent());

        if (produto.temAtributos() && atributo == null) {
            (new ErrorAlert(this)).setTitle("Pedidos").setMessage("Tamanho é obrigatório").show();
            return;
        }

        pedido.adicionar(produto, atributo);
        startActivity(intent);

    }

    private Atributo getAtributo(View view) {

        final RadioGroup radiogroup = (RadioGroup) view.findViewById(R.id.tamanho);

        if (radiogroup != null) {
            final int i = radiogroup.getCheckedRadioButtonId();
            final RadioButton radiobutton = (RadioButton) radiogroup.findViewById(i);
            if (radiobutton != null) {
                return (Atributo) radiobutton.getTag();
            }
        }
        return null;

    }
}
