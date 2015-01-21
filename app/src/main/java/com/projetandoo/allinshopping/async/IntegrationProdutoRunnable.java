package com.projetandoo.allinshopping.async;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.projetandoo.allinshopping.MyApplication;
import com.projetandoo.allinshopping.exceptions.IntegrationException;
import com.projetandoo.allinshopping.integration.downloads.DownloadResource;
import com.projetandoo.allinshopping.models.Imagem;
import com.projetandoo.allinshopping.models.Produto;
import com.projetandoo.allinshopping.services.ProdutoService;
import com.projetandoo.allinshopping.utilities.Constante;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;

public class IntegrationProdutoRunnable implements Runnable {

	private final DownloadResource download = new DownloadResource();
	private final Produto produto;
	private final ProdutoService service;

	public IntegrationProdutoRunnable(final Produto produto,
			final ProdutoService produtoservice) {
		this.produto = produto;
		this.service = produtoservice;
	}

	@Override
	public void run() {
		try {
			download.getResourceByProduto(produto, new DownloadResource.OnFinishSaveImages() {
                @Override
                public void onFinishSaveImages() {
                    service.save(produto);
                }
            });

//            getResourceByProduto(produto);
//			service.save(produto);

		} catch (IntegrationException e) {
			throw new RuntimeException(e);
		}
	}


    public void getResourceByProduto(Produto produto) //NOPMD
            throws IntegrationException {

        File directory = new File(Constante.SDCARD_ALLINSHOPP_IMAGES);
        if( !directory.exists() ){
            directory.mkdirs();
        }

        int i = -1;
        for( Imagem imagem : produto.getImagens() ) {

            Boolean hasSaved = (++i == (produto.getImagens().size()-1));
            new AsyncTask<Object, Void, Boolean>(){

                @Override
                protected Boolean doInBackground(Object... params) {
                    Imagem imagem = (Imagem) params[0];
                    Bitmap bitmap;
                    try {
                        bitmap = Picasso.with(MyApplication.getAppContext()).load(imagem.getURL()).get();
                        File file = new File(Constante.SDCARD_ALLINSHOPP_IMAGES , String.format("%s-%s.jpg", imagem.getProduto().getId(),imagem.getId()));

                        file.createNewFile();
                        FileOutputStream ostream = new FileOutputStream(file);
                        if (bitmap != null)
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, ostream);
                        ostream.close();

                        imagem.setFileName(file.getAbsolutePath());


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return (Boolean) params[1];
                }

                @Override
                protected void onPostExecute(Boolean hasSaved) {
                    if(hasSaved)
                        saveProduto();

                }
            }.execute(imagem, hasSaved);
        }


//        new AsyncTask<Imagem, Void, Void>(){
//
//            @Override
//            protected Void doInBackground(Imagem... params) {
//
//                for( Imagem imagem : params) {
//                    Bitmap bitmap;
//                    try {
//                        bitmap = Picasso.with(MyApplication.getAppContext()).load(imagem.getURL()).get();
//                        File file = new File(Constante.SDCARD_ALLINSHOPP_IMAGES , String.format("%s-%s.jpg", imagem.getProduto().getId(),imagem.getId()));
//
//                        FileOutputStream ostream = new FileOutputStream(file);
//                        if (bitmap != null)
//                            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, ostream);
//                        ostream.close();
//
//                        imagem.setFileName(file.getAbsolutePath());
//
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                saveProduto();
//
//                return null;
//            }
//        }.execute((Imagem[]) produto.getImagens().toArray());

    }

    private void saveProduto(){
        service.save(produto);
    }
}
