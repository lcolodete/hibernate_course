package exercicio41;

import org.hibernate.HibernateException;
import java.util.List;

public class ProdutoAppService
{	
	private static ProdutoDAO produtoDAO = new ProdutoDAO();

	public long inclui(Produto umProduto) 
	{	try
		{	HibernateUtil.beginTransaction();

			long numero = produtoDAO.inclui(umProduto);

			HibernateUtil.commitTransaction();
			
			return numero;
		} 
		catch(InfraestruturaException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

		    throw e;
		}
		finally
		{   try
		    {   HibernateUtil.closeSession();
		    }
		    catch(InfraestruturaException he)
		    {	throw he;
		    }
		}
	}

	public void altera(Produto umProduto) 
	{	try
		{	HibernateUtil.beginTransaction();

			produtoDAO.altera(umProduto);

			HibernateUtil.commitTransaction();
		} 
		catch(InfraestruturaException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

		    throw e;
		}
		finally
		{   try
		    {   HibernateUtil.closeSession();
		    }
		    catch(InfraestruturaException he)
		    {	throw he;
		    }
		}
	}

	public void exclui(Produto umProduto) throws AplicacaoException
	{	try
		{	HibernateUtil.beginTransaction();

			produtoDAO.exclui(umProduto);

			HibernateUtil.commitTransaction();
		} 
		catch(InfraestruturaException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

		    throw e;
		}
		finally
		{   try
		    {   HibernateUtil.closeSession();
		    }
		    catch(HibernateException he)
		    {	throw he;
		    }
		}
	}

	public Produto recuperaUmProduto(long numero) throws AplicacaoException
	{	try
		{	HibernateUtil.beginTransaction();

			Produto umProduto = produtoDAO.recuperaUmProduto(numero);

			HibernateUtil.commitTransaction();
			
			return umProduto;
		} 
		catch(ObjetoNaoEncontradoException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

		    throw new AplicacaoException("Produto nao encontrado.");
		}
		catch(InfraestruturaException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

		    throw e;
		}
		finally
		{   try
		    {   HibernateUtil.closeSession();
		    }
		    catch(InfraestruturaException he)
		    {	throw he;
		    }
		}
	}

	public List recuperaProdutosPelaDescricaoComHQL(String descricao)
	{	try
		{	HibernateUtil.beginTransaction();

			List produtos = produtoDAO.recuperaProdutosPelaDescricaoComHQL(descricao);

			HibernateUtil.commitTransaction();
			
			return produtos;
		} 
		catch(InfraestruturaException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

		    throw e;
		}
		finally
		{   try
		    {   HibernateUtil.closeSession();
		    }
		    catch(InfraestruturaException he)
		    {	throw he;
		    }
		}
	}

	public List recuperaProdutosPelaDescricaoComCriteria1(String descricao)
	{	try
		{	HibernateUtil.beginTransaction();

			List produtos = produtoDAO.recuperaProdutosPelaDescricaoComCriteria1(descricao);

			HibernateUtil.commitTransaction();
			
			return produtos;
		} 
		catch(InfraestruturaException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

		    throw e;
		}
		finally
		{   try
		    {   HibernateUtil.closeSession();
		    }
		    catch(InfraestruturaException he)
		    {	throw he;
		    }
		}
	}

	public List recuperaProdutosPelaDescricaoComCriteria2(String descricao)
	{	try
		{	HibernateUtil.beginTransaction();

			List produtos = produtoDAO.recuperaProdutosPelaDescricaoComCriteria2(descricao);

			HibernateUtil.commitTransaction();
			
			return produtos;
		} 
		catch(InfraestruturaException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

		    throw e;
		}
		finally
		{   try
		    {   HibernateUtil.closeSession();
		    }
		    catch(InfraestruturaException he)
		    {	throw he;
		    }
		}
	}

	public List recuperaProdutosPelaDescricaoComSQL(String descricao)
	{	try
		{	HibernateUtil.beginTransaction();

			List produtos = produtoDAO.recuperaProdutosPelaDescricaoComSQL(descricao);

			HibernateUtil.commitTransaction();
			
			return produtos;
		} 
		catch(InfraestruturaException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

		    throw e;
		}
		finally
		{   try
		    {   HibernateUtil.closeSession();
		    }
		    catch(InfraestruturaException he)
		    {	throw he;
		    }
		}
	}
}