package exercicio42;

import corejava.*;
import java.util.Iterator;

public class Principal
{
	public static void main(String[] args)
	{
		String nome;
		String descricao;
		double lanceMinimo;
		String dataCadastro;
		Produto umProduto;

		ProdutoAppService produtoAppService = new ProdutoAppService();

		boolean continua = true;
		while (continua)
		{
			System.out.println('\n' + "O que voce deseja fazer?");
			System.out.println('\n' + "1. Cadastrar um produto");
			System.out.println("2. Alterar um produto");
			System.out.println("3. Remover um produto");
			System.out
					.println("4. Recuperar produtos com iterate() em vez de list() - HQL");
			System.out.println("5. Sair");

			int opcao = Console.readInt('\n' + "Digite um numero entre 1 e 5:");

			switch (opcao)
			{
			case 1:
			{
				nome = Console.readLine('\n' + "Informe o nome do produto: ");
				descricao = Console.readLine("Informe a descricao do produto: ");
				lanceMinimo = Console.readDouble("Informe o valor do lance minimo: ");
				dataCadastro = Console
						.readLine("Informe a data de cadastramento do produto: ");

				umProduto = new Produto(nome, descricao, lanceMinimo, Util
						.strToDate(dataCadastro));

				long numero = produtoAppService.inclui(umProduto);

				System.out.println('\n' + "Produto numero " + numero
						+ " incluido com sucesso!");

				break;
			}

			case 2:
			{
				int resposta = Console
						.readInt('\n' + "Digite o numero do produto que voce deseja alterar: ");

				try
				{
					umProduto = produtoAppService.recuperaUmProduto(resposta);
				}
				catch (AplicacaoException e)
				{
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' + "Número = " + umProduto.getId()
						+ "    Nome = " + umProduto.getNome() + "    Salario = "
						+ umProduto.getDescricao());

				System.out.println('\n' + "O que voce deseja alterar?");
				System.out.println('\n' + "1. Nome");
				System.out.println("2. Descricao");

				int opcaoAlteracao = Console
						.readInt('\n' + "Digite um numero de 1 a 2:");

				switch (opcaoAlteracao)
				{
				case 1:
					String novoNome = Console.readLine("Digite o novo nome: ");
					umProduto.setNome(novoNome);

					produtoAppService.altera(umProduto);

					System.out.println('\n' + "Alteracao de nome efetuada com sucesso!");

					break;
				case 2:
					String novaDescricao = Console.readLine("Digite a nova descricao: ");

					umProduto.setDescricao(novaDescricao);

					produtoAppService.altera(umProduto);

					System.out.println('\n' + "Alteracao de descricao efetuada "
							+ "com sucesso!");

					break;

				default:
					System.out.println('\n' + "Opcao invalida!");
				}

				break;
			}

			case 3:
			{
				int resposta = Console
						.readInt('\n' + "Digite o numero do produto que voce deseja remover: ");

				try
				{
					umProduto = produtoAppService.recuperaUmProduto(resposta);
				}
				catch (AplicacaoException e)
				{
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' + "Número = " + umProduto.getId()
						+ "    Nome = " + umProduto.getNome() + "    Descricao = "
						+ umProduto.getDescricao());

				String resp = Console.readLine('\n' + "Confirma a remocao do produto?");

				if (resp.equals("s"))
				{
					try
					{
						produtoAppService.exclui(umProduto);

						System.out.println('\n' + "Produto removido com sucesso!");
					}
					catch (AplicacaoException e)
					{
						System.out.println('\n' + e.getMessage());
					}
				}
				else
				{
					System.out.println('\n' + "Produto nao removido.");
				}

				break;
			}

			case 4:
			{
				try
				/* ==> */{
					HibernateUtil.beginTransaction();

					ProdutoDAO produtoDAO = new ProdutoDAO();

					/*
					 * Ocasionalmente pode-se obter um melhor desempenho executando uma
					 * busca através do método iterate(). Este melhor desempenho só
					 * acontecerá se você espera que as instâncias retornadas já vão estar
					 * no segundo nível de cache. Se estes objetos não estiverem no
					 * segundo nível de cache, iterate() terá um desempenho pior do que
					 * list() e poderá requerer muitos acessos a banco de dados para uma
					 * simples busca: geralmente um acesso para o select inicial que
					 * retorna apenas identificadores, e n selects adicionais para
					 * inicializar as instâncias.
					 */

					for (Iterator iter = produtoDAO.recuperaProdutosComIterate(); iter.hasNext();)
					{
						Produto produto = (Produto) iter.next();

						/* ==> */System.out.println('\n' + "************>>> "
								+ produto.getClass().getName() + '\n');

						System.out.println('\n' + "Produto numero = " + produto.getId()
								+ "  Nome = " + produto.getNome() + "  Descricao = "
								+ produto.getDescricao() + "  Lance minimo = "
								+ produto.getLanceMinimoMasc());
					}

					HibernateUtil.commitTransaction();
				}
				catch (InfraestruturaException e)
				{
					try
					{
						HibernateUtil.rollbackTransaction();
					}
					catch (InfraestruturaException ie)
					{
					}

					throw e;
				}
				finally
				{
					try
					/* ==> */{
						HibernateUtil.closeSession();
						// Comentar esta linha e rodar novamente
					}
					catch (InfraestruturaException he)
					{
						throw he;
					}
				}

				/*
				 * A T E N Ç Ã O
				 * 
				 * Após executar o case 4, comente a linha acima e execute novamente.
				 * Como o iterate() retorna apenas as chaves primárias dos produtos no
				 * Object[], quando os produtos são requisitados (a cada
				 * System.out.println) novas buscas são executadas se os produtos não
				 * estiverem no cache.
				 * 
				 * Se comentarmos a linha acima a sessão não será fechada, e como ela
				 * representa o primeiro nível de cache, ao re-executarmos o case 4 os
				 * dados serão recuperados do cache.
				 */

				break;
			}

			case 5:
			{
				continua = false;
				break;
			}

			default:
				System.out.println('\n' + "Opção inválida!");
			}
		}
	}
}
