package exercicio21.produto;

import corejava.*;
import exercicio21.util.*;
import exercicio21.lance.*;
import exercicio21.categoria.*;

import java.util.Set;
import java.util.Iterator;

import static java.lang.System.out;

public class PrincipalProduto
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
			out.println('\n' + "O que voce deseja fazer?");
			out.println('\n' + "1. Cadastrar um produto informando sua categoria");
			out.println("2. Alterar um produto");
			out.println("3. Remover um produto (as categorias são removidas em cascata)");
			out.println("4. Listar um produto e seus lances");
			out.println("5. Listar todos os produtos e seus lances");
			out.println("");
			out.println("6. Designar um lance como vencedor para um produto");
			out.println("7. Recuperar o lance vencedor para um produto");
			out.println("");
			out.println("8. Informar nova categoria de um produto");
			out.println("9. Remover uma categoria de um produto");
			out.println("10. Recuperar as categorias de um produto");
			out.println("");
			out.println("11. Sair");

			int opcao = Console.readInt('\n' + "Digite um numero entre 1 e 11:");

			switch (opcao)
			{ // Cadastrar um produto informando sua categoria
			/* ==> */
			case 1:
			{
				nome = Console.readLine('\n' + "Informe o nome do produto: ");
				descricao = Console.readLine("Informe a descricao do produto: ");
				lanceMinimo = Console.readDouble("Informe o valor do lance minimo: ");
				dataCadastro = Console
						.readLine("Informe a data de cadastramento do produto: ");

				long idCategoria = Console.readInt("Informe a categoria do produto: ");

				umProduto = new Produto(nome, descricao, lanceMinimo, Util
						.strToDate(dataCadastro));

				try
				/* ==> */{
					long numero = produtoAppService.inclui(umProduto, idCategoria);

					out.println('\n' + "Produto numero " + numero
							+ " incluido com sucesso!");
				}
				catch (AplicacaoException e)
				{
					out.println('\n' + e.getMessage());
				}

				break;
			}

				// Alterar um produto
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
					out.println('\n' + e.getMessage());
					break;
				}

				out.println('\n' + "Número = " + umProduto.getId() + "    Nome = "
						+ umProduto.getNome() + "    Descricao = "
						+ umProduto.getDescricao());

				out.println('\n' + "O que voce deseja alterar?");
				out.println('\n' + "1. Nome");
				out.println("2. Descricao");

				int opcaoAlteracao = Console
						.readInt('\n' + "Digite um numero de 1 a 2:");

				switch (opcaoAlteracao)
				{
				case 1:
					String novoNome = Console.readLine("Digite o novo nome: ");

					umProduto.setNome(novoNome);

					produtoAppService.altera(umProduto);
					out.println('\n' + "Alteracao de nome efetuada com sucesso!");

					break;

				case 2:
					String novaDescricao = Console.readLine("Digite a nova descricao: ");

					umProduto.setDescricao(novaDescricao);

					produtoAppService.altera(umProduto);
					out.println('\n' + "Alteracao de descricao efetuada "
							+ "com sucesso!");

					break;

				default:
					out.println('\n' + "Opcao invalida!");
				}

				break;
			}

				// Remover um produto (as categorias são removinas em cascata)
				/* ==> */case 3:
			{
				int resposta = Console
						.readInt('\n' + "Digite o numero do produto que voce deseja remover: ");

				try
				{
					umProduto = produtoAppService.recuperaUmProduto(resposta);
				}
				catch (AplicacaoException e)
				{
					out.println('\n' + e.getMessage());
					break;
				}

				out.println('\n' + "Número = " + umProduto.getId() + "    Nome = "
						+ umProduto.getNome() + "    Descricao = "
						+ umProduto.getDescricao());

				String resp = Console.readLine('\n' + "Confirma a remocao do produto?");

				if (resp.equals("s"))
				{
					try
					/* ==> */{ // Observe que as ocorrências em Produto_Categorias
						// referentes a um produto também são removidas. E
						// observe que não é possível remover uma Categoria
						// que possua uma associação com algum produto.

						produtoAppService.exclui(umProduto);
						out.println('\n' + "Produto removido com sucesso!");
					}
					catch (AplicacaoException e)
					{
						out.println('\n' + e.getMessage());
						break;
					}
				}
				else
				{
					out.println('\n' + "Produto nao removido.");
				}

				break;
			}

				// Listar um produto e seus lances
			case 4:
			{
				long numero = Console.readInt("Informe o numero do produto: ");

				try
				{
					umProduto = produtoAppService.recuperaUmProdutoELances(numero);
				}
				catch (AplicacaoException e)
				{
					out.println('\n' + e.getMessage());
					break;
				}

				out.println("Id = " + umProduto.getId() + "  Nome = "
						+ umProduto.getNome() + "  Descricao = " + umProduto.getDescricao()
						+ "  Lance minimo = " + umProduto.getLanceMinimo()
						+ "  Data Cadastro = " + umProduto.getDataCadastroMasc());

				Set lances = umProduto.getLances();

				for (Iterator it = lances.iterator(); it.hasNext();)
				{
					Lance lance = (Lance) it.next();
					out.println('\n' + "      Lance numero = " + lance.getId()
							+ "  valor = " + lance.getValor() + "  Data = "
							+ lance.getDataCriacaoMasc());
				}

				break;
			}

				// Listar todos os produtos e seus lances
			case 5:
			{
				Set produtos = produtoAppService.recuperaProdutosELances();

				if (produtos.size() != 0)
				{
					out.println("");

					for (Iterator it = produtos.iterator(); it.hasNext();)
					{
						Produto produto = (Produto) it.next();
						out.println('\n' + "Produto numero = " + produto.getId()
								+ "  Nome = " + produto.getNome() + "  Descricao = "
								+ produto.getDescricao() + "  Lance minimo = "
								+ produto.getLanceMinimoMasc());

						Set lances = produto.getLances();

						for (Iterator it2 = lances.iterator(); it2.hasNext();)
						{
							Lance lance = (Lance) it2.next();
							out.println('\n' + "      Lance numero = " + lance.getId()
									+ "  valor = " + lance.getValor() + "  Data = "
									+ lance.getDataCriacaoMasc());
						}
					}
				}
				else
				{
					out.println('\n' + "Nao ha produtos cadastrados com esta descricao.");
				}

				break;
			}

				// Designar um lance como vencedor para um produto
			case 6:
			{
				long idProduto = Console
						.readInt('\n' + "Informe o numero do produto: ");

				try
				{
					Lance lanceVencedor = produtoAppService
							.atribuiLanceVencedorAProduto(idProduto);
					out.println('\n' + "Lance vencedor - Id = " + lanceVencedor.getId()
							+ "  Valor = " + lanceVencedor.getValor()
							+ "  Data de emissao = " + lanceVencedor.getDataCriacao()
							+ "  Data da venda = "
							+ lanceVencedor.getProduto().getDataVenda());
				}
				catch (AplicacaoException e)
				{
					out.println('\n' + e.getMessage());
				}

				break;
			}

				// Recuperar o lance vencedor para um produto
			case 7:
			{
				long idProduto = Console
						.readInt('\n' + "Informe o numero do produto: ");

				try
				{
					Lance lanceVencedor = produtoAppService
							.recuperaLanceVencedorDeProduto(idProduto);

					out.println('\n' + "Lance vencedor - Id = " + lanceVencedor.getId()
							+ "  Valor = " + lanceVencedor.getValor()
							+ "  Data de emissao = " + lanceVencedor.getDataCriacao()
							+ "  Data da venda = "
							+ lanceVencedor.getProduto().getDataVenda());
				}
				catch (AplicacaoException e)
				{
					out.println('\n' + e.getMessage());
				}

				break;
			}

				// Informar nova categoria de um produto
				/* ==> */case 8:
			{
				int idProduto = Console.readInt('\n'
						+ "Digite o numero do produto ao qual voce deseja "
						+ "atribuir uma categoria: ");

				try
				{
					umProduto = produtoAppService.recuperaUmProduto(idProduto);
				}
				catch (AplicacaoException e)
				{
					out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' + "Número = " + umProduto.getId()
						+ "    Nome = " + umProduto.getNome() + "    Descricao = "
						+ umProduto.getDescricao());

				long idCategoria = Console.readInt("Informe o numero da categoria: ");

				try
				/* ==> */{
					produtoAppService.atribuiCategoriaAProduto(idProduto, idCategoria);
					out.println('\n' + "Categoria atribuida ao produto com sucesso!");
				}
				catch (AplicacaoException e)
				{
					out.println('\n' + e.getMessage());
				}

				break;
			}

				// Remover uma categoria de um produto
				/* ==> */case 9:
			{
				int idProduto = Console.readInt('\n'
						+ "Digite o numero do produto do qual voce deseja "
						+ "remover uma categoria: ");

				try
				{
					umProduto = produtoAppService.recuperaUmProduto(idProduto);
				}
				catch (AplicacaoException e)
				{
					out.println('\n' + e.getMessage());
					break;
				}

				out.println('\n' + "Número = " + umProduto.getId() + "    Nome = "
						+ umProduto.getNome() + "    Descricao = "
						+ umProduto.getDescricao());

				long idCategoria = Console.readInt("Informe o numero da categoria que "
						+ "voce deseja remover: ");

				try
				{ // A razão de ter sido criado um método que remove
					// uma Categoria de um Produto é o fato da remoção
					// ter que acontecer dentro de uma sessao do
					// Hibernate.

					/* ==> */produtoAppService.removeCategoriaDeProduto(idProduto,
							idCategoria);

					out.println('\n' + "Categoria removida do produto com sucesso!");
				}
				catch (AplicacaoException e)
				{
					out.println('\n' + e.getMessage());
				}

				break;
			}

				// Recuperar as categorias de um produto
				/* ==> */case 10:
			{
				long numero = Console.readInt("Informe o numero do produto: ");

				try
				/* ==> */{
					umProduto = produtoAppService.recuperaUmProdutoECategorias(numero);
				}
				catch (AplicacaoException e)
				{
					out.println('\n' + e.getMessage());
					break;
				}

				out.println('\n' + "Produto numero = " + umProduto.getId()
						+ "  Nome = " + umProduto.getNome() + "  Descricao = "
						+ umProduto.getDescricao() + "  Lance minimo = "
						+ umProduto.getLanceMinimo() + "  Data Cadastro = "
						+ umProduto.getDataCadastroMasc());

				Set categorias = umProduto.getCategorias();

				for (Iterator it = categorias.iterator(); it.hasNext();)
				{
					Categoria categoria = (Categoria) it.next();
					out.println('\n' + "      Categoria numero = " + categoria.getId()
							+ "  Nome = " + categoria.getNome());
				}

				break;
			}

			case 11:
			{
				continua = false;
				break;
			}

			default:
				out.println('\n' + "Opção inválida!");
			}
		}
	}
}
