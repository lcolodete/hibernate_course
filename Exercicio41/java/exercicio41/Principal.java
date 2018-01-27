package exercicio41;

import corejava.*;
import java.util.Iterator;
import java.util.List;

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
					.println("4. Recuperar produtos pela descricao com HQL - Digite \"samsung\"");
			System.out
					.println("5. Recuperar produtos pela descricao com Criteria (sem MatchMode)");
			System.out
					.println("6. Recuperar produtos pela descricao com Criteria (com MatchMode)");
			System.out
					.println("7. Recuperar produtos pela descricao com o uso de SQL");
			System.out.println("8. Sair");

			int opcao = Console.readInt('\n' + "Digite um numero entre 1 e 8:");

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

				// Recuperar produtos pela descricao (HQL)
			case 4:
			{
				String desc = Console
						.readLine('\n' + "Informe a descricao desejada: (digite samsung) ");

				/* ==> */List produtos = produtoAppService
						.recuperaProdutosPelaDescricaoComHQL(desc);

				if (produtos.size() != 0)
				{
					System.out.println("");
					for (Iterator it = produtos.iterator(); it.hasNext();)
					{
						Produto produto = (Produto) it.next();
						System.out.println('\n' + "Produto numero = " + produto.getId()
								+ "  Nome = " + produto.getNome() + "  Descricao = "
								+ produto.getDescricao());
					}
				}
				else
				{
					System.out
							.println('\n' + "Nao ha produtos cadastrados com esta descricao.");
				}

				break;
			}

				// Recuperar produtos pela descricao com Criteria (sem MatchMode)
			case 5:
			{
				String desc = Console
						.readLine('\n' + "Informe a descricao desejada: (digite samsung) ");

				/* ==> */List produtos = produtoAppService
						.recuperaProdutosPelaDescricaoComCriteria1(desc);

				if (produtos.size() != 0)
				{
					System.out.println("");
					for (Iterator it = produtos.iterator(); it.hasNext();)
					{
						Produto produto = (Produto) it.next();
						System.out.println('\n' + "Produto numero = " + produto.getId()
								+ "  Nome = " + produto.getNome() + "  Descricao = "
								+ produto.getDescricao());
					}
				}
				else
				{
					System.out
							.println('\n' + "Nao ha produtos cadastrados com esta descricao.");
				}

				break;
			}

				// Recuperar produtos pela descricao com Criteria (com MatchMode)
			case 6:
			{
				String desc = Console
						.readLine('\n' + "Informe a descricao desejada: (digite samsung) ");

				/* ==> */List produtos = produtoAppService
						.recuperaProdutosPelaDescricaoComCriteria2(desc);

				if (produtos.size() != 0)
				{
					System.out.println("");
					for (Iterator it = produtos.iterator(); it.hasNext();)
					{
						Produto produto = (Produto) it.next();
						System.out.println('\n' + "Produto numero = " + produto.getId()
								+ "  Nome = " + produto.getNome() + "  Descricao = "
								+ produto.getDescricao());
					}
				}
				else
				{
					System.out
							.println('\n' + "Nao ha produtos cadastrados com esta descricao.");
				}

				break;
			}

				// Recuperar produtos pela descricao com uso de SQL
			case 7:
			{
				String desc = Console
						.readLine('\n' + "Informe a descricao desejada: (digite samsung) ");

				/* ==> */List produtos = produtoAppService
						.recuperaProdutosPelaDescricaoComSQL(desc);

				if (produtos.size() != 0)
				{
					System.out.println("");
					for (Iterator it = produtos.iterator(); it.hasNext();)
					{
						Produto produto = (Produto) it.next();
						System.out.println('\n' + "Produto numero = " + produto.getId()
								+ "  Nome = " + produto.getNome() + "  Descricao = "
								+ produto.getDescricao());
					}
				}
				else
				{
					System.out
							.println('\n' + "Nao ha produtos cadastrados com esta descricao.");
				}

				break;
			}

			case 8:
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
