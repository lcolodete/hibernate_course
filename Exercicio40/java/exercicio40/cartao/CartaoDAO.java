package exercicio40.cartao;

import org.hibernate.Session;
import org.hibernate.HibernateException;
import java.util.List;

import exercicio40.util.*;

public class CartaoDAO
{	
	public long inclui(Cartao umCartao) 
	{	try
		{	Session sessao = HibernateUtil.getSession();

			sessao.save(umCartao);

			return umCartao.getId();
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public void altera(Cartao umCartao) 
	{	try
		{	Session sessao = HibernateUtil.getSession();

			sessao.update(umCartao);
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public void exclui(Cartao umCartao) 
	{	try
		{	Session sessao = HibernateUtil.getSession();
		
			sessao.delete(umCartao);
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public Cartao recuperaUmCartao(long id) 
		throws ObjetoNaoEncontradoException
	{	try
		{	Session sessao = HibernateUtil.getSession();

			Cartao umCartao = (Cartao)sessao.get(Cartao.class, new Long(id));
			
			if(umCartao == null)
			{	throw new ObjetoNaoEncontradoException();
			}
			
			return umCartao;
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public List recuperaCartoes()
	{	try
		{	Session sessao = HibernateUtil.getSession();

			return sessao.createQuery("from Cartao order by id").list();
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}
}