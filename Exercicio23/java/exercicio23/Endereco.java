package exercicio23;

public class Endereco
{
	private String rua;

	private String numero;

	private String complemento;

	/** *** Métodos Construtores **** */

	public Endereco()
	{
	}

	public Endereco(String rua, String numero, String complemento)
	{
		this.rua = rua;
		this.numero = numero;
		this.complemento = complemento;
	}

	/** *** Métodos do tipo get **** */

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

	/** *** Métodos do tipo set **** */

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
