package exercicio18b.lance;

import java.sql.Date;
import exercicio18b.produto.Produto;
import exercicio18b.util.*;

public class Lance
{	private Long id;
	
	private double valor;
	private Date dataCriacao;
	private Produto produto;

	// ********* Construtores *********

	public Lance()
	{
	}

	// ********* M�todos do Tipo Get *********

	public Long getId()
	{	return id;
	}
	
	public double getValor()
	{	return valor;
	}

	public String getValorMasc()
	{	return Util.doubleToStr(valor);
	}
	
	public Date getDataCriacao()
	{	return dataCriacao;
	}
	
	public String getDataCriacaoMasc()
	{	return Util.dateToStr(dataCriacao);
	}
	
	// ********* M�todos do Tipo Set *********

	@SuppressWarnings("unused")
	private void setId(Long id)
	{	this.id = id;
	}

	public void setValor(String valor, double valorUltimoLance)
		throws AplicacaoException
	{	
		if (valor == null || valor.length() == 0)
		{	throw new AplicacaoException("Valor inv�lido.");
		}
		else
		{	try
			{	this.valor = Util.strToDouble(valor);
			}
			catch(NumberFormatException e)
			{	throw new AplicacaoException("Valor inv�lido.");
			}
		}
		
		if(this.valor <= valorUltimoLance)
		{	throw new AplicacaoException(
				"O valor do lance tem que ser superior a " + valorUltimoLance);
		}
	}

	public void setDataCriacao(String dataCriacao, Date dataUltimoLance)
		throws AplicacaoException
	{	if (!Util.dataValida(dataCriacao))
		{	throw new AplicacaoException("Data de emiss�o inv�lida.");
		}
		else
		{	this.dataCriacao = Util.strToDate(dataCriacao);
		}
		
		if(this.dataCriacao.before(dataUltimoLance))
		{	throw new AplicacaoException(
				"A data de emiss�o do lance n�o pode ser anterior a " 
				+ Util.dateToStr(dataUltimoLance));
		}

		Date hoje = new Date(System.currentTimeMillis());
		
		if(this.dataCriacao.after(hoje))
		{	throw new AplicacaoException(
				"A data de emiss�o do lance n�o pode ser posterior � data de hoje: " 
				+ Util.dateToStr(hoje));
		}
	}

	// ********* M�todos para Associa��es *********

	public Produto getProduto()
	{	return produto;
	}
	
	public void setProduto(Produto produto)
	{	this.produto = produto;
	}
}	