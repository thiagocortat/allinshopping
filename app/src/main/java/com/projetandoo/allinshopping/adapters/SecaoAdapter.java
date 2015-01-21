package com.projetandoo.allinshopping.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.androidquery.AQuery;
import com.projetandoo.allinshopping.R;
import com.projetandoo.allinshopping.models.Secao;

import java.util.List;

public class SecaoAdapter extends ArrayAdapter<Secao> {
    private List<Secao> sections;
//    private AQuery aq;

    public SecaoAdapter(Context context, List<Secao> secoes) {
        super(context, android.R.layout.simple_list_item_1, secoes);
        this.sections = secoes;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewgroup) {

        Activity activity = (Activity) getContext();
        if (view == null) {
            view = activity.getLayoutInflater().inflate(R.layout.secoes, null);
        }

//        if (aq == null) {
//            aq = new AQuery(view);
//        }

        Secao secao = sections.get(i);

        Button btSecao = (Button) view.findViewById(R.id.secao);
        btSecao.setText(secao.getNome());
        btSecao.setTag(secao);
        btSecao.setOnClickListener((OnClickListener) activity);

//        aq.id(R.id.secao).text(secao.getNome());
//        aq.id(R.id.secao).clicked((OnClickListener) activity);
//        aq.id(R.id.secao).tag(secao);
        return view;
    }

}
