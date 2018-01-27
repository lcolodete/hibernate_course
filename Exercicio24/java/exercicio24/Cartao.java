package exercicio24;

import java.sql.Date;

public class Cartao extends Pagamento
{
	private String numero;

	private String mesExp;

	private String anoExp;

	/** *** Métodos Construtores **** */

	public Cartao()
	{
	}

	public Cartao(double valor, Date data, String numero, String mesExp,
			String anoExp)
	{
		super(valor, data);
		this.numero = numero;
		this.mesExp = mesExp;
		this.anoExp = anoExp;
	}

	/** *** Métodos do tipo get **** */

	public String getNumero()
	{
		return numero;
	}

	public String getMesExp()
	{
		return mesExp;
	}

	public String getAnoExp()
	{
		return anoExp;
	}

	/** *** Métodos do tipo set **** */

	public void setNumero(String numero)
	{
		this.numero = numero;
	}

	public void setMesExp(String mesExp)
	{
		this.mesExp = mesExp;
	}

	public void setAnoExp(String anoExp)
	{
		this.anoExp = anoExp;
	}
}
