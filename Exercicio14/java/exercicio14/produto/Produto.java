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
	
	/* A   interface  Set   representa  uma   coleção  sem  elementos 
	 * duplicados.  Para que um elemento  seja armazenado em um Set é
	 * utilizado  o  método  HashCode()  que  irá retornar um  número 
	 * inteiro que será  convertido na posição do elemento dentro  do
	 * Set. Um  Set  não  garante a ordem de  iteração dos  elementos 
	 * contidos no Set; em particular, não garante  que a  ordem  irá
	 * permanecer  constante  ao  longo  do  tempo.  Um Set permite a 
	 * existência de elementos null.
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

	// ********* Métodos do Tipo Set *********

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
	
	// ********* Métodos para Associações *********


//==>
	public Set getLances()
	{
		return lances;
	}
	
//==>
	// Esse método pode ser privado porque é o Hibernate que vai usá-lo
	private void setLances(Set lances)
	{
		this.lances = lances;	
	}
}

