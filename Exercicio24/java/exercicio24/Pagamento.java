package exercicio24;

import java.sql.Date;

public abstract class Pagamento
{
	private Long id;

	private Date data;

	private double valor;

	/** *** M�todos Construtores **** */

	public Pagamento()
	{
	}

	public Pagamento(double valor, Date data)
	{
		this.valor = valor;
		this.data = data;
	}

	/** *** M�todos do tipo get **** */

	public Long getId()
	{
		return id;
	}

	public Date getData()
	{
		return data;
	}

	public double getValor()
	{
		return valor;
	}

	/** *** M�todos do tipo set **** */

	@SuppressWarnings("unused")
	private void setId(Long id)
	{
		this.id = id;
	}

	public void setData(Date data)
	{
		this.data = data;
	}

	public void setValor(double valor)
	{
		this.valor = valor;
	}
}
