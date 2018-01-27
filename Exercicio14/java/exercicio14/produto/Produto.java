package exercicio14.produto;

import java.sql.Date;
import java.util.Set;
import java.text.ParseException;

import exercicio14.util.*; 

public class Produto
{
	private Long id;
	private String nome;
	private String descricao;
	private double lanceMinimo;
	private Date dataCadastro;
	private Date dataVenda;

	//  Um produto possui lances

//==>
	private Set lances;
	
	/* A   interface  Set   representa  uma   cole��o  sem  elementos 
	 * duplicados.  Para que um elemento  seja armazenado em um Set �
	 * utilizado  o  m�todo  HashCode()  que  ir� retornar um  n�mero 
	 * inteiro que ser�  convertido na posi��o do elemento dentro  do
	 * Set. Um  Set  n�o  garante a ordem de  itera��o dos  elementos 
	 * contidos no Set; em particular, n�o garante  que a  ordem  ir�
	 * permanecer  constante  ao  longo  do  tempo.  Um Set permite a 
	 * exist�ncia de elementos null.
	 */

	
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
		catch(ParseException e)
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

//	@SuppressWarnings("unused")
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
	{	this.dataCadastro = dataCadastro;
	}
	
	public void setDataVenda(Date dataVenda)
	{	this.dataVenda = dataVenda;
	}
	
	// ********* M�todos para Associa��es *********


//==>
	public Set getLances()
	{
		return lances;
	}
	
//==>
	// Esse m�todo pode ser privado porque � o Hibernate que vai us�-lo
	private void setLances(Set lances)
	{
		this.lances = lances;	
	}
}

