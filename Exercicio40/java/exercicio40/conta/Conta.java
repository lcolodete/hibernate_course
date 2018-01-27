package exercicio40.conta;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;

import exercicio40.clienteContaCartao.*;

public class Conta
{	private Long id;
	private Date dataAbertura;
	private Set cccs = new HashSet();

	public Conta()
	{	
	}

	public Conta(Date dataAbertura)
	{	this.dataAbertura = dataAbertura;
	}

	public Long getId()
	{	return id;
	}
	
	public void setId(Long id)
	{	this.id = id;
	}
	
	public Date getDataAbertura()
	{	return dataAbertura;
	}
	
	public void setDataAbertura(Date dataAbertura)
	{	this.dataAbertura = dataAbertura;
	}

	public Set getCccs()
	{	return cccs;
	}

	public void setCccs(Set cccs)
	{	this.cccs = cccs;
	}

	@SuppressWarnings("unchecked")
	public boolean adicionarClienteContaCartao(ClienteContaCartao ccc)
	{	ccc.setConta(this);
		return cccs.add(ccc);
	}
}

