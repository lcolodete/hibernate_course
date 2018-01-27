package exercicio22.lance;

import corejava.*;
import exercicio22.util.*;
import exercicio22.produto.*;

import java.util.List;

import static java.lang.System.out;   

public class PrincipalLance
{	public static void main (String[] args) 
	{	
		double valor;
		String dataCriacao;

		Produto umProduto;
		Lance umLance;

		ProdutoAppService produtoAppService = new ProdutoAppService();
		LanceAppService lanceAppService = new LanceAppService();

		boolean continua = true;
		while (continua)
		{	out.println('\n' + "O que voce deseja fazer?");
			out.println('\n' + "1. Cadastrar um lance de um produto");
			out.println("2. Remover um lance");
			out.println("3. Recuperar todos os lances");
			out.println("4. Sair");
						
			int opcao = Console.readInt('\n' + "Digite um numero entre 1 e 4:");			
					
			switch (opcao)
			{	case 1:
				{
					long idProduto = Console.
						readInt('\n' + "Informe o numero do produto: ");
					
					try
					{	umProduto = produtoAppService.recuperaUmProduto(idProduto);
					}
					catch(AplicacaoException e)
					{	out.println('\n' + e.getMessage());
						break;
					}
					
					valor = Console.readDouble('\n' + 
									"Informe o valor do lance: ");
					dataCriacao = Console.readLine(
									"Informe a data de emissao do lance: ");

					try
					{	lanceAppService.inclui(valor, Util.strToDate(dataCriacao), umProduto);	

						out.println('\n' + "Lance adicionado com sucesso");						
					}
					catch(AplicacaoException e)
					{	out.println("");

						for (int i=0; i<e.getMensagens().size(); i++)
						{	out.println (e.getMensagens().get(i));
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
					{	out.println('\n' + e.getMessage());
						break;
					}
										
					out.println('\n' + 
						"Número = " + umLance.getId() + 
						"    Valor = " + umLance.getValor() +
						"    Data de Emissao = " + umLance.getDataCriacao());
																						
					String resp = Console.readLine('\n' + 
						"Confirma a remocao do lance?");

					if(resp.equals("s"))
					{	lanceAppService.exclui (umLance);
						out.println('\n' + 
								"Lance removido com sucesso!");
					}
					else
					{	out.println('\n' + 
							"Lance nao removido.");
					}
					
					break;
				}

				case 3:
				{	List arrayLances = lanceAppService.recuperaLances();
									
					if (arrayLances.size() == 0)
					{	out.println('\n' + "Nao ha lances cadastrados.");
						break;
					}
										
					out.println("");
					int i;
					for (i = 0; i < arrayLances.size(); i++)
					{	umLance = (Lance)arrayLances.get(i);
						out.println(	
							"Numero = " + umLance.getId() + 
							"  Valor = " + umLance.getValor() +
							"  Data de Emissao = " + umLance.getDataCriacao());
					}
										
					break;
				}

				case 4:
				{	continua = false;
					break;
				}

				default:
					out.println('\n' + "Opção inválida!");						
			}
		}		
	}
}
