package exercicio12;

import corejava.*;

import java.util.Iterator;
import java.util.List;

public class Principal
{	public static void main (String[] args) 
	{	
		String nome;
		String descricao;
		double lanceMinimo;
		String dataCadastro;
		Produto umProduto;

		ProdutoDAO produtoDAO = new ProdutoDAO();

		boolean continua = true;
		while (continua)
		{	System.out.println('\n' + "O que voce deseja fazer?");
			System.out.println('\n' + "1. Cadastrar um produto");
			System.out.println("2. Alterar um produto");
			System.out.println("3. Remover um produto");
			System.out.println("4. Listar um produto");
			System.out.println("5. Listar todos os produtos");
			System.out.println("6. Sair");
						
			int opcao = Console.readInt('\n' + 
							"Digite um numero entre 1 e 6:");			
					
			switch (opcao)
			{	case 1:
				{
					nome = Console.readLine('\n' + 
						"Informe o nome do produto: ");
					descricao = Console.readLine(
						"Informe a descricao do produto: ");
					lanceMinimo = Console.readDouble(
						"Informe o valor do lance minimo: ");
					dataCadastro = Console.readLine(
						"Informe a data de cadastramento do produto: ");
						
					umProduto = new Produto(nome, 
					                        descricao, 
					                        lanceMinimo, 
					                        Util.strToDate(dataCadastro));
			
					long numero = produtoDAO.inclui(umProduto);

					System.out.println('\n' + "Produto numero " + 
						    numero + " incluido com sucesso!");	

					break;
				}

				case 2:
				{	int resposta = Console.readInt('\n' + 
					  "Digite o numero do produto que voce deseja alterar: ");
										
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
					System.out.println("2. Descricao");

					int opcaoAlteracao = Console.readInt('\n' + 
											"Digite um numero de 1 a 2:");
					
					switch (opcaoAlteracao)
					{	case 1:
							String novoNome = Console.
										readLine("Digite o novo nome: ");

							umProduto.setNome(novoNome);

							produtoDAO.altera(umProduto);

							System.out.println('\n' + 
								  "Alteracao de nome efetuada com sucesso!");
								
							break;

						case 2:
							String novaDescricao = Console.
								readLine('\n' +"Digite a nova descricao: ");

							umProduto.setDescricao(novaDescricao);

							produtoDAO.altera(umProduto);

							System.out.println('\n' + 
									"Alteracao de descricao efetuada " +
									"com sucesso!");						
								
							break;

						default:
							System.out.println('\n' + "Opcao invalida!");
					}

					break;
				}

				case 3:
				{	int resposta = Console.readInt('\n' + 
					  "Digite o numero do produto que voce deseja remover: ");
									
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
						"    Descricao = " + umProduto.getDescricao());
														
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
					long resposta = Console.readInt('\n' +
						"Informe o numero do produto: ");
				
					try
					{	umProduto = produtoDAO.recuperaUmProduto(resposta);
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
									
					System.out.println('\n' +
						"Id = " + umProduto.getId() +
					    "  Nome = " + umProduto.getNome() +
					    "  Descricao = " + umProduto.getDescricao() +
					    "  Lance minimo = " + umProduto.getLanceMinimo() +
					    "  Data Cadastro = " + 
					    		umProduto.getDataCadastroMasc());
					
					break;
				}

				case 5:
				{
					List produtos = produtoDAO.recuperaProdutos();
						
					if (produtos.size() != 0)
					{	System.out.println("");

						for (Iterator it = produtos.iterator(); it.hasNext(); )
						{	Produto produto = (Produto) it.next();
							System.out.println('\n' + 
								"Produto numero = \""  + produto.getId() + 
								"\"  Nome = \""  + produto.getNome() +
								"\"  Descricao = \""  + produto.getDescricao() +
								"\"  Lance minimo = \""  + 
										produto.getLanceMinimoMasc() + "\"");
                    	} 
					}
					else
					{	System.out.println('\n' + 
							"Nao ha produtos cadastrados com esta descricao.");
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
