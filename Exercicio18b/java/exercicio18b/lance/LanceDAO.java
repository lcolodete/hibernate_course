package exercicio18b.lance;

import org.hibernate.Session;
import org.hibernate.HibernateException;
import java.util.List;
import exercicio18b.util.*;

public class LanceDAO
{	
	public long inclui(Lance umLance) 
	{	try
		{	Session sessao = HibernateUtil.getSession();

			sessao.save(umLance);
			
			return umLance.getId();
		} 
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public void exclui(Lance umLance) 
	{	try
		{	Session sessao = HibernateUtil.getSession();
		
			sessao.delete(umLance);
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public Lance recuperaUmLance(long numero) 
	{	try
		{	Session sessao = HibernateUtil.getSession();

			Lance umLance = (Lance)sessao
				.get(Lance.class, new Long(numero));
			
			if (umLance == null)
			{	throw new ObjetoNaoEncontradoException();
			}

			return umLance;
		} 
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public List recuperaLances()
	{	try
		{	Session sessao = HibernateUtil.getSession();

			List lances = sessao.createQuery("from Lance order by id")
			                    .list();

			return lances;
		} 
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}
}
