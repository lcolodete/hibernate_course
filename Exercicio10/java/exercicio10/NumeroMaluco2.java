package exercicio10;

public class NumeroMaluco2 
{	
 	// As 4 linhas abaixo definem uma variável estática do tipo ThreadLocal
	// e efetua o override do método initialValue() da classe ThreadLocal.

	private static ThreadLocal numero = new ThreadLocal()
	{	protected synchronized Object initialValue() 
        {	return new Double(Math.random());
    	}
    };

    public static double get() 
    {	return (Double)numero.get();
    }
}
