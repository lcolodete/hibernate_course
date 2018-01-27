package exercicio16.lance;

import java.sql.Date;
import exercicio16.produto.Produto;
import exercicio16.util.*;

public class Lance
{	private Long id;
	private double valor;
	private Date dataCriacao;
	private Produto produto;

	// ********* Construtores *********

	public Lance()
	{
	}

	public Lance(double valor, Date dataCriacao, Produto produto)
	{	this.valor = valor;
		this.dataCriacao = dataCriacao;
		this.produto = produto;
	}

	// ********* Métodos do Tipo Get *********

	public Long getId()
	{	return id;
	}
	
	public double getValor()
	{	return valor;
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

	public void setValor(double valor)
	{	this.valor = valor;
	}

	public void setDataCriacao(Date dataCriacao)
	{	this.dataCriacao = dataCriacao;
	}

	// ********* Métodos para Associações *********

	public Produto getProduto()
	{	return produto;
	}
	
	public void setProduto(Produto produto)
	{	this.produto = produto;
	}
}	