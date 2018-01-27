package exercicio39.cliente;

import org.hibernate.HibernateException;
import java.util.List;
import exercicio39.util.*;

public class ClienteAppService
{	
	private static ClienteDAO clienteDAO = new ClienteDAO();

	public long inclui(Cliente umCliente) 
	{	try
		{	HibernateUtil.beginTransaction();

			long id = clienteDAO.inclui(umCliente);

			HibernateUtil.commitTransaction();
			
			return id;
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

			umCliente = clienteDAO.recuperaUmCliente(umCliente.getId());

/* ==> */	if(umCliente.getCccs().size() > 0)
			{	throw new AplicacaoException("Este Cliente possui " +
				                             "conta(s) e cartao(oes) " +
				                             "e nao pode ser removido");
			}

			clienteDAO.exclui(umCliente);

			HibernateUtil.commitTransaction();
		} 
		catch(ObjetoNaoEncontradoException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

			throw new AplicacaoException("Cliente nao encontrado.");
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
		{   try
		    {   HibernateUtil.closeSession();
		    }
		    catch(HibernateException he)
		    {	throw he;
		    }
		}
	}

	public Cliente recuperaUmCliente(long id) throws AplicacaoException
	{	try
		{	HibernateUtil.beginTransaction();

			Cliente umCliente = clienteDAO.recuperaUmCliente(id);

			HibernateUtil.commitTransaction();
			
			return umCliente;
		} 
		catch(ObjetoNaoEncontradoException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

		    throw new AplicacaoException(1, "Cliente nao encontrado.");
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
}