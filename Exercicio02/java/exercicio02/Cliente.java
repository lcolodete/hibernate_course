package exercicio02;

public class Cliente
{	
	/* O atributo identificador permite � aplica��o acessar a identidade de 
	 * banco de dados - o valor da chave prim�ria - de um objeto persistente. 
	 * Se dois objetos Cliente possuem o mesmo valor para o atributo 
	 * identificador ent�o eles representam a mesma linha no banco de dados. 
	 *
	 * O hibernate permite praticamente qualquer tipo para o atributo 
	 * identificador. A classe deve possuir um construtor sem argumentos. 
	 *
	 * Observe que a classe Cliente n�o tem nenhuma depend�ncia com o 
	 * Hibernate.
	 */
	
	private Long numero;
	private String nome;
	private double salario;

//  M�todos construtores

	public Cliente ()
	{	
	}

	public Cliente (String nome, double salario)
	{	this.nome = nome;
		this.salario = salario;
	}

//  M�todos do tipo get

	public Long getNumero()
	{	return numero;
	}

	public String getNome()
	{	return nome;
	}
		
	public double getSalario()
	{	return salario;
	}

//  M�todos do tipo set

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
}