package exercicio26;

import java.sql.Date;

public class Cartao extends Pagamento
{	private String numero;
	private String mesExp;
	private String anoExp;
	
	public Cartao()
	{
	}

	public Cartao(double valor, Date data, String numero, String mesExp, String anoExp)
	{	super(valor, data);
		this.numero = numero;
		this.mesExp = mesExp;
		this.anoExp = anoExp;
	}

	public String getNumero()
	{	return numero;
	}
	
	public void setNumero(String numero)
	{	this.numero = numero;
	}
	
	public String getMesExp()
	{	return mesExp;
	}
	
	public void setMesExp(String mesExp)
	{	this.mesExp = mesExp;
	}

	public String getAnoExp()
	{	return anoExp;
	}
	
	public void setAnoExp(String anoExp)
	{	this.anoExp = anoExp;
	}
}

