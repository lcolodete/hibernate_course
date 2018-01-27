package exercicio06;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.StaleObjectStateException;
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
		    {   sessao.close();
		    }
		    catch(HibernateException he)
		    {	throw he;
		    }
		}
	}

	public void altera(Cliente umCliente) 
	{	Session sessao = null;
		Transaction tx = null;
		
		try
		{	sessao = FabricaDeSessoes.criarSessao();
			tx = sessao.beginTransaction();

			sessao.update(umCliente);
			
			/* 
			 * O SEGUINTE COMANDO SQL SERÁ GERADO:
			 * 
			 * update EMPREGADOS
			 * set VERSAO = umCliente.getVersao() + 1,
			 * 	   NOME = umCliente.getNome(), 
			 *     SALARIO = umCliente.getSalario()
			 * where NUMERO = umCliente.getNumero() and 
			 *       VERSAO = umCliente.getVersao()
			 *     
			 */

			tx.commit();
		} 
//==>
		catch ( StaleObjectStateException so )
		{   
			if (tx != null)
		    {
				try
		        {
					tx.rollback();
		        }
		        catch(HibernateException he)
		        {
		        }
		    }
			throw new EstadoDeObjetoObsoletoException();
		}
		catch(HibernateException e)
		{   
			if (tx != null)
		    {
				try
		        {
					tx.rollback();
		        }
		        catch(HibernateException he)
		        { 
		        }
		    }
		    throw e;
		}
		finally
		{   
			try
		    {
				sessao.close();
		    }
		    catch(HibernateException he)
		    {
		    	throw he;
		    }
		}
	}

	public void exclui(Cliente umCliente) 
	{
		Session sessao = null;
		Transaction tx = null;
		
		try
		{
			sessao = FabricaDeSessoes.criarSessao();
			tx = sessao.beginTransaction();

			sessao.delete(umCliente);

		    tx.commit();
		}
//==>
		catch(StaleObjectStateException e)
		{   
			if (tx != null)
		    {   
				try
		        {
					tx.rollback();
		        }
		        catch(HibernateException he)
		        { 
		        }
		    }
			throw new EstadoDeObjetoObsoletoException();
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
		    
			// Se não encontrar retorna null
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

			List clientes = sessao
				.createQuery("from Cliente order by numero")
				.list();

		    tx.commit();
		    
			// Se não encontrar retorna um List vazio
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