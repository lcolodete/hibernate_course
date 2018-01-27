package exercicio40.conta;

import org.hibernate.HibernateException;
import java.util.List;

import exercicio40.util.*;

public class ContaAppService
{	
	private static ContaDAO contaDAO = new ContaDAO();

	public long inclui(Conta umConta) 
	{	try
		{	HibernateUtil.beginTransaction();

			long id = contaDAO.inclui(umConta);

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

	public void altera(Conta umaConta) 
	{	try
		{	HibernateUtil.beginTransaction();
			
			contaDAO.altera(umaConta);

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

	public void exclui(Conta umaConta) throws AplicacaoException
	{	try
		{	HibernateUtil.beginTransaction();

			umaConta = contaDAO.recuperaUmaConta(umaConta.getId());

			if(umaConta.getCccs().size() > 0)
			{	throw new AplicacaoException("Este Conta possui " +
				                             "cliente(s) e cartao(oes) " +
				                             "e nao pode ser removida");
			}

			contaDAO.exclui(umaConta);

			HibernateUtil.commitTransaction();
		} 
		catch(ObjetoNaoEncontradoException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

			throw new AplicacaoException("Conta nao encontrada.");
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

	public Conta recuperaUmaConta(long id) throws AplicacaoException
	{	try
		{	HibernateUtil.beginTransaction();

			Conta umConta = contaDAO.recuperaUmaConta(id);

			HibernateUtil.commitTransaction();
			
			return umConta;
		} 
		catch(ObjetoNaoEncontradoException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

		    throw new AplicacaoException("Conta nao encontrado.");
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

	public List recuperaContas()
	{	try
		{	HibernateUtil.beginTransaction();

			List contas = contaDAO.recuperaContas();

			HibernateUtil.commitTransaction();
			
			return contas;
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