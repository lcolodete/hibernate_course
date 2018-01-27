package exercicio44.lance;

import corejava.*;
import exercicio44.util.*;
import exercicio44.produto.*;
import exercicio44.cliente.*;

import java.util.Iterator;
import java.util.List;

public class PrincipalLance
{	public static void main (String[] args) 
	{	
		double valor;
		String dataCriacao;

		Produto umProduto;
		Cliente umCliente;
		Lance umLance;

		ClienteAppService clienteAppService = new ClienteAppService();
		ProdutoAppService produtoAppService = new ProdutoAppService();
		LanceAppService lanceAppService = new LanceAppService();

		boolean continua = true;
		while (continua)
		{	System.out.println('\n' + "O que voce deseja fazer?");
			System.out.println('\n' + "1. Cadastrar um lance de um produto");
			System.out.println("2. Remover um lance");
			System.out.println("3. Recuperar todos os lances");
			System.out.println("4. Recuperar todos os lances de um produto acima de um determinado valor");
			System.out.println("5. Sair");

			int opcao = Console.readInt('\n' + "Digite um numero entre 1 e 5:");			
					
			switch (opcao)
			{	case 1:
				{
					long idProduto = Console.
						readInt('\n' + "Informe o numero do produto: ");
					
					try
					{	umProduto = produtoAppService.recuperaUmProduto(idProduto);
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}

					long idCliente = Console.readInt('\n' + 
						"Informe o numero do cliente que emitiu o lance: ");

					try
					{	umCliente = clienteAppService.recuperaUmCliente(idCliente);
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
					
					valor = Console.readDouble('\n' + 
									"Informe o valor do lance: ");
					dataCriacao = Console.readLine(
									"Informe a data de emissao do lance: ");

					try
					{	lanceAppService.inclui(valor, Util.strToDate(dataCriacao), umProduto, umCliente);	

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
				{	int resposta = Console.readInt('\n' + 
						"Digite o numero do lance que voce deseja remover: ");
									
					try
					{	umLance = lanceAppService.recuperaUmLance(resposta);
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"Número = " + umLance.getId() + 
						"    Valor = " + umLance.getValor() +
						"    Data de Emissao = " + umLance.getDataCriacao());
																						
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
					{	System.out.println('\n' + "Nao ha lances cadastrados.");
						break;
					}
										
					System.out.println("");
					int i;
					for (i = 0; i < arrayLances.size(); i++)
					{	umLance = (Lance)arrayLances.get(i);
						System.out.println(	
							"Numero = " + umLance.getId() + 
							"  Valor = " + umLance.getValor() +
							"  Data de Emissao = " + umLance.getDataCriacao());
					}
										
					break;
				}

				case 4:
				{	// Recuperar os lances de um produto acima de um determinado valor - versao 1
				
					long id = Console.readInt('\n' + "Informe o numero do produto: ");
					double valorMinimo = Console.readDouble("Informe o valor minimo do lance desejado: ");
					
					Produto produto;
					
					try
					{	produto = produtoAppService.recuperaUmProduto(id);
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
					
/* ==> */			List lances = lanceAppService
						.recuperaLancesDeUmProduto(produto, valorMinimo); 
						
					if(lances.size() != 0)
					{	System.out.println("");
						for (Iterator it = lances.iterator(); it.hasNext(); )
						{	Lance lance = (Lance) it.next();
							System.out.println("     Lance numero = "  + lance.getId() + 
									   			"  Valor = "  + lance.getValor() +
									   			"  Data do lance = "  + lance.getDataCriacao());
						}
					}
					else
					{	System.out.println('\n' + "Nenhum lance cadastrado para este produto.");
					}
					
					break;
				}

				case 5:
				{	continua = false;
					break;
				}

				default:
					System.out.println('\n' + "Opção inválida!");						
			}
		}		
	}
}
