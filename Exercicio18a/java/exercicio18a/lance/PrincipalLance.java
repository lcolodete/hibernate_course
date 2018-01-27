package exercicio18a.lance;

import corejava.*;
import exercicio18a.util.*;
import exercicio18a.produto.*;

import java.sql.Date;
import java.util.List;

public class PrincipalLance
{	public static void main (String[] args) 
	{	
		String valor;
		String dataCriacao;

		Produto umProduto;
		Lance umLance;

		ProdutoDAO produtoDAO = new ProdutoDAO();
		LanceDAO lanceDAO = new LanceDAO();

		boolean continua = true;
		while (continua)
		{	System.out.println('\n' + "O que voce deseja fazer?");
			System.out.println('\n' + "1. Cadastrar um lance de um produto");
			System.out.println("2. Remover um lance");
			System.out.println("3. Recuperar todos os lances");
			System.out.println("4. Sair");
						
			int opcao = Console.readInt('\n' + 
							"Digite um número entre 1 e 4:");			
					
			switch (opcao)
			{	case 1:
				{
					long idProduto = Console.
						readInt('\n' + "Informe o número do produto: ");
					
					try
					{	umProduto = produtoDAO.recuperaUmProduto(idProduto);
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
					
					valor = Console.readLine('\n' + 
									"Informe o valor do lance: ");
					dataCriacao = Console.readLine(
									"Informe a data de emissao do lance: ");
									
					umLance = new Lance();	// Construtor com argumentos 
											// deixa de existir.
					
					Lance ultimoLance = produtoDAO.recuperaUltimoLance(umProduto);

					double valorUltimoLance;
					Date   dataUltimoLance;
					
					if (ultimoLance == null)
					{
						valorUltimoLance = umProduto.getLanceMinimo() - 1;
						dataUltimoLance = umProduto.getDataCadastro();
					}
					else
					{
						valorUltimoLance = ultimoLance.getValor();
						dataUltimoLance = ultimoLance.getDataCriacao();
					}
					
					boolean deuErro = false;
					
					try
					{	
						umLance.setValor(valor, valorUltimoLance);
					}
					catch(AplicacaoException e)
					{
						System.out.println('\n' + e.getMessage());
						deuErro = true;
					}
					
					try
					{
						umLance.setDataCriacao(dataCriacao, dataUltimoLance);
					}
					catch(AplicacaoException e)
					{	
						System.out.println('\n' + e.getMessage());
						deuErro = true;
					}

					if (!deuErro)
					{	umLance.setProduto(umProduto); // Evita a recuperacao 
													   // de todos os lances 
													   // de um produto.
						lanceDAO.inclui(umLance);

						System.out.println('\n' + "Lance adicionado com sucesso");
					}

					break;
				}

				case 2:
				{	int resposta = Console.readInt('\n' + 
						"Digite o número do lance que você deseja remover: ");
									
					try
					{	umLance = lanceDAO.recuperaUmLance(resposta);
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"Número = " + umLance.getId() + 
						"    Valor = " + umLance.getValor() +
						"    Data de Emissão = " + umLance.getDataCriacao());
																						
					String resp = Console.readLine('\n' + 
						"Confirma a remoção do lance?");

					if(resp.equals("s"))
					{	lanceDAO.exclui (umLance);
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
				{	List arrayLances = lanceDAO.recuperaLances();
									
					if (arrayLances.size() == 0)
					{	System.out.println('\n' + 
							"Nao há lances cadastrados.");
						break;
					}
										
					System.out.println("");
					int i;
					for (i = 0; i < arrayLances.size(); i++)
					{	umLance = (Lance)arrayLances.get(i);
						System.out.println(	
							"Número = " + umLance.getId() + 
							"  Valor = " + umLance.getValor() +
							"  Data de Emissão = " + 
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
