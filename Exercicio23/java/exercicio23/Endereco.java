package exercicio23;

public class Endereco
{
	private String rua;

	private String numero;

	private String complemento;

	/** *** M�todos Construtores **** */

	public Endereco()
	{
	}

	public Endereco(String rua, String numero, String complemento)
	{
		this.rua = rua;
		this.numero = numero;
		this.complemento = complemento;
	}

	/** *** M�todos do tipo get **** */

	public String getRua()
	{
		return rua;
	}

	public String getComplemento()
	{
		return complemento;
	}

	public String getNumero()
	{
		return numero;
	}

	/** *** M�todos do tipo set **** */

	public void setNumero(String numero)
	{
		this.numero = numero;
	}

	public void setRua(String rua)
	{
		this.rua = rua;
	}

	public void setComplemento(String complemento)
	{
		this.complemento = complemento;
	}
}
