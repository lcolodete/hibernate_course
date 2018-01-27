package exercicio18a.produto;

import org.hibernate.Session;
import org.hibernate.HibernateException;
import java.util.List;
import java.util.Set;
import java.util.LinkedHashSet;

import exercicio18a.util.*;
import exercicio18a.lance.*;

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
			               "order by l.id asc" ;

			Produto umProduto =
				(Produto) sessao.createQuery(busca)
							    .setLong("id", numero)
							    .uniqueResult();

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
				             "order by p.id asc, l.id asc")
				.list();

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

	public Lance recuperaUltimoLance(Produto produto)
	{	
		try
		{	HibernateUtil.beginTransaction();
			Session sessao = HibernateUtil.getSession();

//			String busca = " FROM Lance lance " +
//				           " inner join fetch lance.produto produto " +
//                           " WHERE produto.id = :id " +
//                           " AND lance.valor = (SELECT max (l.valor) " +
//                           						" FROM Lance l " +
//                           						" WHERE l.produto.id = :id )";
//
// RETORNA uma lista de lances, cada um com o produto inicializado
			
//			String busca = " FROM Lance lance " +
//	           " inner join lance.produto produto " +
//            " WHERE produto.id = :id " +
//            " AND lance.valor = (SELECT max (l.valor) " +
//            						" FROM Lance l " +
//            						" WHERE l.produto.id = :id )";
//
// RETORNA retirando o fetch, retorna uma lista de array de objetos

			String busca =  " select lance " + 
							" FROM Lance lance " +
							" inner join lance.produto produto " +
							" WHERE produto.id = :id " +
							" AND lance.valor = (SELECT max (l.valor) " +
												" FROM Lance l " +
												" WHERE l.produto.id = :id )";
			// RETORNA apenas o lance, sem o produto inicializado, o retorno
			// sendo uma lista apenas com lances
			
//			String busca = " FROM Lance lance " +
//            " WHERE lance.produto.id = :id " +
//            " AND lance.valor = (SELECT max (l.valor) " +
//            						" FROM Lance l " +
//            						" WHERE l.produto.id = :id )";
//
// RETORNA apenas o lance, sem o produto inicializado

			Lance umLance = (Lance)sessao
				.createQuery(busca)
				.setLong("id", produto.getId())
				.uniqueResult();

			HibernateUtil.commitTransaction();
			
			return umLance;
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