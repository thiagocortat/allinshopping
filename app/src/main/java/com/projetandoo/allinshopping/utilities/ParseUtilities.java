package com.projetandoo.allinshopping.utilities;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

public final class ParseUtilities
{

    private ParseUtilities()
    {
    }

	public static String formatDate(Date toFormat)
    {
        return formatDate(toFormat, "dd/MM/yyyy");
    }

	public static String formatDate(Date toFormat, String format)
    {   
		return new SimpleDateFormat(format, Constante.PT_BR).format(toFormat);
    }

	public static String formatMoney(Number money)
    {
		DecimalFormat decimalformat = (DecimalFormat) DecimalFormat.getCurrencyInstance(Constante.PT_BR);
        decimalformat.setRoundingMode(RoundingMode.HALF_UP);
		return decimalformat.format(money);
    }

	public static Date toDate(String toParse, String format)
    {
        try
        {   
			return new SimpleDateFormat(format, Constante.PT_BR).parse(toParse);
        }
        catch (ParseException parseexception)
        {   
            Log.e("com.projetandoo.allinshopping", parseexception.getMessage(), parseexception);
            throw new RuntimeException(parseexception);
        }

    }

}
