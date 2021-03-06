package exercicio40.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Locale;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

public class Util
{	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private static NumberFormat nf = NumberFormat.getInstance(new Locale("pt","BR"));

	static
	{	nf.setMaximumFractionDigits (2);	   // O default � 3.
		nf.setMinimumFractionDigits (2);
		nf.setGroupingUsed(false);
	}

	public static boolean dataValida(String umaData)
	{	try
		{	sdf.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			sdf.parse(umaData, pos);
			if (pos.getIndex() != 10) return false;
			return true; 
		}
		catch(Exception e)
		{	System.out.println("Passou 5");
			return false;
		} 
	}

	public static Date strToDate(String umaData)
	{	int dia = Integer.parseInt(umaData.substring(0,2));
		int mes = Integer.parseInt(umaData.substring(3,5));
		int ano = Integer.parseInt(umaData.substring(6,10));

		return java.sql.Date.valueOf(ano + "-" + mes + "-" + dia);
	}

	public static Timestamp strToTimestamp(String umaData)
	{	int dia = Integer.parseInt(umaData.substring(0,2));
		int mes = Integer.parseInt(umaData.substring(3,5));
		int ano = Integer.parseInt(umaData.substring(6,10));

		int hh = Integer.parseInt(umaData.substring(11,13));
		int mi = Integer.parseInt(umaData.substring(14, 16));
		int ss = Integer.parseInt(umaData.substring(17,19));

		return Timestamp.valueOf(ano + "-" + mes + "-" + dia + " " + hh + ":" + mi + ":" + ss);
	}

	public static String dateToStr(Date umaData)
	{	return sdf.format(umaData);
	}

	public static double strToDouble(String valor)
		throws NumberFormatException
	{	if (valor == null || "".equals(valor))
			return 0;
		else   
		{	return Double.parseDouble(valor);
		}		
	}

	public static String doubleToStr(double valor)
	{	return nf.format(valor);	
	}
}