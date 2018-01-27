package exercicio14.lance;

import corejava.*;
import exercicio14.util.*;
import exercicio14.produto.*;

import java.sql.Date;
import java.util.List;

public class PrincipalLance
{
	public static void main (String[] args) 
	{	
		double valor;
		Date dataCriacao;
		String umaData;

		Lance umLance;

		ProdutoDAO produtoDAO = new ProdutoDAO();
		LanceDAO lanceDAO = new LanceDAO();

		boolean continua = true;
		while (continua)
		{	System.out.println('\n' + "O que voce deseja fazer?");
/* ==> */   System.out.println('\n' + "1. Cadastrar um lance de um produto");
/* ==> */   System.out.println("2. Remover um lance");
/* ==> */   System.out.println("3. Recuperar todos os lances");
			System.out.println("4. Sair");
						
			int opcao = Console.readInt('\n' + 
				"Digite um numero entre 1 e 4:");			
					
			switch (opcao)
			{
				case 1:
				{
					long idProduto = Console.readInt('\n' + 
									"Informe o numero do produto: ");
					valor = Console.readDouble('\n' + 
									"Informe o valor do lance: ");
					umaData = Console.readLine('\n' +
									"Informe a data de emissao do lance: ");
					dataCriacao = Util.strToDate(umaData);
								
					umLance = new Lance (valor, dataCriacao);
					
					try
					{
						produtoDAO.recuperaUmProduto(idProduto);
					}
					catch(AplicacaoException e)
					{	
						System.out.println('\n' + e.getMessage());
						break;
					}
					
					// Adiciona o lance ao produto
					//
					produtoDAO.adicionarLance(idProduto, umLance);

					System.out.println('\n' + "Lance adicionado com sucesso");						

					break;
				}

				case 2:
				{	
					int resposta = Console.readInt('\n' + 
						"Digite o numero do lance que voce deseja remover: ");
									
					try
					{	
						umLance = lanceDAO.recuperaUmLance(resposta);	
					}
					catch(AplicacaoException e)
					{
						System.out.println('\n' + e.getMessage());
						break;
					}
					
					// Como associações single-point são recuperadas?
					// System.out.println(umLance.getProduto()
					//						  .getClass()
					//						  .getName());							

					System.out.println("Classe = " + umLance
														.getProduto()
														.getClass()
														.getName());							
					// Se tirarmos o comentário abaixo ocorrerá um  erro
					// uma vez que a  sessão que  recuperou o  lance  já
					// foi fechada.
					
					// System.out.println(umLance.getProduto().getNome());							
										
					System.out.println('\n' + 
						"Número = " + umLance.getId() + 
						"    Valor = " + umLance.getValor() +
						"    Data de Emissao = " + umLance.getDataCriacao());
																						
					String resp = Console.readLine('\n' + 
						"Confirma a remocao do lance?");

					if(resp.equals("s"))
					{	lanceDAO.exclui (umLance);
						System.out.println('\n' + "Lance removido com sucesso!");
					}
					else
					{	System.out.println('\n' + "Lance nao removido.");
					}
					
					break;
				}

				case 3:
				{	
					List arrayLances = lanceDAO.recuperaLances();
									
					if (arrayLances.size() == 0)
					{
						System.out.println('\n' + 
							"Nao ha lances cadastrados.");
						break;
					}
										
					System.out.println("");
					int i;
					for (i = 0; i < arrayLances.size(); i++)
					{	umLance = (Lance)arrayLances.get(i);
						System.out.println(	
							"Numero = " + umLance.getId() + 
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
					System.out.println('\n' + "Opção inválida!");						
			}
		}		
	}
}
