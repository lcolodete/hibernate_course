package exercicio02;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.LockMode;
import java.util.List;

public class ClienteDAO
{	
	public long inclui(Cliente umCliente) 
	{	Session sessao = null;
		Transaction tx = null;
		
		try
		{	// transiente - objeto novo: ainda não persistente
			// persistente - apos salvar 
			// destacado - obj persistente fora de uma sessao
		
//==>
			sessao = FabricaDeSessoes.criarSessao();
			tx = sessao.beginTransaction();
			//do something
			
			sessao.save(umCliente);
						
			tx.commit();
			
			//aqui já tenho o número do cliente
			
			return umCliente.getNumero();
		
		} 
		catch(HibernateException e)
		{   if (tx != null)
		    {   try
				{
//==>
		    		tx.rollback();
		        }
		        catch(HibernateException he)
		        { }
		    }
		    throw e;
		}
		finally
		{   try
			{
//==>
				sessao.close();
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
		{	
//==>
			sessao = FabricaDeSessoes.criarSessao();
			tx = sessao.beginTransaction();
			
			// do some work
			
			//ATENCAO: Essa não é a melhor implementação!!!!!!!!!!
			
			Cliente c = (Cliente) sessao.load(Cliente.class, 
											  umCliente.getNumero(),
											  LockMode.READ); // vai agora no banco buscar os dados; não cria o proxy 

			// tira o objeto c da sessao para evitar conflito com o umCliente
			sessao.evict(c);
			
			// ate este momento umCliente é um objeto destacado
			sessao.update(umCliente);
			// nesse momento passa a ser um objeto persistente
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
//==>
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

	public boolean exclui(long numero) 
	{	Session sessao = null;
		Transaction tx = null;
		
		try
		{	
//==>
			sessao = FabricaDeSessoes.criarSessao();
			tx = sessao.beginTransaction();
			
			//do the job
			
			Cliente umCliente = (Cliente) sessao.load(Cliente.class, numero); // cria o proxy
			
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
			
//==>
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

	public void exclui(Cliente umCliente) 
	{	Session sessao = null;
		Transaction tx = null;
		
		try
		{
//==>
			sessao = FabricaDeSessoes.criarSessao();
			tx = sessao.beginTransaction();
			
			sessao.delete(umCliente);
			
			tx.commit();

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
		{	
//==>
			sessao = FabricaDeSessoes.criarSessao();
			tx = sessao.beginTransaction();
			
			Cliente umCliente = (Cliente) sessao.get(Cliente.class, numero);
			
			tx.commit();
			
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
		{	
//==>		
			sessao = FabricaDeSessoes.criarSessao();
			tx = sessao.beginTransaction();
			
			// do the job
			
			List clientes = sessao.createQuery("FROM Cliente ORDER BY NUMERO").list();
			
			tx.commit();
			
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