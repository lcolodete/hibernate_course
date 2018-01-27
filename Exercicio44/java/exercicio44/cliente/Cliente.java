package exercicio44.cliente;

import java.util.Set;
import java.util.HashSet;

public class Cliente
{	
	private Long numero;
	private String nome;
	private double salario;
    private Set lances = new HashSet();

	public Cliente ()
	{	
	}

	public Cliente (String nome, double salario)
	{	this.nome = nome;
		this.salario = salario;
	}

	public Cliente (Long numero, String nome, double salario)
	{	this.numero = numero;
		this.nome = nome;
		this.salario = salario;
	}

	public Long getNumero()
	{	return numero;
	}

	public String getNome()
	{	return nome;
	}
		
	public double getSalario()
	{	return salario;
	}

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

	public Set getLances()
	{	return lances;
	}
	
	public void setLances(Set lances)
	{	this.lances = lances;	
	}
}