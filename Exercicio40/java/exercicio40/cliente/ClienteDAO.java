package exercicio40.cliente;

import org.hibernate.Session;
import org.hibernate.HibernateException;
import java.util.List;

import exercicio40.util.*;

public class ClienteDAO
{	
	public long inclui(Cliente umCliente) 
	{	try
		{	Session sessao = HibernateUtil.getSession();

			sessao.save(umCliente);

			return umCliente.getId();
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public void altera(Cliente umCliente) 
	{	try
		{	Session sessao = HibernateUtil.getSession();

			sessao.update(umCliente);
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public void exclui(Cliente umCliente) 
	{	try
		{	Session sessao = HibernateUtil.getSession();
		
			sessao.delete(umCliente);
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public Cliente recuperaUmCliente(long id) 
		throws ObjetoNaoEncontradoException
	{	try
		{	Session sessao = HibernateUtil.getSession();

			Cliente umCliente = (Cliente)sessao.get(Cliente.class, new Long(id));
			
			if(umCliente == null)
			{	throw new ObjetoNaoEncontradoException();
			}
			
			return umCliente;
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public List recuperaClientes()
	{	try
		{	Session sessao = HibernateUtil.getSession();

			return sessao.createQuery("from Cliente order by id").list();
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}
}