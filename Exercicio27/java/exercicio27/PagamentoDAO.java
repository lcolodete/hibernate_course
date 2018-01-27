package exercicio27;

import org.hibernate.Session;
import org.hibernate.HibernateException;
import java.util.List;

public class PagamentoDAO
{	
	public long inclui(Pagamento umPagamento) 
	{	try
		{	Session sessao = HibernateUtil.getSession();

			sessao.save(umPagamento);

			return umPagamento.getId();
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public void altera(Pagamento umPagamento) 
	{	try
		{	Session sessao = HibernateUtil.getSession();

			sessao.update(umPagamento);
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public void exclui(Pagamento umPagamento) 
	{	try
		{	Session sessao = HibernateUtil.getSession();
		
			sessao.delete(umPagamento);
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public Pagamento recuperaUmPagamento(long numero) 
	{	try
		{	Session sessao = HibernateUtil.getSession();

			Pagamento umPagamento = (Pagamento)sessao
				.get(Pagamento.class, new Long(numero));

			if (umPagamento == null)
				throw new ObjetoNaoEncontradoException();

			return umPagamento;
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public Cartao recuperaUmPagamentoEmCartao(long numero) 
	{	try
		{	Session sessao = HibernateUtil.getSession();

			Cartao umPagamento = (Cartao)sessao
				.get(Cartao.class, new Long(numero));

			if (umPagamento == null)
				throw new ObjetoNaoEncontradoException();

			return umPagamento;
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public List recuperaPagamentosEmCartao()
	{	try
		{	Session sessao = HibernateUtil.getSession();

			return sessao
				.createQuery("from Cartao order by numero")
				.list();
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public List recuperaPagamentos()
	{	try
		{	Session sessao = HibernateUtil.getSession();

			return sessao
				.createQuery("from Pagamento order by numero")
				.list();
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}
}