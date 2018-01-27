package exercicio06;

public class Cliente
{	private Long numero;
	private String nome;
	private double salario;
	private int versao;

//  Métodos construtores

	public Cliente ()
	{	
	}

	public Cliente (String nome, double salario)
	{	this.nome = nome;
		this.salario = salario;
	}

//  Métodos do tipo get

	public Long getNumero()
	{	return numero;
	}

	public String getNome()
	{	return nome;
	}
		
	public double getSalario()
	{	return salario;
	}

	public int getVersao()  
	{	return versao;
	}

//  Métodos do tipo set

	public void setNumero(Long numero)
	{	this.numero = numero;
	}

	public void setNome(String nome)
	{	this.nome = nome;
	}
		
	public void setSalario(double salario)
	{	this.salario = salario;
	}

	public void setVersao(int versao)
	{	this.versao = versao;
	}
}