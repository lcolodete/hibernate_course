package exercicio20.produto;

import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import java.util.List;
import java.util.Set;
import exercicio20.util.*;
import exercicio20.lance.*;

public class ProdutoAppService
{
	private static ProdutoDAO produtoDAO = new ProdutoDAO();

	public long inclui(Produto umProduto)
	{
		try
		{
			HibernateUtil.beginTransaction();

			long numero = produtoDAO.inclui(umProduto);

			HibernateUtil.commitTransaction();

			return numero;
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

			Session sessao = HibernateUtil.getSession();

			sessao.lock(umProduto, LockMode.UPGRADE);

			sessao.evict(umProduto);

			Produto produto = produtoDAO.recuperaUmProdutoELances(umProduto.getId());

			if (produto.getLances().size() > 0)
			{
				throw new AplicacaoException(
						"Este produto possui lances e nao pode ser removido");
			}

			produtoDAO.exclui(produto);

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
				produto = produtoDAO.recuperaUmProduto(idProduto);
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

			// Designa lance vencedor a produto
			produto.setLanceVencedor(maiorLance);

			// Designa a produto a data de sua venda
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
}