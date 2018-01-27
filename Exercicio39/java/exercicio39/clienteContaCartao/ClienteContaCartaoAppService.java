package exercicio39.clienteContaCartao;

import org.hibernate.HibernateException;
import java.util.List;
import exercicio39.util.*;

public class ClienteContaCartaoAppService
{	
	private static ClienteContaCartaoDAO clienteContaCartaoDAO = new ClienteContaCartaoDAO();

	public long inclui(ClienteContaCartao umClienteContaCartao) 
	{	try
		{	HibernateUtil.beginTransaction();

			long id = clienteContaCartaoDAO.inclui(umClienteContaCartao);

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

	public void altera(ClienteContaCartao umClienteContaCartao) 
	{	try
		{	HibernateUtil.beginTransaction();
			
			clienteContaCartaoDAO.altera(umClienteContaCartao);

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

	public void exclui(ClienteContaCartao umClienteContaCartao) throws AplicacaoException
	{	try
		{	HibernateUtil.beginTransaction();

			clienteContaCartaoDAO.exclui(umClienteContaCartao);

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
		    catch(HibernateException he)
		    {	throw he;
		    }
		}
	}

	public ClienteContaCartao recuperaUmClienteContaCartao(long id) throws AplicacaoException
	{	try
		{	HibernateUtil.beginTransaction();

			ClienteContaCartao umClienteContaCartao = clienteContaCartaoDAO.recuperaUmClienteContaCartao(id);

			HibernateUtil.commitTransaction();
			
			return umClienteContaCartao;
		} 
		catch(ObjetoNaoEncontradoException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

		    throw new AplicacaoException("ClienteContaCartao nao encontrado.");
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

	public List recuperaClienteContaCartoes()
	{	try
		{	HibernateUtil.beginTransaction();

			List clienteContaCartaos = clienteContaCartaoDAO.recuperaClienteContaCartoes();

			HibernateUtil.commitTransaction();
			
			return clienteContaCartaos;
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

	public List recuperaClienteContaCartoes(long idCliente, long idCartao)
	{	try
		{	HibernateUtil.beginTransaction();

			List clienteContaCartaos = clienteContaCartaoDAO.recuperaClienteContaCartoes(idCliente, idCartao);

			HibernateUtil.commitTransaction();
			
			return clienteContaCartaos;
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