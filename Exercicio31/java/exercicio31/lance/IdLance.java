package exercicio31.lance;

import java.io.Serializable;

public class IdLance implements Serializable
{	
	private final static long serialVersionUID = 1;	
	
	private long idProduto;
	private long numeroLance;

	/* **********  Métodos Construtores  ********** */  
	
	public IdLance()
	{
	}

	public IdLance(long idProduto, long numeroLance)
	{	this.idProduto = idProduto;
		this.numeroLance = numeroLance;
	}
	
	/*
	 *                    M U I T O     I M P O R T A N T E	
	 *
	 * Os métodos equals() e hashCode() só são necessários quando um 
	 * objeto recuperado em uma  sessão é acrescentado a uma coleção 
	 * criada em outra sessão.  Para saber se o objeto recuperado na 
	 * sessão anterior já existe  na coleção criada  na nova sessão, 
	 * são  utilizados os  métodos  hashCode() e equals().  O método 
	 * hashCode()  retorna um número que é  convertido na posição do 
	 * objeto  na  coleção  (um HashSet,  por  exemplo)  e o  método 
	 * equals() é utilizado para encontrar o objeto no caso de haver
	 * colisão.
	 * 
	 * Neste  exercício, se comentarmos  estes dois métodos (abaixo) 
	 * eles   não  farão   falta  pois   nunca  misturamos   objetos 
	 * recuperados em sessões diferentes.
	 */
	
//	public boolean equals(Object outro)
//	{	if(this == outro) return true;
//	
//		IdLance l = (IdLance)outro;
//		return (idProduto + "-" + numeroLance)
//			.equals(l.idProduto + "-" + l.numeroLance); 
//	}
//	
//	public int hashCode()
//	{	return (idProduto + "-" + numeroLance).hashCode(); 
//	}

	/* **********  Métodos do tipo get  ********** */  

	public long getIdProduto()
	{	return idProduto;
	}
	
	public long getNumeroLance()
	{	return numeroLance;
	}
	
	/* **********  Métodos do tipo set  ********** */  

	@SuppressWarnings("unused")
	private void setIdProduto(long idProduto)
	{	this.idProduto = idProduto;
	}

	@SuppressWarnings("unused")
	private void setNumeroLance(long numeroLance)
	{	this.numeroLance = numeroLance;
	}
}
