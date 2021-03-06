package exercicio44.produto;

import java.sql.Date;
import java.util.Set;
import java.util.HashSet;

import exercicio44.lance.Lance;
import exercicio44.util.*;

public class Produto
{	private Long id;
	private String nome;
	private String descricao;
	private double lanceMinimo;
	private Date dataCadastro;
	private Date dataVenda;

    private Set lances = new HashSet();
	
    private Lance lanceVencedor;

	// ********* Construtores *********

	public Produto()
	{
	}

	public Produto(String nome, 
	               String descricao, 
	               double lanceMinimo, 
	               Date dataCadastro)
	{	this.nome = nome;
		this.descricao = descricao;
		this.lanceMinimo = lanceMinimo;
		this.dataCadastro = dataCadastro;
	}

	// ********* M�todos do Tipo Get *********

	public Long getId()
	{	return id;
	}
	
	public String getNome()
	{	return nome;
	}
	
	public String getDescricao()
	{	return descricao;
	}
	
	public double getLanceMinimo()
	{	return lanceMinimo;
	}
	
	public String getLanceMinimoMasc()
	{	try
		{	return Util.doubleToStr(lanceMinimo);
		}
		catch(NumberFormatException e)
		{	throw new RuntimeException(e);
		}
	}

	public Date getDataCadastro()
	{	return dataCadastro;
	}
	
	public String getDataCadastroMasc()
	{	return Util.dateToStr(dataCadastro);
	}

	public Date getDataVenda()
	{	return dataVenda;
	}
	
	public String getDataVendaMasc()
	{	return Util.dateToStr(dataVenda);
	}

	// ********* M�todos do Tipo Set *********

	@SuppressWarnings("unused")
	private void setId(Long id)
	{	this.id = id;
	}
	
	public void setNome(String nome)
	{	this.nome = nome;
	}
	
	public void setDescricao(String descricao)
	{	this.descricao = descricao;
	}
	
	public void setLanceMinimo(double lanceMinimo)
	{	this.lanceMinimo = lanceMinimo;
	}
	
	public void setDataCadastro(Date dataCadastro)
		throws AplicacaoException
	{	
		Date hoje = new Date(System.currentTimeMillis());
		
		if (dataCadastro.after(hoje))
		{	throw new AplicacaoException
				("A data de cadastramento do produto nao pode ser " +
				 "posterior `a data de hoje: " + Util.dateToStr(hoje));
		}
		else
		{	this.dataCadastro = dataCadastro;	
		}
	}
	
	public void setDataVenda(Date dataVenda)
	{	this.dataVenda = dataVenda;
	}
	
	// ********* M�todos para Associa��es *********

	public Set getLances()
	{	return lances;
	}
	
	public void setLances(Set lances)
	{	this.lances = lances;	
	}

	public Lance getLanceVencedor()
	{	return lanceVencedor;
	}
	
	public void setLanceVencedor(Lance lanceVencedor)
	{	this.lanceVencedor = lanceVencedor;	
	}
}

