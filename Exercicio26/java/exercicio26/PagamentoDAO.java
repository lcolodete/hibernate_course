package exercicio26;

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

	public Pagamento recuperaUmPagamento(long id) 
	{	try
		{	Session sessao = HibernateUtil.getSession();

		    //Pagamento pagamento = (Pagamento)sessao.get(Pagamento.class, new Long(id));
			// Nao faz buscas polimorficas
		    
		    Pagamento umPagamento = (Pagamento)sessao
		    	.createQuery("from exercicio26.Pagamento as p where p.id = :id")
		    	.setParameter("id", id)
		    	.uniqueResult();

			/* É preciso utilizar o nome completamente qualificado da classe.

		       Para efetuar uma busca polimórfica:
		    
		       Pagamento pagamento = (Pagamento)sessao
		       		.createCriteria(Pagamento.class)
		            .add(Expression.eq("id", id))
		            .uniqueResult();
		    */
		                                           ;       
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

	public List recuperaPagamentos()
	{	try
		{	Session sessao = HibernateUtil.getSession();

			return sessao
				.createQuery("from exercicio26.Pagamento as p order by id asc")
				.list();

			// É preciso utilizar o nome completamente qualificado da classe.
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}
}