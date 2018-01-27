package exercicio25;

import org.hibernate.Session;
import org.hibernate.HibernateException;
import java.util.List;

public class PagamentoDAO
{
	public long inclui(Pagamento umPagamento)
	{
		try
		{
			Session sessao = HibernateUtil.getSession();

			sessao.save(umPagamento);

			return umPagamento.getId();
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}

	public void altera(Pagamento umPagamento)
	{
		try
		{
			Session sessao = HibernateUtil.getSession();

			sessao.update(umPagamento);
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}

	public void exclui(Pagamento umPagamento)
	{
		try
		{
			Session sessao = HibernateUtil.getSession();

			sessao.delete(umPagamento);
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}

	public Pagamento recuperaUmPagamento(long numero)
			throws ObjetoNaoEncontradoException
	{
		try
		{
			Session sessao = HibernateUtil.getSession();

			Pagamento umPagamento = (Pagamento) sessao.get(Pagamento.class, new Long(
					numero));

			if (umPagamento == null)
				throw new ObjetoNaoEncontradoException();

			return umPagamento;
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}

	public Cartao recuperaUmPagamentoEmCartao(long numero)
			throws ObjetoNaoEncontradoException
	{
		try
		{
			Session sessao = HibernateUtil.getSession();

			// ==>
			Cartao umPagamento = (Cartao) sessao.get(Cartao.class, new Long(numero));

			if (umPagamento == null)
				throw new ObjetoNaoEncontradoException();

			return umPagamento;
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}

	public List recuperaPagamentos()
	{
		try
		{
			Session sessao = HibernateUtil.getSession();

			// ==>
			return sessao.createQuery("from Pagamento order by numero").list();

			/*
			 * O comando SQL que será executado:
			 * 
			 * UNION ALL - Evita que as linhas duplicadas sejam eliminadas. Como neste
			 * caso não há linhas duplicadas, a eliminação é desnecessária.
			 * 
			 * 
			 * select pagamento0_.ID as ID0_, 
			 * pagamento0_.DATA_PGTO as DATA2_0_,
			 * pagamento0_.VALOR as VALOR0_, 
			 * pagamento0_.NUMERO as NUMERO1_,
			 * pagamento0_.MES_EXP as MES2_1_, 
			 * pagamento0_.ANO_EXP as ANO3_1_,
			 * pagamento0_.BANCO as BANCO2_, 
			 * pagamento0_.AGENCIA as AGENCIA2_,
			 * pagamento0_.CONTA as CONTA2_, 
			 * pagamento0_.clazz_ as clazz_ 
			 * from (
			 * 		select NUMERO, 
			 * 			ANO_EXP, 
			 * 			DATA_PGTO, 
			 * 			null as CONTA, 
			 * 			null as AGENCIA,
			 * 			MES_EXP, 
			 * 			VALOR, 
			 * 			null as BANCO, 
			 * 			ID, 
			 * 			1 as clazz_ 
			 * 		from CARTAO 
			 * 		union all
			 * 		select 
			 * 			null as NUMERO, 
			 * 			null as ANO_EXP,
			 * 			DATA_PGTO, 
			 * 			CONTA, 
			 * 			AGENCIA, 
			 * 			null as MES_EXP,
			 *			VALOR, 
			 *			BANCO, 
			 *			ID, 
			 *			2 as clazz_ from CONTA ) pagamento0_
			 * order by pagamento0_.NUMERO
			 */

		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}
}