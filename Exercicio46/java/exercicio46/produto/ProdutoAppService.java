package exercicio46.produto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import exercicio46.controleDao.FabricaDeDao;
import exercicio46.controleTransacao.Transacional;
import exercicio46.util.AplicacaoException;
import exercicio46.util.ObjetoNaoEncontradoException;

public class ProdutoAppService
{
	private static ProdutoDAO produtoDAO;

	public ProdutoAppService()
	{ // System.out.println("**************>>>> Executou construtor de
		// ProdutoAppService");
		try
		{
			produtoDAO = FabricaDeDao.getDao(ProdutoDAO.class, Produto.class);

			// O método acima tb poderia ser chamado assim:
			// produtoDAO = FabricaDeDao.<ProdutoDAO>getDao(ProdutoDAO.class,
			// Produto.class);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Transacional
	@SuppressWarnings("unchecked")
	public long inclui(String nome, String descricao, String lanceMinimo,
			String dataCadastro) throws AplicacaoException
	{
		Produto umProduto = new Produto();

		boolean deuErro = false;
		ArrayList mensagens = new ArrayList();

		try
		{
			umProduto.setNome(nome);
		}
		catch (AplicacaoException e)
		{
			mensagens.add(e.getMessage());
			deuErro = true;
		}

		try
		{
			umProduto.setDescricao(descricao);
		}
		catch (AplicacaoException e)
		{
			mensagens.add(e.getMessage());
			deuErro = true;
		}

		try
		{
			umProduto.setLanceMinimo(lanceMinimo);
		}
		catch (AplicacaoException e)
		{
			mensagens.add(e.getMessage());
			deuErro = true;
		}

		try
		{
			umProduto.setDataCadastro(dataCadastro);
		}
		catch (AplicacaoException e)
		{
			mensagens.add(e.getMessage());
			deuErro = true;
		}

		long numero;

		if (!deuErro)
		{
			numero = produtoDAO.inclui(umProduto);
		}
		else
		{
			throw new AplicacaoException(mensagens);
		}

		return numero;
	}

	@Transacional
	public void altera(Produto umProduto)
	{
		produtoDAO.altera(umProduto);
	}

	@Transacional
	public void exclui(Produto umProduto) throws AplicacaoException
	{
		Produto produto = produtoDAO.buscaUmProdutoELances(umProduto.getId());

		if (produto.getLances().size() > 0)
		{
			throw new AplicacaoException(
					"Este produto possui lances e nao pode ser removido");
		}

		produtoDAO.exclui(produto);
	}

	public Produto recuperaUmProduto(long numero) throws AplicacaoException
	{
		try
		{
			Produto umProduto = produtoDAO.getPorId(numero);

			return umProduto;
		}
		catch (ObjetoNaoEncontradoException e)
		{
			throw new AplicacaoException("Produto não encontrado");
		}
	}

	public Produto buscaUmProdutoELances(long numero) throws AplicacaoException
	{
		return produtoDAO.buscaUmProdutoELances(numero);
	}

	public List recuperaProdutos()
	{
		return produtoDAO.recuperaProdutos();
	}

	public Set recuperaProdutosELances()
	{
		return produtoDAO.recuperaConjuntoDeProdutosELances();
	}
}