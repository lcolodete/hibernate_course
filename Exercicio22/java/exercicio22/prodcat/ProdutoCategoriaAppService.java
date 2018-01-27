package exercicio22.prodcat;

import org.hibernate.Session;
import org.hibernate.HibernateException;
import java.util.List;
import exercicio22.util.*;

public class ProdutoCategoriaAppService
{	
	private static ProdutoCategoriaDAO produtoCategoriaDAO = new ProdutoCategoriaDAO();

	public IdProdutoCategoria inclui(ProdutoCategoria umProdutoCategoria) 
	{	try
		{	HibernateUtil.beginTransaction();

			IdProdutoCategoria id = produtoCategoriaDAO.inclui(umProdutoCategoria);

			HibernateUtil.commitTransaction();
			
			return id;
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
		    {    HibernateUtil.closeSession();
		    }
		    catch(InfraestruturaException he)
		    {    throw he;
		    }
		}
	}

	public boolean altera(ProdutoCategoria umProdutoCategoria) 
	{	try
		{	HibernateUtil.beginTransaction();

			ProdutoCategoria pc = produtoCategoriaDAO.
				recuperaUmProdutoCategoriaComLock(umProdutoCategoria.getId());

			Session sessao = HibernateUtil.getSession();

			sessao.evict(pc);
			
			produtoCategoriaDAO.altera(umProdutoCategoria);

			HibernateUtil.commitTransaction();
			
			return true;
		} 
		catch(ObjetoNaoEncontradoException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

			return false;
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
			{	HibernateUtil.closeSession();
		    }
		    catch(InfraestruturaException he)
		    {	throw he;
		    }
		}
	}

	public boolean exclui(long idProduto, long idCategoria) 
	{	try
		{	HibernateUtil.beginTransaction();

			IdProdutoCategoria idProdutoCategoria = 
				new IdProdutoCategoria(idProduto, idCategoria);

			@SuppressWarnings("unused")
			ProdutoCategoria umProdutoCategoria = produtoCategoriaDAO
				.recuperaUmProdutoCategoriaComLock(idProdutoCategoria);

			produtoCategoriaDAO.exclui(idProdutoCategoria);

			HibernateUtil.commitTransaction();
			
			return true;
		} 
		catch(ObjetoNaoEncontradoException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

			return false;
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

	public ProdutoCategoria recuperaUmProdutoCategoria(long idProduto, 
	                                                   long idCategoria) 
		throws AplicacaoException
	{	try
		{	HibernateUtil.beginTransaction();

			ProdutoCategoria umProdutoCategoria = produtoCategoriaDAO
				.recuperaUmProdutoCategoriaComLock
						(new IdProdutoCategoria(idProduto, idCategoria));

			HibernateUtil.commitTransaction();
			
			return umProdutoCategoria;
		} 
		catch(ObjetoNaoEncontradoException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }
	
		    throw new AplicacaoException("ProdutoCategoria não encontrado");
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

	public List recuperaProdutoCategorias()
	{	try
		{	HibernateUtil.beginTransaction();

			List produtoCategorias = produtoCategoriaDAO.recuperaProdutoCategorias();

			HibernateUtil.commitTransaction();
			
			return produtoCategorias;
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