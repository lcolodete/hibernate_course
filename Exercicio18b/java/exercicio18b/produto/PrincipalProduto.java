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
		{	System.out.println('\n' + "O que voc� deseja fazer?");
			System.out.println('\n' + "1. Cadastrar um produto");
			System.out.println("2. Alterar um produto");
			System.out.println("3. Remover um produto");
			System.out.println("4. Listar um produto e seus lances");
			System.out.println("5. Listar todos os produtos e seus lances");
			System.out.println("6. Sair");
						
			int opcao = Console.readInt('\n' + 
							"Digite um n�mero entre 1 e 6:");
					
			switch (opcao)
			{	case 1:
				{
					nome = Console.readLine('\n' + 
						"Informe o nome do produto: ");
					descricao = Console.readLine(
						"Informe a descri��o do produto: ");
					lanceMinimo = Console.readLine(
						"Informe o valor do lance m�nimo: ");
					dataCadastro = Console.readLine(
						"Informe a data de cadastramento do produto: ");
						
					try
					{
						long numero = produtoAppService.inclui(nome, descricao, lanceMinimo, dataCadastro);
					
						System.out.println('\n' + "Produto n�mero " + 
						    numero + " inclu�do com sucesso!");	
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
						"Digite o n�mero do produto que voc� deseja alterar: ");
										
					try
					{	
						umProduto = produtoAppService.recuperaUmProduto(resposta);
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"N�mero = " + umProduto.getId() + 
						"    Nome = " + umProduto.getNome() +
						"    Sal�rio = " + umProduto.getDescricao());
												
					System.out.println('\n' + "O que voc� deseja alterar?");
					System.out.println('\n' + "1. Nome");
					System.out.println("2. Descri��o");

					int opcaoAlteracao = Console.readInt('\n' + 
											"Digite um n�mero de 1 a 2:");
					
					switch (opcaoAlteracao)
					{	case 1:
							String novoNome = Console.
										readLine("Digite o novo nome: ");

							try
							{
								umProduto.setNome(novoNome);

								produtoAppService.altera(umProduto);

								System.out.println('\n' + 
									"Altera��o de nome efetuada com sucesso!");
							}
							catch(AplicacaoException e)
							{	System.out.println('\n' + "Nome inv�lido");
							}
								
							break;
					
						case 2:
							String novaDescricao = Console.
									readLine("Digite a nova descri��o: ");

							try
							{	umProduto.setDescricao(novaDescricao);

								produtoAppService.altera(umProduto);
								
								System.out.println('\n' + 
									"Altera��o de descri��o efetuada " +
									"com sucesso!");						
							}
							catch(AplicacaoException e)
							{	System.out.println('\n' + "Descri��o inv�lida");
							}

							break;

						default:
							System.out.println('\n' + "Op��o inv�lida!");
					}

					break;
				}

				case 3:
				{	int resposta = Console.readInt('\n' + 
						"Digite o n�mero do produto que voc� deseja remover: ");
									
					try
					{	umProduto = produtoAppService.
										recuperaUmProduto(resposta);
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"N�mero = " + umProduto.getId() + 
						"    Nome = " + umProduto.getNome() +
						"    Descri��o = " + umProduto.getDescricao());
														
					String resp = Console.readLine('\n' + 
						"Confirma a remo��o do produto?");

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
							"Produto n�o removido.");
					}
					
					break;
				}

				case 4:
				{	
					long numero = Console.readInt('\n' + 
						"Informe o n�mero do produto: ");
				
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
					    "  Descri��o = " + umProduto.getDescricao() +
					    "  Lance m�nimo = " + umProduto.getLanceMinimo() +
					    "  Data Cadastro = " + umProduto.getDataCadastroMasc());
					
					Set lances = umProduto.getLances();
					
					for (Iterator it = lances.iterator(); it.hasNext(); )
					{	Lance lance = (Lance) it.next();
						System.out.println(	'\n' + 
							"      Lance n�mero = "  + lance.getId() + 
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
								"  Descri��o = " + produto.getDescricao() +
								"  Lance m�nimo = " + produto.getLanceMinimoMasc() +
								"  Data Cadastro = " + produto.getDataCadastroMasc());

							Set lances = produto.getLances();
							
							for (Iterator it2 = lances.iterator(); 
							              it2.hasNext();)
							{	Lance lance = (Lance) it2.next();
								System.out.println(	'\n' + 
								  "      Lance n�mero = "  + lance.getId() + 
								  "  Valor = " + lance.getValor() +
								  "  Data = " + lance.getDataCriacaoMasc());
							}	
                    	} 
					}
					else
					{	System.out.println('\n' + 
						  "N�o h� produtos cadastrados com esta descri��o.");
					}

					break;
				}

				case 6:
				{	continua = false;
					break;
				}

				default:
					System.out.println('\n' + "Op��o inv�lida!");
			}
		}		
	}
}
