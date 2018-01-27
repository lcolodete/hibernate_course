package exercicio30;

import org.hibernate.HibernateException;
import java.util.List;

public class ProdutoAppService
{	
	private static ProdutoDAO produtoDAO = new ProdutoDAO();

	public long inclui(Produto umProduto) throws AplicacaoException
	{	try
		{	HibernateUtil.beginTransaction();

			try
//==>			
			{	produtoDAO.recuperaUmProduto(umProduto.getId());

				throw new AplicacaoException ("Produto já cadastrado com este número.");
			}
			catch(ObjetoNaoEncontradoException e)
			{	
			}
			
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
		catch(AplicacaoException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

		    throw e;
		}
		finally
		{    try
		     {  HibernateUtil.closeSession();
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
		{    try
		     {  HibernateUtil.closeSession();
		     }
		     catch(InfraestruturaException he)
		     {	throw he;
		     }
		}
	}

	public void exclui(Produto umProduto) 
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
		{    try
		     {  HibernateUtil.closeSession();
		     }
		     catch(HibernateException he)
		     {	throw he;
		     }
		}
	}

	public Produto recuperaUmProduto(long numero) 
		throws AplicacaoException
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

		    throw new AplicacaoException("Produto nao encontrado");
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
		{    try
		     {  HibernateUtil.closeSession();
		     }
		     catch(InfraestruturaException he)
		     {	throw he;
		     }
		}
	}

	public List recuperaProdutos()
	{	try
		{	HibernateUtil.beginTransaction();

			List produtos = produtoDAO.recuperaProdutos();

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
		{    try
		     {  HibernateUtil.closeSession();
		     }
		     catch(InfraestruturaException he)
		     {	throw he;
		     }
		}
	}
}