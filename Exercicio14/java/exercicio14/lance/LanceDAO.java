package exercicio14.lance;

import org.hibernate.LockMode;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.HibernateException;
import java.util.List;
import exercicio14.util.*;

public class LanceDAO
{	
	public long inclui(Lance umLance) 
	{	try
		{	HibernateUtil.beginTransaction();
			Session sessao = HibernateUtil.getSession();

			sessao.save(umLance);
			
			HibernateUtil.commitTransaction();
			return umLance.getId();
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

	public void altera(Lance umLance) 
	{	try
		{	HibernateUtil.beginTransaction();
			Session sessao = HibernateUtil.getSession();

			sessao.update(umLance);
			
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

	public void exclui(Lance umLance) 
	{	try
		{	HibernateUtil.beginTransaction();
			Session sessao = HibernateUtil.getSession();
		
			sessao.delete(umLance);

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

	public Lance recuperaUmLance(long numero) 
		throws AplicacaoException
	{	try
		{	HibernateUtil.beginTransaction();
			Session sessao = HibernateUtil.getSession();

			Lance umLance = (Lance)sessao
				.load(Lance.class,
					  new Long(numero),
					  LockMode.READ);
											  
			HibernateUtil.commitTransaction();
			return umLance;
		} 
		catch(ObjectNotFoundException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

			throw new AplicacaoException("Lance nao encontrado");
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

	public List recuperaLances()
	{	
		try
		{
			HibernateUtil.beginTransaction();
			Session sessao = HibernateUtil.getSession();

			List lances = sessao.createQuery("FROM Lance ORDER BY id").list();
			
			HibernateUtil.commitTransaction();
			return lances;
		} 
		catch(HibernateException e)
		{	
			try
			{
				HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{
			}

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
