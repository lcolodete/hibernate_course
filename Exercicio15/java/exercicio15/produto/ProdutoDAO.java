package exercicio15.produto;

import org.hibernate.Session;
import org.hibernate.HibernateException;
import java.util.List;
import java.util.Set;
import java.util.LinkedHashSet;

import exercicio15.util.*;

public class ProdutoDAO
{	
	public long inclui(Produto umProduto) 
	{	try
		{	HibernateUtil.beginTransaction();
			Session sessao = HibernateUtil.getSession();

			sessao.save(umProduto);
			
			HibernateUtil.commitTransaction();
			return umProduto.getId();
		} 
		catch(HibernateException e)
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
			Session sessao = HibernateUtil.getSession();

			sessao.update(umProduto);
			
			HibernateUtil.commitTransaction();
		}
		catch(HibernateException e)
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

	public void exclui(Produto umProduto) 
	{	try
		{	HibernateUtil.beginTransaction();
			Session sessao = HibernateUtil.getSession();
		
			sessao.delete(umProduto);

			HibernateUtil.commitTransaction();
		}
		catch(HibernateException e)
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

	public Produto recuperaUmProduto(long numero) 
		throws AplicacaoException
	{	try
		{	HibernateUtil.beginTransaction();
			Session sessao = HibernateUtil.getSession();

			Produto umProduto = (Produto)sessao.get(Produto.class, 
												new Long(numero));

			if(umProduto == null)
			{	throw new ObjetoNaoEncontradoException(); 
			}

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
		catch(HibernateException e)
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

	public Produto recuperaUmProdutoELances(long numero) 
		throws AplicacaoException
	{	try
		{	HibernateUtil.beginTransaction();
			Session sessao = HibernateUtil.getSession();

			String busca = "from Produto p left outer join fetch p.lances l " +
			               "where p.id = :id " +
			               "order by p.id, l.id";

			/* Para a ordenação por lance funcionar é preciso que  tenha
			 * especificado para o atributo lances da classe Produto uma
			 * ordenação com o elemento order-by, assim:
			 * 
			 *       <set name="lances"
			 *			  . . .
			 *            order-by="id">
			 *            
			 * Se este  elemento order-by não tiver sido  especificado a
			 * ordenação especificada no HQL não irá funcionar.
			 */

			Produto umProduto =
				(Produto) sessao.createQuery(busca)
							    .setLong("id", numero)
							    .uniqueResult();
							    
			// O método uniqueResult() propaga HibernateException se a query
			// retornar mais de uma linha.

			if(umProduto == null)
			{	throw new ObjetoNaoEncontradoException(); 
			}

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
		catch(HibernateException e)
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

	@SuppressWarnings("unchecked")
	public Set recuperaProdutosELances()
	{	try
		{	HibernateUtil.beginTransaction();
			Session sessao = HibernateUtil.getSession();

			List produtos = sessao
				.createQuery("from Produto p left outer join fetch p.lances l " +
				             "order by p.id, l.id")
				.list();

			/* Para a ordenação por lance funcionar é preciso que  tenha
			 * especificado para o atributo lances da classe Produto uma
			 * ordenação com o elemento order-by, assim:
			 * 
			 *       <set name="lances"
			 *			  . . .
			 *            order-by="id">
			 *            
			 * Se este  elemento order-by não tiver sido  especificado a
			 * ordenação especificada no HQL não irá funcionar.
			 */

			HibernateUtil.commitTransaction();
			return new LinkedHashSet(produtos);
		} 
		catch(HibernateException e)
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