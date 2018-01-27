package exercicio35;

import java.util.List;
import java.util.ArrayList;

public class Cliente
{	
	private Long id;
	private String nome;
	private double salario;

	private List telefones = new ArrayList();

	public Cliente ()
	{	
	}

	public Cliente (String nome, double salario)
	{	this.nome = nome;
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

	public List getTelefones()
	{	return telefones;
	}

	public void setTelefones(List telefones)
	{	this.telefones = telefones;
	}
	
	@SuppressWarnings("unchecked")
	public boolean adicionarTelefone(String telefone)
	{	// Este m�todo sempre retorna true
		return telefones.add(telefone);
	}
	
	public boolean removerTelefone(String telefone)
	{
		return telefones.remove(telefone);
	}
}