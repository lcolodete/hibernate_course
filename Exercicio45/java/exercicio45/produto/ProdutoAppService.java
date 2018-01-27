package exercicio45.produto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import exercicio45.controleTransacao.Transacional;
import exercicio45.util.AplicacaoException;

public class ProdutoAppService
{
	private static ProdutoDAO produtoDAO = new ProdutoDAO();

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
		Produto produto = produtoDAO.recuperaUmProdutoELances(umProduto.getId());

		if (produto.getLances().size() > 0)
		{
			throw new AplicacaoException(
					"Este produto possui lances e nao pode ser removido");
		}

		produtoDAO.exclui(produto);
	}

	public Produto recuperaUmProduto(long numero) throws AplicacaoException
	{
		return produtoDAO.recuperaUmProduto(numero);
	}

	public Produto recuperaUmProdutoELances(long numero)
			throws AplicacaoException
	{
		return produtoDAO.recuperaUmProdutoELances(numero);
	}

	public List recuperaProdutos()
	{
		return produtoDAO.recuperaProdutos();
	}

	public Set recuperaProdutosELances()
	{
		return produtoDAO.recuperaProdutosELances();
	}
}