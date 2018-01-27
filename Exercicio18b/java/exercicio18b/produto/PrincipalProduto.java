package exercicio18b.produto;

import corejava.*;
import exercicio18b.util.*;
import exercicio18b.lance.Lance;

import java.util.Set;
import java.util.Iterator;

public class PrincipalProduto {
	public static void main (String[] args) 
	{	
		String nome;
		String descricao;
		String lanceMinimo;
		String dataCadastro;
		Produto umProduto;

		ProdutoAppService produtoAppService = new ProdutoAppService();

		boolean continua = true;
		while (continua)
		{	System.out.println('\n' + "O que você deseja fazer?");
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
						
					try
					{
						long numero = produtoAppService.inclui(nome, descricao, lanceMinimo, dataCadastro);
					
						System.out.println('\n' + "Produto número " + 
						    numero + " incluído com sucesso!");	
					}
					catch(AplicacaoException e)
					{
						System.out.println("");

						for (int i=0; i<e.getMensagens().size(); i++)
						{
							System.out.println (e.getMensagens().get(i));
						}
					}

					break;
				}

				case 2:
				{	int resposta = Console.readInt('\n' + 
						"Digite o número do produto que você deseja alterar: ");
										
					try
					{	
						umProduto = produtoAppService.recuperaUmProduto(resposta);
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"Número = " + umProduto.getId() + 
						"    Nome = " + umProduto.getNome() +
						"    Salário = " + umProduto.getDescricao());
												
					System.out.println('\n' + "O que você deseja alterar?");
					System.out.println('\n' + "1. Nome");
					System.out.println("2. Descrição");

					int opcaoAlteracao = Console.readInt('\n' + 
											"Digite um número de 1 a 2:");
					
					switch (opcaoAlteracao)
					{	case 1:
							String novoNome = Console.
										readLine("Digite o novo nome: ");

							try
							{
								umProduto.setNome(novoNome);

								produtoAppService.altera(umProduto);

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

								produtoAppService.altera(umProduto);
								
								System.out.println('\n' + 
									"Alteração de descrição efetuada " +
									"com sucesso!");						
							}
							catch(AplicacaoException e)
							{	System.out.println('\n' + "Descrição inválida");
							}

							break;

						default:
							System.out.println('\n' + "Opção inválida!");
					}

					break;
				}

				case 3:
				{	int resposta = Console.readInt('\n' + 
						"Digite o número do produto que você deseja remover: ");
									
					try
					{	umProduto = produtoAppService.
										recuperaUmProduto(resposta);
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"Número = " + umProduto.getId() + 
						"    Nome = " + umProduto.getNome() +
						"    Descrição = " + umProduto.getDescricao());
														
					String resp = Console.readLine('\n' + 
						"Confirma a remoção do produto?");

					if(resp.equals("s"))
					{	try
						{	produtoAppService.exclui (umProduto);
							System.out.println('\n' + 
								"Produto removido com sucesso!");
						}
						catch(AplicacaoException e)
						{	System.out.println('\n' + e.getMessage());
						}
					}
					else
					{	System.out.println('\n' + 
							"Produto não removido.");
					}
					
					break;
				}

				case 4:
				{	
					long numero = Console.readInt('\n' + 
						"Informe o número do produto: ");
				
					try
					{	umProduto = produtoAppService.
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
							"  Valor = "  + lance.getValor() +
							"  Data = "  + lance.getDataCriacaoMasc());
					}	
										
					break;
				}

				case 5:
				{
					Set produtos = produtoAppService.recuperaProdutosELances();
						
					if (produtos.size() != 0)
					{	System.out.println("");

						for (Iterator it = produtos.iterator(); 
						              it.hasNext(); )
						{	Produto produto = (Produto) it.next();
							System.out.println('\n' + 
								"Produto numero = " + produto.getId() + 
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
								  "  Valor = " + lance.getValor() +
								  "  Data = " + lance.getDataCriacaoMasc());
							}	
                    	} 
					}
					else
					{	System.out.println('\n' + 
						  "Não há produtos cadastrados com esta descrição.");
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
