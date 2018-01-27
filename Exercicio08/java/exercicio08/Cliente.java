package exercicio08;

public class Cliente
{	
	private Long numero;
	private String nome;
	private double salario;

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

//  Métodos do tipo set

	@SuppressWarnings("unused")
	private void setNumero(Long numero)
	{	this.numero = numero;
	}

	public void setNome(String nome)
	{	this.nome = nome;
	}
		
	public void setSalario(double salario)
	{	this.salario = salario;
	}
}