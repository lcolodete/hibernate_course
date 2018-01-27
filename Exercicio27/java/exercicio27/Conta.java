package exercicio27;

import java.sql.Date;

public class Conta extends Pagamento
{	private String banco;
	private String agencia;
	private String conta;
	
	public Conta()
	{
	}

	public Conta(double valor, Date data, String banco, String agencia, String conta)
	{	super(valor, data);
		this.banco = banco;
		this.agencia = agencia;
		this.conta = conta;
	}

	public String getBanco()
	{	return banco;
	}
	
	public void setBanco(String banco)
	{	this.banco = banco;
	}
	
	public String getAgencia()
	{	return agencia;
	}
	
	public void setAgencia(String agencia)
	{	this.agencia = agencia;
	}

	public String getConta()
	{	return conta;
	}
	
	public void setConta(String conta)
	{	this.conta = conta;
	}
}

