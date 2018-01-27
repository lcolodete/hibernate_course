package exercicio34;

public class Telefone
{	private String ddd;
	private String numero;
	
	public Telefone()
	{
	}

	public Telefone(String ddd, String numero)
	{	this.ddd = ddd;
		this.numero = numero;
	}

	public String getDdd()
	{	return ddd;
	}
	
	public void setDdd(String ddd)
	{	this.ddd = ddd;
	}
	
	public String getNumero()
	{	return numero;
	}
	
	public void setNumero(String numero)
	{	this.numero = numero;
	}

	public boolean equals(Object outro)
	{	if(this == outro) return true;
	
		Telefone t = (Telefone)outro;
		return (ddd + "-" + numero).equals(t.ddd + "-" + t.numero); 
	}
	
	public int hashCode()
	{	return (ddd + "-" + numero).hashCode(); 
	}
}

