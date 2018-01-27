package exercicio03;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import java.util.List;

public class ClienteDAO
{	
	public long inclui(Cliente umCliente) 
	{	Session sessao = null;
		Transaction tx = null;
		
		try
		{	sessao = FabricaDeSessoes.criarSessao();
			tx = sessao.beginTransaction();

			sessao.save(umCliente);

			tx.commit();
			return umCliente.getNumero();
		} 
		catch(HibernateException e)
		{   if (tx != null)
		    {   try
		        {   tx.rollback();
		        }
		        catch(HibernateException he)
		        { }
		    }
		    throw e;
		}
		finally
		{   try
		    {	sessao.close();
		    }
		    catch(HibernateException he)
		    {	throw he;
		    }
		}
	}

	public boolean altera(Cliente umCliente) 
	{	Session sessao = null;
		Transaction tx = null;
		
		try
		{	sessao = FabricaDeSessoes.criarSessao();
			tx = sessao.beginTransaction();

			Cliente c = (Cliente)sessao.load(Cliente.class, 
					                		 umCliente.getNumero(), 
					                		 LockMode.READ);
			sessao.evict(c);
			
			sessao.update(umCliente);

			tx.commit();
			return true;
		} 
		catch(ObjectNotFoundException e)
		{	if (tx != null)
		    {   try
		        {   tx.rollback();
		        }
		        catch(HibernateException he)
		        { }
		    }
			return false;
		}
		catch(HibernateException e)
		{   if (tx != null)
		    {   try
		        {   tx.rollback();
		        }
		        catch(HibernateException he)
		        { }
		    }
			throw e;
		}
		finally
		{   try
		    {  	sessao.close();
		    }
		    catch(HibernateException he)
		    {	throw he;
		    }
		}
	}

	public boolean exclui(long numero) 
	{	Session sessao = null;
		Transaction tx = null;
		
		try
		{	sessao = FabricaDeSessoes.criarSessao();
			tx = sessao.beginTransaction();

			// Neste exemplo, o objeto persistente cliente est� sendo removido
			// do banco de dados. Ap�s a remo��o ele se torna um objeto 
			// transiente e, em seguida, � destru�do pelo coletor de lixo.
			
			Cliente umCliente = (Cliente)sessao
						.load(Cliente.class, new Long(numero));

			// Neste caso o acesso a banco de dados acontece e a exce��o 
			// ObjectNotFoundException � propagada quando o m�todo delete � 
			// executado. Um select � emitido no momento que o delete() �
			// executado pelo fato do delete() referenciar umCliente. 
						
			sessao.delete(umCliente);

			tx.commit();
			return true;
		} 
		catch(ObjectNotFoundException e)
		{	if (tx != null)
		    {   try
		        {   tx.rollback();
		        }
		        catch(HibernateException he)
		        { }
		    }
			return false;
		}
		catch(HibernateException e)
		{   if (tx != null)
		    {   try
		        {   tx.rollback();
		        }
		        catch(HibernateException he)
		        { }
		    }
		    throw e;
		}
		finally
		{   try
		    {   sessao.close();
		    }
		    catch(HibernateException he)
		    {	throw he;
		    }
		}
	}

	public Cliente recuperaUmCliente(long numero) 
	{	Session sessao = null;
		Transaction tx = null;
		
		try
		{	sessao = FabricaDeSessoes.criarSessao();
			tx = sessao.beginTransaction();

			Cliente umCliente = (Cliente)sessao.get(Cliente.class, new Long(numero));

		    tx.commit();
		    
			// Se n�o encontrar retorna null
			return umCliente;
		} 
		catch(HibernateException e)
		{   if (tx != null)
		    {   try
		        {   tx.rollback();
		        }
		        catch(HibernateException he)
		        { }
		    }
			throw e;
		}
		finally
		{   try
		    {   sessao.close();
		    }
		    catch(HibernateException he)
		    {	throw he;
		    }
		}
	}

	public List recuperaClientes()
	{	Session sessao = null;
		Transaction tx = null;
		
		try
		{	sessao = FabricaDeSessoes.criarSessao();
			tx = sessao.beginTransaction();

			List clientes = sessao.
				createQuery("from Cliente order by numero").list();

			tx.commit();
		    
			// Se n�o encontrar retorna um List vazio
			return clientes;
		} 
		catch(HibernateException e)
		{   if (tx != null)
		    {   try
		        {   tx.rollback();
		        }
		        catch(HibernateException he)
		        { }
		    }
			throw e;
		}
		finally
		{   try
		    {   sessao.close();
		    }
		    catch(HibernateException he)
		    {	throw he;
		    }
		}
	}
}