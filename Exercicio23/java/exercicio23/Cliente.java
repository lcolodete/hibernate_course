package exercicio23;

public class Cliente
{
	private Long numero;

	private String nome;

	private double salario;

	private Endereco enderecoResidencial;

	private Endereco enderecoComercial;

	/** *** Métodos Construtores **** */

	public Cliente()
	{
	}

	public Cliente(String nome, double salario, Endereco enderecoResidencial,
			Endereco enderecoComercial)
	{
		this.nome = nome;
		this.salario = salario;
		this.enderecoResidencial = enderecoResidencial;
		this.enderecoComercial = enderecoComercial;
	}

	/** *** Métodos do tipo get **** */

	public Long getNumero()
	{
		return numero;
	}

	public String getNome()
	{
		return nome;
	}

	public double getSalario()
	{
		return salario;
	}

	public Endereco getEnderecoResidencial()
	{
		return enderecoResidencial;
	}

	public Endereco getEnderecoComercial()
	{
		return enderecoComercial;
	}

	/** *** Métodos do tipo set **** */

	@SuppressWarnings("unused")
	private void setNumero(Long numero)
	{
		this.numero = numero;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public void setSalario(double salario)
	{
		this.salario = salario;
	}

	/* ==> */
	public void setEnderecoResidencial(Endereco enderecoResidencial)
	{
		this.enderecoResidencial = enderecoResidencial;
	}

	/* ==> */
	public void setEnderecoComercial(Endereco enderecoComercial)
	{
		this.enderecoComercial = enderecoComercial;
	}
}