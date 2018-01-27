package exercicio40.cliente;

import java.util.Set;
import java.util.HashSet;

import exercicio40.clienteContaCartao.*;

public class Cliente
{	private Long id;
	private String nome;
	private Set cccs = new HashSet();
	
	public Cliente()
	{
	}

	public Cliente(String nome)
	{	this.nome = nome;
	}

	public Long getId()
	{	return id;
	}
	
	public void setId(Long id)
	{	this.id = id;
	}
	
	public String getNome()
	{	return nome;
	}
	
	public void setNome(String nome)
	{	this.nome = nome;
	}
	
	public Set getCccs()
	{	return cccs;
	}

	public void setCccs(Set cccs)
	{	this.cccs = cccs;
	}

	@SuppressWarnings("unchecked")
	public boolean adicionarClienteContaCartao(ClienteContaCartao ccc)
	{	ccc.setCliente(this);
		return cccs.add(ccc);
	}
}

