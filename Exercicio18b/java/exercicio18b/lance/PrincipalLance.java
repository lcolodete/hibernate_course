package exercicio18b.lance;

import corejava.*;
import exercicio18b.util.*;
import exercicio18b.produto.*;

import java.util.List;

public class PrincipalLance
{
	public static void main(String[] args)
	{
		String valor;
		String dataCriacao;

		Produto umProduto;
		Lance umLance;

		ProdutoAppService produtoAppService = new ProdutoAppService();
		LanceAppService lanceAppService = new LanceAppService();

		boolean continua = true;
		while (continua)
		{
			System.out.println('\n' + "O que voc� deseja fazer?");
			System.out.println('\n' + "1. Cadastrar um lance de um produto");
			System.out.println("2. Remover um lance");
			System.out.println("3. Recuperar todos os lances");
			System.out.println("4. Sair");

			int opcao = Console.readInt('\n' + "Digite um n�mero entre 1 e 4:");

			switch (opcao)
			{
			case 1:
			{
				long idProduto = Console
						.readInt('\n' + "Informe o n�mero do produto: ");

				try
				{
					umProduto = produtoAppService.recuperaUmProduto(idProduto);
				}
				catch (AplicacaoException e)
				{
					System.out.println('\n' + e.getMessage());
					break;
				}

				valor = Console.readLine('\n' + "Informe o valor do lance: ");
				dataCriacao = Console.readLine("Informe a data de emiss�o do lance: ");

				try
				{
					lanceAppService.inclui(valor, dataCriacao, umProduto);

					System.out.println('\n' + "Lance adicionado com sucesso");
				}
				catch (AplicacaoException e)
				{
					System.out.println("");

					for (int i = 0; i < e.getMensagens().size(); i++)
					{
						System.out.println(e.getMensagens().get(i));
					}
				}

				break;
			}

			case 2:
			{
				int resposta = Console
						.readInt('\n' + "Digite o n�mero do lance que voc� deseja remover: ");

				try
				{
					umLance = lanceAppService.recuperaUmLance(resposta);
				}
				catch (AplicacaoException e)
				{
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' + "N�mero = " + umLance.getId()
						+ "    Valor = " + umLance.getValor() + "    Data de Emiss�o = "
						+ umLance.getDataCriacao());

				String resp = Console.readLine('\n' + "Confirma a remo��o do lance?");

				if (resp.equals("s"))
				{
					lanceAppService.exclui(umLance);
					System.out.println('\n' + "Lance removido com sucesso!");
				}
				else
				{
					System.out.println('\n' + "Lance n�o removido.");
				}

				break;
			}

			case 3:
			{
				List arrayLances = lanceAppService.recuperaLances();

				if (arrayLances.size() == 0)
				{
					System.out.println('\n' + "Nao h� lances cadastrados.");
					break;
				}

				System.out.println("");
				int i;
				for (i = 0; i < arrayLances.size(); i++)
				{
					umLance = (Lance) arrayLances.get(i);
					System.out.println("N�mero = " + umLance.getId() + "  Valor = "
							+ umLance.getValor() + "  Data de Emiss�o = "
							+ umLance.getDataCriacao());
				}

				break;
			}

			case 4:
			{
				continua = false;
				break;
			}

			default:
				System.out.println('\n' + "Op��o inv�lida!");
			}
		}
	}
}
