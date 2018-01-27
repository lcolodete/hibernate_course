package exercicio34;

import org.hibernate.Session;
import org.hibernate.HibernateException;
import java.util.List;
import java.util.Set;
import java.util.LinkedHashSet;

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

	public Cliente recuperaUmCliente(long numero) 
		throws ObjetoNaoEncontradoException
	{	try
		{	Session sessao = HibernateUtil.getSession();

			Cliente umCliente = (Cliente)sessao
				.get(Cliente.class, new Long(numero));
			
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

			return sessao
				.createQuery("from Cliente order by id")
				.list();
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}
	
	public Cliente recuperaUmClienteETelefones(long id) 
		throws ObjetoNaoEncontradoException
	{	
		try
		{	Session sessao = HibernateUtil.getSession();

			Cliente umCliente = (Cliente)sessao
				.createQuery("from Cliente c " + 
				             "left outer join fetch c.telefones f " + 
				             "where c.id = :id")
			    .setLong("id", id)
			    .uniqueResult();

			if(umCliente == null)
			{	throw new ObjetoNaoEncontradoException();
			}
			
			return umCliente;
		} 
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public Set recuperaClientesETelefones()
	{	
		try
		{	Session sessao = HibernateUtil.getSession();

			List clientes = sessao
				.createQuery("from Cliente c " + 
						     "left outer join fetch c.telefones " +
						     "order by c.id asc")
				.list();

			return new LinkedHashSet(clientes);
		} 
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}
}