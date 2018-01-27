package exercicio14.lance;

import java.sql.Date;
import exercicio14.produto.Produto;
import exercicio14.util.*;

public class Lance
{
	private Long id;
	private double valor;
	private Date dataCriacao;

	// Um lance se refere a um �nico produto

//==>		
	
	private Produto produto;

	// ********* Construtores *********

	public Lance()
	{
	}

	public Lance(double valor, Date dataCriacao)
	{	this.valor = valor;
		this.dataCriacao = dataCriacao;
	}

	// ********* M�todos do Tipo Get *********

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
	
	// ********* M�todos do Tipo Set *********

//	@SuppressWarnings("unused")
	private void setId(Long id)
	{	this.id = id;
	}

	public void setValor(double valor)
	{	this.valor = valor;
	}

	public void setDataCriacao(Date dataCriacao)
	{	this.dataCriacao = dataCriacao;
	}

	// ********* M�todos para Associa��es *********

//==>
	public Produto getProduto()
	{
		return produto;
	}
	
//==>
	public void setProduto(Produto produto)
	{
		this.produto = produto;
	}
}
