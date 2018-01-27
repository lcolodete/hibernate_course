package exercicio22.produto;

import org.hibernate.HibernateException;
import java.util.List;
import java.util.Set;
import exercicio22.util.*;
import exercicio22.lance.*;
import exercicio22.categoria.*;
import exercicio22.prodcat.*;

public class ProdutoAppService
{
	private static ProdutoDAO produtoDAO = new ProdutoDAO();

	private static CategoriaDAO categoriaDAO = new CategoriaDAO();

	private static ProdutoCategoriaDAO produtoCategoriaDAO = new ProdutoCategoriaDAO();

	public long inclui(Produto umProduto, long idCategoria)
			throws AplicacaoException
	{
		try
		{
			HibernateUtil.beginTransaction();

			try
			{
				categoriaDAO.recuperaUmaCategoria(idCategoria);
			}
			catch (ObjetoNaoEncontradoException e)
			{
				throw new AplicacaoException("Categoria nao encontrada.");
			}

			long idProduto = produtoDAO.inclui(umProduto);

			// Cria o objeto produtoCategoria
			/* ==> */ProdutoCategoria produtoCategoria = new ProdutoCategoria(
					idProduto, idCategoria);
			// Salva o objeto produtoCategoria
			/* ==> */produtoCategoriaDAO.inclui(produtoCategoria);

			HibernateUtil.commitTransaction();

			return idProduto;
		}
		catch (AplicacaoException e)
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
			{
				HibernateUtil.closeSession();
			}
			catch (InfraestruturaException he)
			{
				throw he;
			}
		}
	}

	public void altera(Produto umProduto)
	{
		try
		{
			HibernateUtil.beginTransaction();

			produtoDAO.altera(umProduto);

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
			{
				HibernateUtil.closeSession();
			}
			catch (InfraestruturaException he)
			{
				throw he;
			}
		}
	}

	public void exclui(Produto umProduto) throws AplicacaoException
	{
		try
		{
			HibernateUtil.beginTransaction();

			Produto produto = produtoDAO.recuperaUmProdutoELances(umProduto.getId());

			if (produto.getLances().size() > 0)
			{
				throw new AplicacaoException(
						"Este produto possui lances e nao pode ser removido");
			}

			produtoDAO.exclui(produto);

			HibernateUtil.commitTransaction();
		}
		catch (AplicacaoException e)
		{
			try
			{
				HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{
			}
			throw e;
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
			{
				HibernateUtil.closeSession();
			}
			catch (HibernateException he)
			{
				throw he;
			}
		}
	}

	public Produto recuperaUmProduto(long numero) throws AplicacaoException
	{
		try
		{
			HibernateUtil.beginTransaction();

			Produto umProduto = produtoDAO.recuperaUmProduto(numero);

			HibernateUtil.commitTransaction();

			return umProduto;
		}
		catch (ObjetoNaoEncontradoException e)
		{
			try
			{
				HibernateUtil.rollbackTransaction();
			}
			catch (InfraestruturaException ie)
			{
			}

			throw new AplicacaoException("Produto nao encontrado");
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
			{
				HibernateUtil.closeSession();
			}
			catch (InfraestruturaException he)
			{
				throw he;
			}
		}
	}

	public Produto recuperaUmProdutoELances(long numero)
			throws AplicacaoException
	{
		try
		{
			HibernateUtil.beginTransaction();

			Produto umProduto = produtoDAO.recuperaUmProdutoELances(numero);

			HibernateUtil.commitTransaction();

			return umProduto;
		}
		catch (ObjetoNaoEncontradoException e)
		{
			try
			{
				HibernateUtil.rollbackTransaction();
			}
			catch (InfraestruturaException ie)
			{
			}

			throw new AplicacaoException("Produto nao encontrado");
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
			{
				HibernateUtil.closeSession();
			}
			catch (InfraestruturaException he)
			{
				throw he;
			}
		}
	}

	public List recuperaProdutos()
	{
		try
		{
			HibernateUtil.beginTransaction();

			List produtos = produtoDAO.recuperaProdutos();

			HibernateUtil.commitTransaction();

			return produtos;
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
			{
				HibernateUtil.closeSession();
			}
			catch (InfraestruturaException he)
			{
				throw he;
			}
		}
	}

	public Set recuperaProdutosELances()
	{
		try
		{
			HibernateUtil.beginTransaction();

			Set produtos = produtoDAO.recuperaProdutosELances();

			HibernateUtil.commitTransaction();

			return produtos;
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
			{
				HibernateUtil.closeSession();
			}
			catch (InfraestruturaException he)
			{
				throw he;
			}
		}
	}

	public double recuperaValorMaiorLance(Produto produto)
	{
		try
		{
			HibernateUtil.beginTransaction();

			double valor = produtoDAO.recuperaValorMaiorLance(produto);

			HibernateUtil.commitTransaction();

			return valor;
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
			{
				HibernateUtil.closeSession();
			}
			catch (InfraestruturaException he)
			{
				throw he;
			}
		}
	}

	public Lance atribuiLanceVencedorAProduto(long idProduto)
			throws AplicacaoException
	{
		try
		{
			HibernateUtil.beginTransaction();

			Produto produto;
			Lance maiorLance;

			try
			{
				produto = produtoDAO.recuperaUmProdutoComLock(idProduto);
			}
			catch (ObjetoNaoEncontradoException e)
			{
				throw new AplicacaoException("Produto nao encontrado.");
			}

			maiorLance = produtoDAO.recuperaUltimoLance(produto);

			if (maiorLance == null)
			{
				throw new AplicacaoException("Este produto nao possui nenhum lance.");
			}

			produto.setLanceVencedor(maiorLance);

			produto.setDataVenda(maiorLance.getDataCriacao());

			HibernateUtil.commitTransaction();

			return maiorLance;
		}
		catch (AplicacaoException e)
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
			{
				HibernateUtil.closeSession();
			}
			catch (InfraestruturaException he)
			{
				throw he;
			}
		}
	}

	public Lance recuperaLanceVencedorDeProduto(long idProduto)
			throws AplicacaoException
	{
		try
		{
			HibernateUtil.beginTransaction();

			Produto produto;

			try
			{
				produto = produtoDAO.recuperaUmProdutoELanceVencedor(idProduto);
			}
			catch (ObjetoNaoEncontradoException e)
			{
				throw new AplicacaoException("Produto nao encontrado.");
			}

			Lance lanceVencedor = produto.getLanceVencedor();

			if (lanceVencedor == null)
			{
				throw new AplicacaoException(
						"Este produto nao possui um lance vencedor.");
			}

			HibernateUtil.commitTransaction();

			return lanceVencedor;
		}
		catch (AplicacaoException e)
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
			{
				HibernateUtil.closeSession();
			}
			catch (InfraestruturaException he)
			{
				throw he;
			}
		}
	}

	public void atribuiCategoriaAProduto(long idProduto, long idCategoria)
			throws AplicacaoException
	{
		try
		{
			HibernateUtil.beginTransaction();

			try
			{
				produtoDAO.recuperaUmProduto(idProduto);
			}
			catch (ObjetoNaoEncontradoException e)
			{
				throw new AplicacaoException("Produto nao encontrado.");
			}

			try
			{
				categoriaDAO.recuperaUmaCategoria(idCategoria);
			}
			catch (ObjetoNaoEncontradoException e)
			{
				throw new AplicacaoException("Categoria nao encontrada.");
			}

			try
			/* ==> */{
				produtoCategoriaDAO.recuperaUmProdutoCategoria(new IdProdutoCategoria(
						idProduto, idCategoria));

				throw new AplicacaoException("Este produto ja possui esta categoria.");
			}
			catch (ObjetoNaoEncontradoException e)
			{ // Se não encontrar significa que o produto
				// ainda não possui essa categoria.
			}

			ProdutoCategoria produtoCategoria = new ProdutoCategoria(idProduto,
					idCategoria);

			/* ==> */produtoCategoriaDAO.inclui(produtoCategoria);

			HibernateUtil.commitTransaction();
		}
		catch (AplicacaoException e)
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
			{
				HibernateUtil.closeSession();
			}
			catch (InfraestruturaException he)
			{
				throw he;
			}
		}
	}

	public void removeCategoriaDeProduto(long idProduto, long idCategoria)
			throws AplicacaoException
	{
		try
		{
			HibernateUtil.beginTransaction();

			try
			{
				produtoDAO.recuperaUmProduto(idProduto);
			}
			catch (ObjetoNaoEncontradoException e)
			{
				throw new AplicacaoException("Produto nao encontrado.");
			}

			IdProdutoCategoria idProdutoCategoria = new IdProdutoCategoria(idProduto,
																																		 idCategoria);

			try
			/* ==> */{
				produtoCategoriaDAO.recuperaUmProdutoCategoria(idProdutoCategoria);
			}
			catch (ObjetoNaoEncontradoException e)
			{
				throw new AplicacaoException("Este produto nao possui esta categoria.");
			}

			/* ==> */produtoCategoriaDAO.exclui(idProdutoCategoria);

			HibernateUtil.commitTransaction();
		}
		catch (AplicacaoException e)
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
			{
				HibernateUtil.closeSession();
			}
			catch (InfraestruturaException e)
			{
				throw e;
			}
		}
	}

	public Produto recuperaUmProdutoECategorias(long numero)
			throws AplicacaoException
	{
		try
		{
			HibernateUtil.beginTransaction();

			/* ==> */Produto umProduto = produtoDAO
					.recuperaUmProdutoECategorias(numero);

			HibernateUtil.commitTransaction();

			return umProduto;
		}
		catch (ObjetoNaoEncontradoException e)
		{
			try
			{
				HibernateUtil.rollbackTransaction();
			}
			catch (InfraestruturaException ie)
			{
			}

			throw new AplicacaoException("Produto nao encontrado");
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
			{
				HibernateUtil.closeSession();
			}
			catch (InfraestruturaException he)
			{
				throw he;
			}
		}
	}
}