package exercicio27;

import org.hibernate.HibernateException;
import java.util.List;

public class PagamentoAppService
{	
	private static PagamentoDAO pagamentoDAO = new PagamentoDAO();

	public long inclui(Pagamento umPagamento) 
	{	try
		{	HibernateUtil.beginTransaction();

			long id = pagamentoDAO.inclui(umPagamento);

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

	public void altera(Pagamento umPagamento) 
	{	try
		{	HibernateUtil.beginTransaction();

			pagamentoDAO.altera(umPagamento);

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
		    {	HibernateUtil.closeSession();
		    }
		    catch(InfraestruturaException he)
		    {	throw he;
		    }
		}
	}

	public void exclui(Pagamento umPagamento) 
	{	try
		{	HibernateUtil.beginTransaction();

			pagamentoDAO.exclui(umPagamento);

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
		    {	HibernateUtil.closeSession();
		    }
		    catch(HibernateException he)
		    {	throw he;
		    }
		}
	}

	public Pagamento recuperaUmPagamento(long numero) 
		throws AplicacaoException
	{	try
		{	HibernateUtil.beginTransaction();

			Pagamento umPagamento = pagamentoDAO.recuperaUmPagamento(numero);

			HibernateUtil.commitTransaction();
			
			return umPagamento;
		} 
		catch(ObjetoNaoEncontradoException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

			throw new AplicacaoException("Pagamento nao encontrado.");
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
		    {	HibernateUtil.closeSession();
		    }
		    catch(InfraestruturaException he)
		    {	throw he;
		    }
		}
	}

	public Cartao recuperaUmPagamentoEmCartao(long numero) 
		throws AplicacaoException
	{	try
		{	HibernateUtil.beginTransaction();

			Cartao umPagamento = pagamentoDAO.recuperaUmPagamentoEmCartao(numero);

			HibernateUtil.commitTransaction();
			
			return umPagamento;
		} 
		catch(ObjetoNaoEncontradoException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

			throw new AplicacaoException("Pagamento nao encontrado.");
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
		    {	HibernateUtil.closeSession();
		    }
		    catch(InfraestruturaException he)
		    {	throw he;
		    }
		}
	}

	public List recuperaPagamentosEmCartao()
	{	try
		{	HibernateUtil.beginTransaction();

			List pagamentos = pagamentoDAO.recuperaPagamentosEmCartao();

			HibernateUtil.commitTransaction();
			
			return pagamentos;
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
		    {	HibernateUtil.closeSession();
		    }
		    catch(InfraestruturaException he)
		    {	throw he;
		    }
		}
	}

	public List recuperaPagamentos()
	{	try
		{	HibernateUtil.beginTransaction();

			List pagamentos = pagamentoDAO.recuperaPagamentos();

			HibernateUtil.commitTransaction();
			
			return pagamentos;
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
		    {	HibernateUtil.closeSession();
		    }
		    catch(InfraestruturaException he)
		    {	throw he;
		    }
		}
	}
}