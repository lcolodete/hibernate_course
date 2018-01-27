package exercicio39.clienteContaCartao;

import exercicio39.cliente.Cliente;
import exercicio39.conta.Conta;
import exercicio39.cartao.Cartao;

public class ClienteContaCartao
{	private Long id;
	private Cliente cliente;
	private Conta   conta;
	private Cartao  cartao;
	
	public ClienteContaCartao()
	{	
	}

	public ClienteContaCartao(Cliente cliente, Conta conta, Cartao cartao)
	{	this.cliente = cliente;
		this.conta = conta;
		this.cartao = cartao;
	}

	public Long getId()
	{	return id;
	}
	
	@SuppressWarnings("unused")
	private void setId(Long id)
	{	this.id = id;
	}

	public Cliente getCliente()
	{	return cliente;
	}
	
	public void setCliente(Cliente cliente)
	{	this.cliente = cliente;
	}

	public Conta getConta()
	{	return conta;
	}
	
	public void setConta(Conta conta)
	{	this.conta = conta;
	}

	public Cartao getCartao()
	{	return cartao;
	}
	
	public void setCartao(Cartao cartao)
	{	this.cartao = cartao;
	}
}

