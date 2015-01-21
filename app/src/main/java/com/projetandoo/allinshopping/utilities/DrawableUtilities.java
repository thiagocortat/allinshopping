package com.projetandoo.allinshopping.utilities;


import android.graphics.drawable.Drawable;
import android.util.Log;

public final class DrawableUtilities
{

	private DrawableUtilities() {
	}

	public static Drawable getImage(final String image)
    {
        Log.i("com.projetandoo.allinshopping", "Gerando a imagem em formato drawable");
        return Drawable.createFromPath(image);

    }
}
