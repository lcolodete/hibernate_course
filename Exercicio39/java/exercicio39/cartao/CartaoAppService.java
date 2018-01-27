package exercicio39.cartao;

import org.hibernate.HibernateException;
import java.util.List;
import exercicio39.util.*;

public class CartaoAppService
{	
	private static CartaoDAO cartaoDAO = new CartaoDAO();

	public long inclui(Cartao umCartao) 
	{	try
		{	HibernateUtil.beginTransaction();

			long id = cartaoDAO.inclui(umCartao);

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

	public void altera(Cartao umCartao) 
	{	try
		{	HibernateUtil.beginTransaction();

			cartaoDAO.altera(umCartao);

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

	public void exclui(Cartao umCartao) throws AplicacaoException
	{	try
		{	HibernateUtil.beginTransaction();

			umCartao = cartaoDAO.recuperaUmCartao(umCartao.getId());

			if(umCartao.getCcc() != null)
			{	throw new AplicacaoException("Este Cartao possui " +
				                             "cliente e conta " +
				                             "e nao pode ser removido");
			}

			cartaoDAO.exclui(umCartao);

			HibernateUtil.commitTransaction();
		} 
		catch(ObjetoNaoEncontradoException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

			throw new AplicacaoException("Cartao nao encontrado.");
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

	public Cartao recuperaUmCartao(long id) throws AplicacaoException
	{	try
		{	HibernateUtil.beginTransaction();

			Cartao umCartao = cartaoDAO.recuperaUmCartao(id);

			HibernateUtil.commitTransaction();
			
			return umCartao;
		} 
		catch(ObjetoNaoEncontradoException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

		    throw new AplicacaoException("Cartao nao encontrado.");
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

	public List recuperaCartoes()
	{	try
		{	HibernateUtil.beginTransaction();

			List cartoes = cartaoDAO.recuperaCartoes();

			HibernateUtil.commitTransaction();
			
			return cartoes;
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