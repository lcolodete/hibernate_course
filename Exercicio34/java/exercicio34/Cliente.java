package exercicio34;

import java.util.Set;
import java.util.HashSet;

public class Cliente
{	
	private Long id;
	private String nome;
	private double salario;

	private Set telefones = new HashSet();

	public Cliente ()
	{	
	}

	public Cliente (String nome, double salario)
	{	this.nome = nome;
		this.salario = salario;
	}

	public Cliente (Long id, String nome, double salario)
	{	this.id = id;
		this.nome = nome;
		this.salario = salario;
	}

	/*******  M�todos do tipo get  *******/

	public Long getId()
	{	return id;
	}

	public String getNome()
	{	return nome;
	}
		
	public double getSalario()
	{	return salario;
	}

	/*******  M�todos do tipo set  *******/

	@SuppressWarnings("unused")
	private void setId(Long id)
	{	this.id = id;
	}	
	
	public void setNome(String nome)
	{	this.nome = nome;
	}
		
	public void setSalario(double salario)
	{	this.salario = salario;
	}

	/*******  M�todos que representam associa��es  *******/

	public Set getTelefones()
	{	return telefones;
	}

	public void setTelefones(Set telefones)
	{	this.telefones = telefones;
	}
	
	@SuppressWarnings("unchecked")
	public boolean adicionarTelefone(Telefone telefone)
	{	return telefones.add(telefone);
	}
	
	public boolean removerTelefone(Telefone telefone)
	{	return telefones.remove(telefone);
	}
}