package exercicio08;

import corejava.Console;
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

			sessao.update(umCliente);

			tx.commit();
			return true;
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

	public boolean atualizaSalario(long numero, double percentualDeAumento)
	{
		Session sessao = null;
		Transaction tx = null;
		
		try
		{
			// Abre a sessao
			sessao = FabricaDeSessoes.criarSessao();
			
			// Abre a transacao
			tx = sessao.beginTransaction();

//==>
			Cliente umCliente = (Cliente) sessao.load(Cliente.class, 
													  numero, 
													  LockMode.UPGRADE); // nesse momento, o registro correspondente a esse cliente está bloqueado

			System.out.println('\n' + "Nome da classe = " + umCliente.getClass().getName()); 
			// Ao utilizarmos o LockMode de UPGRADE o método load() 
			// deixa de retornar um proxy e passa a retornar um 
			// objeto do tipo Cliente. 
	
			Console.readLine("Aperte uma tecla para prosseguir...");

//==>
			umCliente.setSalario(umCliente.getSalario() * (1 + percentualDeAumento/100) );

			tx.commit(); // Aqui o lock é liberado!

			return true;
		} 
		catch(ObjectNotFoundException e)
		{   if (tx != null)
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
		    {	sessao.close();
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

			Cliente umCliente = (Cliente)sessao.load(Cliente.class, 
													new Long(numero));

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
		    {	sessao.close();
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

			Cliente umCliente = (Cliente)sessao.get(Cliente.class, 
													new Long(numero));

			tx.commit();
		    
			return umCliente; // Se não encontrar retorna null
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

	public List recuperaClientes()
	{	Session sessao = null;
		Transaction tx = null;
		
		try
		{	sessao = FabricaDeSessoes.criarSessao();
			tx = sessao.beginTransaction();

			List clientes = sessao.
				createQuery("from Cliente order by numero").list();

			tx.commit();
		    
			return clientes; // Se não encontrar retorna um List vazio
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
}