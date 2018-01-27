package exercicio31.lance;

import corejava.*;
import exercicio31.util.*;
import exercicio31.produto.*;

import java.util.List;

public class PrincipalLance
{	public static void main (String[] args) 
	{	
		String valor;
		String dataCriacao;

		Produto umProduto;
		Lance umLance;

		ProdutoAppService produtoAppService = new ProdutoAppService();
		LanceAppService lanceAppService = new LanceAppService();

		boolean continua = true;
		while (continua)
		{	System.out.println('\n' + "O que voce deseja fazer?");
			System.out.println('\n' + "1. Cadastrar um lance de um produto");
			System.out.println("2. Remover um lance");
			System.out.println("3. Recuperar todos os lances");
			System.out.println("4. Sair");
						
			int opcao = Console.readInt('\n' + 
							"Digite um numero entre 1 e 4:");			
					
			switch (opcao)
			{	case 1:
				{
/* ==> */			long idProduto = Console.
						readInt('\n' + "Informe o numero do produto: ");
					
					try
					{	umProduto = produtoAppService
							.recuperaUmProduto(idProduto);
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}

/* ==> */			long numeroLance = Console.readInt('\n' +
							"Informe o numero do lance para este produto: ");

					// ficou com pregui�a, por isso pediu para o usuario digitar
					// o certo seria fazer um select para saber qual o numero do 
					// ultimo lance para esse produto

					valor = Console.readLine( 
							"Informe o valor do lance: ");
					dataCriacao = Console.readLine(
							"Informe a data de emissao do lance: ");

					try
/* ==> */			{	lanceAppService.inclui(idProduto, 
		                                       numeroLance, 
									           valor, 
									           dataCriacao, 
									           umProduto);	

						System.out.println('\n' + "Lance adicionado com sucesso");						
					}
					catch(AplicacaoException e)
					{	System.out.println("");

						for (int i=0; i<e.getMensagens().size(); i++)
						{	System.out.println (e.getMensagens().get(i));
						}
					}
						
					break;
				}

				case 2:
				{	
				
/* ==> */			long idProduto = Console.readInt('\n' + "Informe o numero do produto: ");
/* ==> */			long numeroLance = Console.readInt("Informe o numero do lance: ");
									
					try
/* ==> */			{	umLance = lanceAppService
							.recuperaUmLance(idProduto, numeroLance);
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
				
					System.out.println('\n' + 
/* ==> */				"Numero do produto = " + umLance.getId().getIdProduto() + 
/* ==> */				"  Numero do lance = " + umLance.getId().getNumeroLance() + 
						"  Valor = " + umLance.getValor() +
						"  Data de Emissao = " + umLance.getDataCriacao());
																						
					String resp = Console.readLine('\n' + 
						"Confirma a remocao do lance?");

					if(resp.equals("s"))
					{	lanceAppService.exclui (umLance);
						System.out.println('\n' + 
								"Lance removido com sucesso!");
					}
					else
					{	System.out.println('\n' + 
							"Lance nao removido.");
					}
					
					break;
				}

				case 3:
				{	List arrayLances = lanceAppService.recuperaLances();
									
					if (arrayLances.size() == 0)
					{	System.out.println('\n' + 
							"Nao ha lances cadastrados.");
						break;
					}
										
					System.out.println("");
					int i;
					for (i = 0; i < arrayLances.size(); i++)
					{	umLance = (Lance)arrayLances.get(i);
						System.out.println(	
							"Produto N. " + umLance.getId().getIdProduto() + 
							"  Lance N. " + umLance.getId().getNumeroLance() + 
							"  Valor = " + umLance.getValor() +
							"  Data de Emissao = " + 
									umLance.getDataCriacao());
					}
										
					break;
				}

				case 4:
				{	continua = false;
					break;
				}

				default:
					System.out.println('\n' + "Op��o inv�lida!");						
			}
		}		
	}
}