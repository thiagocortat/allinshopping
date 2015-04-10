package com.projetandoo.allinshopping.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.projetandoo.allinshopping.R;
import com.projetandoo.allinshopping.models.Secao;
import com.projetandoo.allinshopping.views.AutoFitTextView;

import java.util.List;

public class SecaoAdapter extends ArrayAdapter<Secao> {

    public static final int VESTUARIO       = 151;
    public static final int UD              = 192;
    public static final int CALCADOS        = 197;
    public static final int BIJUTERIAS      = 201;
    public static final int COSMETICOS      = 206;
    public static final int SEX_SHOP        = 217;
    public static final int LIVROS          = 289;

    private List<Secao> sections;

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

        Secao secao = sections.get(i);



        AutoFitTextView txSecao =  (AutoFitTextView) view.findViewById(R.id.secao);
        txSecao.setMaxLines(1);
        txSecao.setText(secao.getNome());
        setIconForSection(secao, txSecao);
//
//        final int maxWidth  = txSecao.getWidth();
//        final int maxHeight = view.getHeight();
//        txSecao.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
//                maxWidth, getContext().getResources().getDisplayMetrics()));

        return view;
    }

    public void setIconForSection(Secao secao, TextView tx){

        if (secao.getSecaoPai() != null)
            setIconForSection(secao.getSecaoPai(), tx);
        else {
            int resIcon;
            switch (secao.getId().intValue()) {
                case VESTUARIO:
                    resIcon = R.drawable.icon_t_shirt;
                    break;
                case UD:
                    tx.setText("UD");
                    resIcon = R.drawable.icon_dining_room;
                    break;
                case CALCADOS:
                    resIcon = R.drawable.icon_womens_shoe;
                    break;
                case BIJUTERIAS:
                    resIcon = R.drawable.icon_wedding_rings;
                    break;
                case COSMETICOS:
                    resIcon = R.drawable.icon_antiseptic_cream;
                    break;
                case SEX_SHOP:
                    resIcon = R.drawable.icon_gender;
                    break;
                case LIVROS:
                    resIcon = R.drawable.icon_books;
                    break;
                default:
                    resIcon = R.drawable.ic_wallet_travel;
                    break;
            }

            Drawable icon = getContext().getResources().getDrawable(resIcon);
            tx.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
//            tx.setCompoundDrawables(icon, null, null, null);
        }
    }

}
