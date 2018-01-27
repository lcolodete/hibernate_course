package exercicio17.produto;

import java.sql.Date;
import java.util.Set;
import java.util.HashSet;
import exercicio17.util.*; 

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
	{
		this.id = id;
	}

	public void setNome(String nome)
		throws AplicacaoException
	{
		if (nome != null && nome.trim().length() > 0)
		{
			this.nome = nome;
		}
		else
		{
			throw new AplicacaoException("Nome inv�lido.");	
		}
	}
	
	public void setDescricao(String descricao)
		throws AplicacaoException
	{	
		if (descricao != null && descricao.trim().length() > 0)
		{
			this.descricao = descricao;
		}
		else
		{
			throw new AplicacaoException("Descri��o inv�lida.");
		}
	}
	
	public void setLanceMinimo(String lanceMinimo)
		throws AplicacaoException
	{
		try
		{
			if (lanceMinimo == null || lanceMinimo.trim().length() == 0)
			{
				throw new AplicacaoException("Lance m�nimo inv�lido.");
			}
			
			this.lanceMinimo = Util.strToDouble(lanceMinimo);
		}
		catch(NumberFormatException e)
		{	
			throw new AplicacaoException("Lance m�nimo inv�lido.");
		}
	}
	
	public void setDataCadastro(String dataCadastro)
		throws AplicacaoException
	{
		if (!Util.dataValida(dataCadastro))
		{
			throw new AplicacaoException("Data de cadastro inv�lida.");
		}
		this.dataCadastro = Util.strToDate(dataCadastro);
	}
	
	public void setDataVenda(Date dataVenda)
	{
		this.dataVenda = dataVenda;
	}
	
	// ********* M�todos para Associa��es *********

	public Set getLances()
	{	return lances;
	}
	
	public void setLances(Set lances)
	{	this.lances = lances;	
	}
}

