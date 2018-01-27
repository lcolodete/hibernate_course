package exercicio44.cliente;

import org.hibernate.HibernateException;
import java.util.List;
import java.util.Set;

import exercicio44.util.*;

public class ClienteAppService
{	
	private static ClienteDAO clienteDAO = new ClienteDAO();

	public long inclui(Cliente umCliente) 
	{	try
		{	HibernateUtil.beginTransaction();

			long numero = clienteDAO.inclui(umCliente);

			HibernateUtil.commitTransaction();
			
			return numero;
		} 
		catch(InfraestruturaException e)
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

	public void altera(Cliente umCliente) 
	{	try
		{	HibernateUtil.beginTransaction();

			clienteDAO.altera(umCliente);

			HibernateUtil.commitTransaction();
		} 
		catch(InfraestruturaException e)
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

	public void exclui(Cliente umCliente) throws AplicacaoException
	{	try
		{	HibernateUtil.beginTransaction();

			Cliente cliente = clienteDAO.recuperaUmClienteELances(umCliente.getNumero());

			if(cliente.getLances().size() > 0)
			{	throw new AplicacaoException("Este cliente possui lances e nao pode ser removido");
			}

			clienteDAO.exclui(cliente);

			HibernateUtil.commitTransaction();
		} 
		catch(AplicacaoException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

		    throw e;
		}
		catch(InfraestruturaException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

		    throw e;
		}
		finally
		{    try
		     {  HibernateUtil.closeSession();
		     }
		     catch(HibernateException he)
		     {	throw he;
		     }
		}
	}

	public Cliente recuperaUmCliente(long numero) throws AplicacaoException
	{	try
		{	HibernateUtil.beginTransaction();

			Cliente umCliente = clienteDAO.recuperaUmCliente(numero);

			HibernateUtil.commitTransaction();
			
			return umCliente;
		} 
		catch(ObjetoNaoEncontradoException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

		    throw new AplicacaoException("Cliente nao encontrado.");
		}
		catch(InfraestruturaException e)
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

	public List recuperaClientes()
	{	try
		{	HibernateUtil.beginTransaction();

			List clientes = clienteDAO.recuperaClientes();

			HibernateUtil.commitTransaction();
			
			return clientes;
		} 
		catch(InfraestruturaException e)
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

	public Set recuperaClientesELances()
	{	try
		{	HibernateUtil.beginTransaction();

			Set clientes = clienteDAO.recuperaClientesELances();

			HibernateUtil.commitTransaction();
			
			return clientes;
		} 
		catch(InfraestruturaException e)
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
}