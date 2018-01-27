package exercicio43;

import corejava.*;
import java.util.List;

public class Principal
{
	public static void main(String[] args)
	{
		Long id;
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
			System.out.println("4. Recuperar id, nome e descrição de produtos");
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

				// Recuperar id, nome e descrição de produtos
			case 4:
			{
				/* ==> */List produtos = produtoAppService
						.recuperaIdNomeEDescricaoDeProdutos();

				if (produtos.isEmpty())
				{
					System.out.println("Não há produtos cadastrados.");
				}
				else
				{
					for (int i = 0; i < produtos.size(); i++)
					{
						Object[] linha = (Object[]) produtos.get(i);
						id = (Long) linha[0];
						nome = (String) linha[1];
						descricao = (String) linha[2];
						System.out.println('\n' + "Produto numero = " + id + "  Nome = "
								+ nome + "  Descricao = " + descricao);
					}
				}

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
