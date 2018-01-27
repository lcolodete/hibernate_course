package exercicio21.lance;

import java.sql.Date;
import exercicio21.produto.Produto;
import exercicio21.util.*;

public class Lance
{	private Long id;
	private double valor;
	private Date dataCriacao;
	private Produto produto;

	// ********* Construtor *********

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

	public void setValor(double valor, double valorMaiorLance) 
		throws AplicacaoException
	{	if(valor <= valorMaiorLance)
		{	throw new AplicacaoException(3, 
				"O valor do lance tem que ser superior a " + 
				 valorMaiorLance);
		}
		
		this.valor = valor;
	}

	public void setDataCriacao(Date dataCriacao, Date dataUltimoLance)
		throws AplicacaoException
	{	if(dataCriacao.before(dataUltimoLance))
		{	throw new AplicacaoException("A data de emissao do lance nao pode ser anterior a " + Util.dateToStr(dataUltimoLance));
		}

		Date hoje = new Date(System.currentTimeMillis());
		
		if(dataCriacao.after(hoje))
		{	throw new AplicacaoException("A data de emissao do lance nao pode ser posterior `a data de hoje: " + Util.dateToStr(hoje));
		}
		
		this.dataCriacao = dataCriacao;
	}

	// ********* Métodos para Associações *********

	public Produto getProduto()
	{	return produto;
	}
	
	public void setProduto(Produto produto)
	{	this.produto = produto;
	}
}	