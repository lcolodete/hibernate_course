package exercicio40.conta;

import org.hibernate.Session;
import org.hibernate.HibernateException;
import java.util.List;

import exercicio40.util.*;

public class ContaDAO
{	
	public long inclui(Conta umConta) 
	{	try
		{	Session sessao = HibernateUtil.getSession();

			sessao.save(umConta);

			return umConta.getId();
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public void altera(Conta umaConta) 
	{	try
		{	Session sessao = HibernateUtil.getSession();

			sessao.update(umaConta);
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public void exclui(Conta umaConta) 
	{	try
		{	Session sessao = HibernateUtil.getSession();
		
			sessao.delete(umaConta);
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public Conta recuperaUmaConta(long id) 
		throws ObjetoNaoEncontradoException
	{	try
		{	Session sessao = HibernateUtil.getSession();

			Conta umConta = (Conta)sessao.get(Conta.class, new Long(id));
			
			if(umConta == null)
			{	throw new ObjetoNaoEncontradoException();
			}
			
			return umConta;
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public List recuperaContas()
	{	try
		{	Session sessao = HibernateUtil.getSession();

			return sessao.createQuery("from Conta order by id").list();
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}
}