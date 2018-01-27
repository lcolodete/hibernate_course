package exercicio17.lance;

import java.sql.Date;
import exercicio17.produto.Produto;
import exercicio17.util.*;

public class Lance
{	private Long id;
	
	private double valor;
	private Date   dataCriacao;

	private Produto produto;

	// ********* Construtores *********

	public Lance()
	{
	}

	// ********* Métodos do Tipo Get *********

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
	
	// ********* Métodos do Tipo Set *********

	@SuppressWarnings("unused")
	private void setId(Long id)
	{	this.id = id;
	}

	public void setValor(String valor)
		throws AplicacaoException
	{	
		if (valor == null || valor.trim().length() == 0)
		{	
			throw new AplicacaoException("Valor invalido.");
		}
		try
		{
			this.valor = Util.strToDouble(valor);
		}
		catch(NumberFormatException e)
		{	
			throw new AplicacaoException("Valor invalido.");
		}
	}

	public void setDataCriacao(String dataCriacao)
		throws AplicacaoException
	{
		if (!Util.dataValida(dataCriacao))
		{	
			throw new AplicacaoException("Data de emissao invalida.");
		}
		this.dataCriacao = Util.strToDate(dataCriacao);
	}

	// ********* Métodos para Associações *********

	public Produto getProduto()
	{	return produto;
	}
	
	public void setProduto(Produto produto)
	{	this.produto = produto;
	}
}	