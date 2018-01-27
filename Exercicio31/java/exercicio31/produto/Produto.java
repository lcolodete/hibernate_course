package exercicio31.produto;

import java.sql.Date;
import java.util.Set;
import java.util.HashSet;
import exercicio31.util.*; 

public class Produto
{	private Long id;
	private String nome;
	private String descricao;
	private double lanceMinimo;
	private Date dataCadastro;
	private Date dataVenda;

    private Set lances = new HashSet();
	
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
	{	return Util.doubleToStr(lanceMinimo);
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
		throws AplicacaoException
	{	if (nome != null && nome.length() > 0)
		{	this.nome = nome;
		}
		else
		{	throw new AplicacaoException("Nome invalido.");
		}
	}
	
	public void setDescricao(String descricao)
		throws AplicacaoException
	{	if (descricao != null && descricao.length() > 0)
		{	this.descricao = descricao;
		}
		else
		{	throw new AplicacaoException("Descricao invalida.");
		}
	}
	
	public void setLanceMinimo(String lanceMinimo)
		throws AplicacaoException
	{	try
		{	if (lanceMinimo == null || lanceMinimo.length() == 0)
			{	throw new AplicacaoException("Lance minimo invalido.");
			}
			else
			{	this.lanceMinimo = Util.strToDouble(lanceMinimo);
			}
		}
		catch(NumberFormatException e)
		{	throw new AplicacaoException("Lance m�nimo inv�lido.");
		}
	}
	
	public void setDataCadastro(String dataCadastro)
		throws AplicacaoException
	{	if (!Util.dataValida(dataCadastro))
		{	throw new AplicacaoException("Data de cadastro invalida.");
		}
		else
		{	Date umaData = Util.strToDate(dataCadastro);
			Date hoje = new Date(System.currentTimeMillis());
		
			if (umaData.after(hoje))
			{	throw new AplicacaoException
					("A data de cadastramento do produto nao pode ser " +
					 "posterior `a data de hoje: " + Util.dateToStr(hoje));
			}
			else
			{	this.dataCadastro = umaData;	
			}
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
}




/*
 package exercicio31.produto;

import java.sql.Date;
import java.util.Set;
import java.util.HashSet;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.text.ParseException;

import exercicio31.lance.Lance;
import exercicio31.util.*; 

public class Produto
{	private Long id;
	private String nome;
	private String descricao;
	private double lanceMinimo;
	private Date dataCadastro;
	private Date dataVenda;

    private Set lances = new HashSet();
	
	// ********* Construtor *********

	public Produto()
	{
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
	{	return Util.doubleToStr(lanceMinimo);
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

	public void setId(Long id)
	{	this.id = id;
	}

	public void setNome(String nome)
		throws AplicacaoException
	{	if (nome != null && nome.length() > 0)
		{	this.nome = nome;
		}
		else
		{	throw new AplicacaoException("Nome invalido.");
		}
	}
	
	public void setDescricao(String descricao)
		throws AplicacaoException
	{	if (descricao != null && descricao.length() > 0)
		{	this.descricao = descricao;
		}
		else
		{	throw new AplicacaoException("Descricao invalida.");
		}
	}
	
	public void setLanceMinimo(String lanceMinimo)
		throws AplicacaoException
	{	try
		{	if (lanceMinimo == null || lanceMinimo.length() == 0)
			{	throw new AplicacaoException("Lance minimo invalido.");
			}
			else
			{	this.lanceMinimo = Util.strToDouble(lanceMinimo);
			}
		}
		catch(ParseException e)
		{	throw new AplicacaoException("Lance minimo invalido.");
		}
	}
	
	public void setDataCadastro(String dataCadastro)
		throws AplicacaoException
	{	if (!Util.dataValida(dataCadastro))
		{	throw new AplicacaoException("Data de cadastro invalida.");
		}
		else
		{	Date umaData = Util.strToDate(dataCadastro);
			Date hoje = new Date(System.currentTimeMillis());
		
			if (umaData.after(hoje))
			{	throw new AplicacaoException
					("A data de cadastramento do produto nao pode ser " +
					 "posterior `a data de hoje: " + Util.dateToStr(hoje));
			}
			else
			{	this.dataCadastro = umaData;	
			}
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
}

*/

