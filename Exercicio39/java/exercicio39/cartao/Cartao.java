package exercicio39.cartao;

import java.util.Date;
import exercicio39.clienteContaCartao.*;

public class Cartao
{	private Long id;
	private Date dataEmissao;
	private ClienteContaCartao ccc;
	
	public Cartao()
	{
	}   

	public Cartao(Date dataEmissao)
	{	this.dataEmissao = dataEmissao;
	}

	public Long getId()
	{	return id;
	}
	
	@SuppressWarnings("unused")
	private void setId(Long id)
	{	this.id = id;
	}
	
	public Date getDataEmissao()
	{	return dataEmissao;
	}
	
	public void setDataEmissao(Date dataEmissao)
	{	this.dataEmissao = dataEmissao;
	}

	public ClienteContaCartao getCcc()
	{	return ccc;
	}

	public void setCcc(ClienteContaCartao ccc)
	{	this.ccc = ccc;
	}
}

