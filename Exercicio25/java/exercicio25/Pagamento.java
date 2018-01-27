package exercicio25;

import java.sql.Date;

public abstract class Pagamento
{	private Long id;
	private Date data;
	private double valor;
	
	public Pagamento()
	{
	}

	public Pagamento(double valor, Date data)
	{	this.valor = valor;
		this.data = data;
	}

	public Long getId()
	{	return id;
	}
	
	@SuppressWarnings("unused")
	private void setId(Long id)
	{	this.id = id;
	}
	
	public Date getData()
	{	return data;
	}
	
	public void setData(Date data)
	{	this.data = data;
	}

	public double getValor()
	{	return valor;
	}
	
	public void setValor(double valor)
	{	this.valor = valor;
	}
}

