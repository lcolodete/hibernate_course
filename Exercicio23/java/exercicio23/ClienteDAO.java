package exercicio23;

import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.LockMode;

import exercicio23.ObjetoNaoEncontradoException;

import java.util.List;

public class ClienteDAO
{	
	public long inclui(Cliente umCliente) 
	{	try
		{	Session sessao = HibernateUtil.getSession();

			sessao.save(umCliente);
			return umCliente.getNumero();
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

	public Cliente recuperaUmCliente(long numero) 
		throws ObjetoNaoEncontradoException	
	{	try
		{	Session sessao = HibernateUtil.getSession();

			Cliente umCliente = (Cliente)sessao.
				get(Cliente.class, new Long(numero));
				
			if (umCliente == null)
			{	throw new ObjetoNaoEncontradoException();
			}
			
			return umCliente;
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public Cliente recuperaUmClienteComLock(long numero) 
		throws ObjetoNaoEncontradoException	
	{	try
		{	Session sessao = HibernateUtil.getSession();

			Cliente umCliente = (Cliente)sessao
				.load(Cliente.class, 
					  new Long(numero), 
					  LockMode.UPGRADE);

			return umCliente;
		}
		catch(ObjectNotFoundException e)
		{	throw new ObjetoNaoEncontradoException();
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public List recuperaClientes()
	{	try
		{	Session sessao = HibernateUtil.getSession();

			return sessao
				.createQuery("from Cliente order by numero")
			    .list();
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}
}