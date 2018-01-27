package exercicio18b.produto;

import org.hibernate.HibernateException;
import java.util.Set;
import java.util.ArrayList;

import exercicio18b.util.*;

public class ProdutoAppService
{
	private static ProdutoDAO produtoDAO = new ProdutoDAO();

	@SuppressWarnings("unchecked")
	public long inclui(String nome, String descricao, String lanceMinimo,
			String dataCadastro) throws AplicacaoException
	{
		try
		{
			HibernateUtil.beginTransaction();

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

			HibernateUtil.commitTransaction();

			return numero;
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
}