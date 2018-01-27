package exercicio18a.produto;

import corejava.*;
import exercicio18a.util.*;
import exercicio18a.lance.Lance;

import java.util.Set;
import java.util.Iterator;

public class PrincipalProduto
{	public static void main (String[] args) 
	{	
		String nome;
		String descricao;
		String lanceMinimo;
		String dataCadastro;
		Produto umProduto;

		ProdutoDAO produtoDAO = new ProdutoDAO();

		boolean continua = true;
		while (continua)
		{	System.out.println('\n' + "O que voce deseja fazer?");
			System.out.println('\n' + "1. Cadastrar um produto");
			System.out.println("2. Alterar um produto");
			System.out.println("3. Remover um produto");
			System.out.println("4. Listar um produto e seus lances");
			System.out.println("5. Listar todos os produtos e seus lances");
			System.out.println("6. Sair");
						
			int opcao = Console.readInt('\n' + 
							"Digite um número entre 1 e 6:");
					
			switch (opcao)
			{	case 1:
				{
					nome = Console.readLine('\n' + 
						"Informe o nome do produto: ");
					descricao = Console.readLine(
						"Informe a descrição do produto: ");
					lanceMinimo = Console.readLine(
						"Informe o valor do lance mínimo: ");
					dataCadastro = Console.readLine(
						"Informe a data de cadastramento do produto: ");
						
					umProduto = new Produto();
					
					boolean deuErro = false;
					
					try
					{	umProduto.setNome(nome);
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + "Nome inválido");
						deuErro = true;
					}
					
					try
					{	umProduto.setDescricao(descricao);
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + "Descrição inválida");
						deuErro = true;
					}
					
					try
					{	umProduto.setLanceMinimo(lanceMinimo);
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + "Valor de lance inválido");
						deuErro = true;
					}

					try
					{
						umProduto.setDataCadastro(dataCadastro);
					}
					catch(AplicacaoException e)
					{
						System.out.println('\n' + e.getMessage());
						deuErro = true;
					}

					if (!deuErro)
					{	long numero = produtoDAO.inclui(umProduto);

						System.out.println('\n' + "Produto número " + 
						    numero + " incluido com sucesso!");	
					}

					break;
				}

				case 2:
				{	int resposta = Console.readInt('\n' + 
						"Digite o número do produto que voce deseja alterar: ");
										
					try
					{	umProduto = produtoDAO.recuperaUmProduto(resposta);
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"Número = " + umProduto.getId() + 
						"    Nome = " + umProduto.getNome() +
						"    Salario = " + umProduto.getDescricao());
												
					System.out.println('\n' + "O que voce deseja alterar?");
					System.out.println('\n' + "1. Nome");
					System.out.println("2. Descrição");

					int opcaoAlteracao = Console.readInt('\n' + 
											"Digite um número de 1 a 2:");
					
					switch (opcaoAlteracao)
					{	case 1:
							String novoNome = Console.
										readLine("Digite o novo nome: ");

							try
							{	umProduto.setNome(novoNome);

								produtoDAO.altera(umProduto);

								System.out.println('\n' + 
									"Alteração de nome efetuada com sucesso!");
							}
							catch(AplicacaoException e)
							{	System.out.println('\n' + "Nome inválido");
							}
								
							break;
					
						case 2:
							String novaDescricao = Console.
									readLine("Digite a nova descrição: ");

							try
							{	umProduto.setDescricao(novaDescricao);

								produtoDAO.altera(umProduto);
								
								System.out.println('\n' + 
									"Alteração de descrição efetuada " +
									"com sucesso!");						
							}
							catch(AplicacaoException e)
							{	System.out.println('\n' + "Descrição inválida");
							}

							break;

						default:
							System.out.println('\n' + "Opcao inválida!");
					}

					break;
				}

				case 3:
				{	int resposta = Console.readInt('\n' + 
						"Digite o número do produto que voce deseja remover: ");
									
					try
					{	umProduto = produtoDAO.
										recuperaUmProdutoELances(resposta);
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					if(umProduto.getLances().size() > 0)
					{	System.out.println('\n' + 
							"Este produto possui lances e nao pode ser removido.");
						break;
					}	

					System.out.println('\n' + 
						"Número = " + umProduto.getId() + 
						"    Nome = " + umProduto.getNome() +
						"    Descrição = " + umProduto.getDescricao());
														
					String resp = Console.readLine('\n' + 
						"Confirma a remocao do produto?");

					if(resp.equals("s"))
					{	produtoDAO.exclui (umProduto);
						System.out.println('\n' + 
							"Produto removido com sucesso!");
					}
					else
					{	System.out.println('\n' + 
							"Produto nao removido.");
					}
					
					break;
				}

				case 4:
				{	
					long numero = Console.readInt('\n' + 
						"Informe o número do produto: ");
				
					try
					{	umProduto = produtoDAO.
										recuperaUmProdutoELances(numero);
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
									
					System.out.println('\n' + 
						"Id = " + umProduto.getId() +
					    "  Nome = " + umProduto.getNome() +
					    "  Descrição = " + umProduto.getDescricao() +
					    "  Lance mínimo = " + umProduto.getLanceMinimo() +
					    "  Data Cadastro = " + umProduto.getDataCadastroMasc());
					
					Set lances = umProduto.getLances();
					
					for (Iterator it = lances.iterator(); it.hasNext(); )
					{	Lance lance = (Lance) it.next();
						System.out.println(	'\n' + 
							"      Lance número = "  + lance.getId() + 
							"  valor = "  + lance.getValor() +
							"  Data = "  + lance.getDataCriacaoMasc());
					}	
										
					break;
				}

				case 5:
				{
					Set produtos = produtoDAO.recuperaProdutosELances();
						
					if (produtos.size() != 0)
					{	System.out.println("");

						for (Iterator it = produtos.iterator(); 
						              it.hasNext(); )
						{	Produto produto = (Produto) it.next();
							System.out.println('\n' + 
								"Produto número = " + produto.getId() + 
								"  Nome = " + produto.getNome() +
								"  Descrição = " + produto.getDescricao() +
								"  Lance mínimo = " + produto.getLanceMinimoMasc() +
								"  Data Cadastro = " + produto.getDataCadastroMasc());

							Set lances = produto.getLances();
							
							for (Iterator it2 = lances.iterator(); 
							              it2.hasNext();)
							{	Lance lance = (Lance) it2.next();
								System.out.println(	'\n' + 
								  "      Lance número = "  + lance.getId() + 
								  "  valor = " + lance.getValor() +
								  "  Data = " + lance.getDataCriacaoMasc());
							}	
                    	} 
					}
					else
					{	System.out.println('\n' + 
						  "Nao ha produtos cadastrados com esta descrição.");
					}

					break;
				}

				case 6:
				{	continua = false;
					break;
				}

				default:
					System.out.println('\n' + "Opção inválida!");
			}
		}		
	}
}
