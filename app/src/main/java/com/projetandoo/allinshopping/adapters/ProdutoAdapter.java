package com.projetandoo.allinshopping.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.projetandoo.allinshopping.R;
import com.projetandoo.allinshopping.ShoppingCartActivity;
import com.projetandoo.allinshopping.alerts.ErrorAlert;
import com.projetandoo.allinshopping.events.AbrirImagemOnClickEvent;
import com.projetandoo.allinshopping.events.ChangeRadioButtonEventListener;
import com.projetandoo.allinshopping.models.Atributo;
import com.projetandoo.allinshopping.models.Imagem;
import com.projetandoo.allinshopping.models.Pedido;
import com.projetandoo.allinshopping.models.Produto;
import com.projetandoo.allinshopping.utilities.DrawableUtilities;
import com.projetandoo.allinshopping.utilities.ParseUtilities;
import com.projetandoo.allinshopping.utilities.PriceUtilities;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class ProdutoAdapter extends ArrayAdapter<Produto>
        implements
        OnClickListener {

//    private AQuery aq;
    private List<Produto> products;

    public ProdutoAdapter(Context context, List<Produto> produtos) {
        super(context, android.R.layout.simple_list_item_1, produtos);
        this.products = produtos;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewgroup) {

        Activity activity = (Activity) getContext();
        ViewHolder viewHolder;
        if (view == null) {
            view = activity.getLayoutInflater().inflate(R.layout.item_resumo, null);

            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Produto produto = products.get(i);


        Uri uri = Uri.fromFile(new File(produto.getDefaultImage()));
        // 1st: reset the imageView
        Picasso.with(activity).cancelRequest(viewHolder.imagem);
        // 2nd start a new load for the imageView
        Picasso.with(activity).load(uri).skipMemoryCache()
                .into(viewHolder.imagem);



        viewHolder.titulo.setText(produto.getTitulo());
        viewHolder.descricao.setText(produto.getDescricao());
        viewHolder.preco.setText(ParseUtilities.formatMoney(produto.getPrecoVenda()));

        viewHolder.tamanho.removeAllViews();

        if (produto.temAtributos()) {
//            RadioGroup radiogroup = (RadioGroup) view.findViewById(R.id.tamanho);
            viewHolder.tamanho
                    .setOnCheckedChangeListener(new ChangeRadioButtonEventListener());

            for (Atributo atributo : produto.getAtributos()) {
                RadioButton radiobutton = new RadioButton(activity);
                radiobutton.setTag(atributo);
                radiobutton.setHint(atributo.getDescricao());
                viewHolder.tamanho.addView(radiobutton);
            }
            viewHolder.tamanho.setVisibility(View.VISIBLE);
        }
        else {
            viewHolder.tamanho.setVisibility(View.GONE);
        }

        viewHolder.imagem.setTag(produto.getDefaultImage());
        viewHolder.imagem.setOnClickListener(new AbrirImagemOnClickEvent());

        viewHolder.adicionar.setTag(produto);
        viewHolder.adicionar.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        Produto produto = (Produto) view.getTag();
        Context context = getContext();
//        final Activity activity = (Activity) getContext();
        Intent intent = new Intent(context, ShoppingCartActivity.class);
        Pedido pedido = PriceUtilities.getPedido();

        Atributo atributo = getAtributo((View) view.getParent());

        if (produto.temAtributos() && atributo == null) {

            (new ErrorAlert(context)).setTitle("Pedidos")
                    .setMessage("Tamanho é obrigatório").show();

            return;
        }

        pedido.adicionar(produto, atributo);
        getContext().startActivity(intent);

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

    private class ViewHolder {

        ImageView imagem;
        TextView titulo, descricao, preco;
        Button adicionar;
        RadioGroup tamanho;

        public ViewHolder(View view){

            imagem = (ImageView) view.findViewById(R.id.imagem);
            titulo = (TextView) view.findViewById(R.id.titulo);
            descricao = (TextView) view.findViewById(R.id.descricao);
            preco = (TextView) view.findViewById(R.id.preco);
            adicionar = (Button) view.findViewById(R.id.adicionar);
            tamanho = (RadioGroup) view.findViewById(R.id.tamanho);

        }
    }
}