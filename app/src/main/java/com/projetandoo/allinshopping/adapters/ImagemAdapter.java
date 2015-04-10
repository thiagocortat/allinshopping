package com.projetandoo.allinshopping.adapters;

import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projetandoo.allinshopping.R;
import com.projetandoo.allinshopping.interfaces.OnSelectedImagem;
import com.projetandoo.allinshopping.models.Imagem;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by thiagocortat on 1/15/15.
 */
public class ImagemAdapter extends RecyclerView.Adapter<ImagemViewHolder> implements View.OnClickListener {

    private static final String TAG = "ImagemAdapter";

    private List<Imagem> imagemList;
    private OnSelectedImagem listener;

    public ImagemAdapter(List<Imagem> recordList) {
        this.imagemList = recordList;
    }

    @Override
    public ImagemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_imagem, viewGroup, false);
        itemView.setOnClickListener(this);
        return new ImagemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ImagemViewHolder holder, int i) {

        Imagem imagem = imagemList.get(i);
        Uri uri = null;
        try {
            uri = Uri.fromFile(new File(imagem.getFileName()));

            Picasso.with(holder.vImageView.getContext())
                    .load(uri).centerCrop().resizeDimen(R.dimen.thumb_size, R.dimen.thumb_size)
                    .skipMemoryCache()
                    .into(holder.vImageView);
        }
        catch (Resources.NotFoundException e) {
            Picasso.with(holder.vImageView.getContext())
                    .load(uri).centerCrop()
                    .skipMemoryCache()
                    .centerCrop().resize(80, 80).into(holder.vImageView);
        }
        catch (NullPointerException e) {
            Picasso.with(holder.vImageView.getContext())
                    .load(R.drawable.img_default_placeholder)
                    .centerCrop().resizeDimen(R.dimen.thumb_size, R.dimen.thumb_size)
                    .into(holder.vImageView);
        }



        holder.itemView.setTag(imagem);
    }

    @Override
    public int getItemCount() {
        return imagemList.size();
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick " + v.getTag());

        if(listener != null){
            listener.selectedImagem((Imagem) v.getTag());
        }
    }

    public void setListener(OnSelectedImagem listener) {
        this.listener = listener;
    }
}
