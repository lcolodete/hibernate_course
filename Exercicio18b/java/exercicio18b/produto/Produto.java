package exercicio18b.produto;

import java.sql.Date;
import java.util.Set;
import java.util.HashSet;
import exercicio18b.lance.Lance;
import exercicio18b.util.*; 

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

	// ********* Métodos do Tipo Get *********

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

	// ********* Métodos do Tipo Set *********

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
		{	throw new AplicacaoException("Nome inválido.");
		}
	}
	
	public void setDescricao(String descricao)
		throws AplicacaoException
	{	if (descricao != null && descricao.length() > 0)
		{	this.descricao = descricao;
		}
		else
		{	throw new AplicacaoException("Descrição inválida.");
		}
	}
	
	public void setLanceMinimo(String lanceMinimo)
		throws AplicacaoException
	{	try
		{	if (lanceMinimo == null || lanceMinimo.length() == 0)
			{	throw new AplicacaoException("Lance mínimo inválido.");
			}
			else
			{	this.lanceMinimo = Util.strToDouble(lanceMinimo);
			}
		}
		catch(NumberFormatException e)
		{	throw new AplicacaoException("Lance mínimo inválido.");
		}
	}
	
	public void setDataCadastro(String dataCadastro)
		throws AplicacaoException
	{	if (!Util.dataValida(dataCadastro))
		{	throw new AplicacaoException("Data de cadastro inválida.");
		}
		else
		{	Date umaData = Util.strToDate(dataCadastro);
			Date hoje = new Date(System.currentTimeMillis());
		
			if (umaData.after(hoje))
			{	throw new AplicacaoException
					("A data de cadastramento do produto não pode ser " +
					 "posterior à data de hoje: " + Util.dateToStr(hoje) + ".");
			}
			else
			{	this.dataCadastro = umaData;	
			}
		}
	}
	
	public void setDataVenda(Date dataVenda)
	{	this.dataVenda = dataVenda;
	}
	
	// ********* Métodos para Associações *********

	public Set getLances()
	{	return lances;
	}
	
	public void setLances(Set<Lance> lances)
	{	this.lances = lances;	
	}
}

