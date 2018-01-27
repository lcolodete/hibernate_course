package exercicio44.cliente;

import org.hibernate.Session;
import org.hibernate.HibernateException;
import java.util.List;
import java.util.Set;
import java.util.LinkedHashSet;

import exercicio44.util.*;

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

			Cliente umCliente = (Cliente)sessao.get(Cliente.class, 
			                                        new Long(numero));
			
			if (umCliente == null)
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

			return sessao.createQuery("from Cliente order by numero").list();
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}
	
	public Cliente recuperaUmClienteELances(long numero) 
		throws ObjetoNaoEncontradoException
	{	try
		{	Session sessao = HibernateUtil.getSession();

			String busca = "from Cliente c left outer join fetch c.lances where c.id = :id";

			Cliente umCliente =
				(Cliente) sessao.createQuery(busca)
							    .setLong("id", numero)
							    .uniqueResult();

			if (umCliente == null)
			{	throw new ObjetoNaoEncontradoException();
			}

			return umCliente;
		} 
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public Set recuperaClientesELances()
	{	try
		{	Session sessao = HibernateUtil.getSession();

			List clientes = sessao
				.createQuery("from Cliente c left outer join fetch c.lances l order by c.id, l.id")
				.list();

			return new LinkedHashSet(clientes);
		} 
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}
}