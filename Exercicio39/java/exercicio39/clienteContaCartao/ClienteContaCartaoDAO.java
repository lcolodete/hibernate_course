package exercicio39.clienteContaCartao;

import org.hibernate.Session;
import org.hibernate.HibernateException;
import java.util.List;
import exercicio39.util.*;

public class ClienteContaCartaoDAO
{	
	public long inclui(ClienteContaCartao umClienteContaCartao) 
	{	try
		{	Session sessao = HibernateUtil.getSession();

			sessao.save(umClienteContaCartao);

			return umClienteContaCartao.getId();
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public void altera(ClienteContaCartao umClienteContaCartao) 
	{	try
		{	Session sessao = HibernateUtil.getSession();

			sessao.update(umClienteContaCartao);
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public void exclui(ClienteContaCartao umClienteContaCartao) 
	{	try
		{	Session sessao = HibernateUtil.getSession();
		
			sessao.delete(umClienteContaCartao);
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public ClienteContaCartao recuperaUmClienteContaCartao(long id) 
		throws ObjetoNaoEncontradoException
	{	try
		{	Session sessao = HibernateUtil.getSession();

			ClienteContaCartao umClienteContaCartao = 
				(ClienteContaCartao)sessao.
					get(ClienteContaCartao.class, new Long(id));
			
			if(umClienteContaCartao == null)
			{	throw new ObjetoNaoEncontradoException();
			}
			
			return umClienteContaCartao;
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public List recuperaClienteContaCartoes()
	{	try
		{	Session sessao = HibernateUtil.getSession();

			return sessao.createQuery("from ClienteContaCartao order by id").list();
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public List recuperaClienteContaCartoes(long idCliente, long idConta)
	{	try
		{	Session sessao = HibernateUtil.getSession();

			return sessao.createQuery("from ClienteContaCartao " +
				                      "where cliente.id = :idCliente and " +
				                      "      conta.id = :idConta")
			    		 .setLong("idCliente", idCliente)
			    		 .setLong("idConta", idConta)
						 .list();
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}
}