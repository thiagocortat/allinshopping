package com.projetandoo.allinshopping.integration.downloads;

import java.io.*;
import java.net.HttpURLConnection;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;

import com.projetandoo.allinshopping.MyApplication;
import com.projetandoo.allinshopping.exceptions.IntegrationException;
import com.projetandoo.allinshopping.models.Imagem;
import com.projetandoo.allinshopping.models.Produto;
import com.projetandoo.allinshopping.utilities.Constante;
import com.squareup.picasso.Callback;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class DownloadResource {

    public interface OnFinishSaveImages{
        public void onFinishSaveImages();
    }

	private CredentialsProvider getCredential() {

		BasicCredentialsProvider provider = new BasicCredentialsProvider();
		AuthScope authscope = new AuthScope(AuthScope.ANY_HOST,
				AuthScope.ANY_PORT);
		UsernamePasswordCredentials credential = new UsernamePasswordCredentials(
				Constante.CREDENTIALS, "");
		provider.setCredentials(authscope, credential);
		return provider;

	}

	public void getResourceByProduto(Produto produto, final OnFinishSaveImages listener) //NOPMD
			throws IntegrationException {

        File directory = new File(Constante.SDCARD_ALLINSHOPP_IMAGES);
        if( !directory.exists() ){
            directory.mkdirs();
        }

//        Picasso.Builder builder = new Picasso.Builder(MyApplication.getAppContext());
//        final Picasso picasso =  builder.downloader(new OkHttpDownloader(MyApplication.getAppContext()) {
//            @Override
//            protected HttpURLConnection openConnection(Uri uri) throws IOException {
//                HttpURLConnection connection = super.openConnection(uri);
//                connection.setRequestProperty("Authorization", Constante.CREDENTIALS);
//                return connection;
//            }
//        }).build();

                int i = -1;
		for( Imagem imagem : produto.getImagens() ) {

            //Bug, crie a featue usando Picasso
//			String imagepath = getResourceByImage(imagem);
//			imagem.setFileName(imagepath);

            Boolean hasSaved = (++i == (produto.getImagens().size()-1));
            new AsyncTask<Object, Void, Void>(){

                @Override
                protected Void doInBackground(Object... params) {
                    Imagem imagem = (Imagem) params[0];
                    Boolean hasSaved = (Boolean) params[1];
                    Bitmap bitmap;
                    try {
                        bitmap = Picasso.with(MyApplication.getAppContext()).load(imagem.getURL()).get();
                        File file = new File(Constante.SDCARD_ALLINSHOPP_IMAGES , String.format("%s-%s.jpg", imagem.getProduto().getId(),imagem.getId()));

//                        file.createNewFile();
                        FileOutputStream ostream = new FileOutputStream(file);
                        if (bitmap != null)
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, ostream);
                        ostream.close();

                        imagem.setFileName(file.getAbsolutePath());

                        if (bitmap != null) {
                            bitmap.recycle();
                        }

                        if(hasSaved && listener != null)
                            listener.onFinishSaveImages();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute(imagem, hasSaved);
        }
		
	}

//	private String getResourceByImage(final Imagem imagem)
//			throws IntegrationException {
//
//		String absolutImagePath = null;
//        FileOutputStream output = null; // NOPMD
//        BufferedInputStream buffer = null; // NOPMD
//
//		try {
//
//			final DefaultHttpClient client = new DefaultHttpClient();
//			client.setCredentialsProvider(this.getCredential());
//			final HttpGet GET = new HttpGet(imagem.getURL());
//
//			final HttpEntity httpentity = client.execute(GET).getEntity();
//
//			if (httpentity != null) {
//
//                buffer = new BufferedInputStream(httpentity.getContent());
//				final File image = new File(Constante.SDCARD_ALLINSHOPP_IMAGES , String.format("%s-%s.bmp", imagem.getProduto().getId(),imagem.getId()));
//                final File directory = new File(Constante.SDCARD_ALLINSHOPP_IMAGES);
//
//                if( !directory.exists() ){
//                    directory.mkdirs();
//                }
//
//                if(image.exists()){
//                    image.delete();
//                }
//                image.createNewFile();
//
//                output = new FileOutputStream(image);
//
//                int i = 0; // NOPMD
//                byte[] readed = new byte[1024];
//                while ((i = buffer.read(readed)) != -1 ) { // NOPMD
//                    output.write(readed,0,i);
//                    output.flush();
//                }
//
//				absolutImagePath = image.getAbsoluteFile().getAbsolutePath();
//
//			}
//
//			return absolutImagePath;
//
//		} catch (ClientProtocolException e) {
//			throw new IntegrationException(
//					"Ocorreu um erro para converter a resposta do servidor "
//							+ e.getMessage(), e);
//		} catch (IllegalStateException e) {
//			throw new IntegrationException(
//					"Ocorreu um erro para converter a resposta do servidor "
//							+ e.getMessage(), e);
//		} catch (FileNotFoundException e) {
//			throw new IntegrationException(
//					"Ocorreu um erro para converter a resposta do servidor "
//							+ e.getMessage(), e);
//		} catch (IOException e) {
//			throw new IntegrationException(
//					"Ocorreu um erro para converter a resposta do servidor "
//							+ e.getMessage(), e);
//		} finally {
//
//                try {
//
//                    if( buffer != null ) {
//                        buffer.close();
//                    }
//                    if(output != null ){
//                        output.close();
//                    }
//                } catch (IOException e) {
//                    Log.w("com.projetandoo.allinshopping.integration.downloads",e.getMessage());
//                }
//        }
//	}



//    class  ImagemTarget implements Target {
//
//        private Imagem imagem;
//
//        public ImagemTarget(Imagem imagem){
//            this.imagem = imagem;
//        }
//
//        @Override
//        public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//
//                    File directory = new File(Constante.SDCARD_ALLINSHOPP_IMAGES);
//                    if( !directory.exists() ){
//                        directory.mkdirs();
//                    }
//
//                    File file = new File(Constante.SDCARD_ALLINSHOPP_IMAGES , String.format("%s-%s.jpg", imagem.getProduto().getId(),imagem.getId()));
//                    try
//                    {
//                        file.createNewFile();
//                        FileOutputStream ostream = new FileOutputStream(file);
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, ostream);
//                        ostream.close();
//
//                        imagem.setFileName(file.getAbsolutePath());
//                    }
//                    catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//
//                }
//            }).start();
//        }
//
//        @Override
//        public void onBitmapFailed(Drawable errorDrawable) {
//
//        }
//
//        @Override
//        public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//        }
//    }
}
