package exercicio31.lance;

import org.hibernate.Session;
import org.hibernate.HibernateException;
import java.util.List;
import exercicio31.util.*;

public class LanceDAO
{	
	public IdLance inclui(Lance umLance) 
	{	try
		{	Session sessao = HibernateUtil.getSession();

			sessao.save(umLance);

			return umLance.getId();
		} 
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public void altera(Lance umLance) 
	{	try
		{	Session sessao = HibernateUtil.getSession();

			sessao.update(umLance);
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

	public Lance recuperaUmLance(long idProduto, long numeroLance) 
		throws ObjetoNaoEncontradoException
	{	try
		{	Session sessao = HibernateUtil.getSession();

/* ==> */	Lance umLance = (Lance)sessao
				.get(Lance.class, new IdLance(idProduto, numeroLance));
			
			if (umLance == null)
			{	throw new ObjetoNaoEncontradoException();
			}

			return umLance;
		} 
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public Lance recuperaUmLanceComProduto(long idProduto, long numeroLance) 
		throws ObjetoNaoEncontradoException
	{	try
		{	Session sessao = HibernateUtil.getSession();

/* ==> */	String busca = "from Lance l left outer join fetch l.produto where l.id = :id";

			Lance umLance =
				(Lance) sessao.createQuery(busca)
/* ==> */					  .setParameter("id", new IdLance(idProduto, numeroLance))
							  .uniqueResult();

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

			List lances = sessao
				.createQuery("from Lance order by id").list();
			

			return lances;
		} 
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}
}
