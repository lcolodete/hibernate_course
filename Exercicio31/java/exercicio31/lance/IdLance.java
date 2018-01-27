package exercicio31.lance;

import java.io.Serializable;

public class IdLance implements Serializable
{	
	private final static long serialVersionUID = 1;	
	
	private long idProduto;
	private long numeroLance;

	/* **********  M�todos Construtores  ********** */  
	
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
	 * Os m�todos equals() e hashCode() s� s�o necess�rios quando um 
	 * objeto recuperado em uma  sess�o � acrescentado a uma cole��o 
	 * criada em outra sess�o.  Para saber se o objeto recuperado na 
	 * sess�o anterior j� existe  na cole��o criada  na nova sess�o, 
	 * s�o  utilizados os  m�todos  hashCode() e equals().  O m�todo 
	 * hashCode()  retorna um n�mero que �  convertido na posi��o do 
	 * objeto  na  cole��o  (um HashSet,  por  exemplo)  e o  m�todo 
	 * equals() � utilizado para encontrar o objeto no caso de haver
	 * colis�o.
	 * 
	 * Neste  exerc�cio, se comentarmos  estes dois m�todos (abaixo) 
	 * eles   n�o  far�o   falta  pois   nunca  misturamos   objetos 
	 * recuperados em sess�es diferentes.
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

	/* **********  M�todos do tipo get  ********** */  

	public long getIdProduto()
	{	return idProduto;
	}
	
	public long getNumeroLance()
	{	return numeroLance;
	}
	
	/* **********  M�todos do tipo set  ********** */  

	@SuppressWarnings("unused")
	private void setIdProduto(long idProduto)
	{	this.idProduto = idProduto;
	}

	@SuppressWarnings("unused")
	private void setNumeroLance(long numeroLance)
	{	this.numeroLance = numeroLance;
	}
}
