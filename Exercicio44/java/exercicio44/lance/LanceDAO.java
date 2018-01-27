package exercicio44.lance;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.HibernateException;
import java.util.List;

import exercicio44.cliente.Cliente;
import exercicio44.produto.Produto;
import exercicio44.util.*;

public class LanceDAO
{
	public long inclui(Lance umLance)
	{
		try
		{
			Session sessao = HibernateUtil.getSession();

			sessao.save(umLance);

			return umLance.getId();
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}

	public void exclui(Lance umLance)
	{
		try
		{
			Session sessao = HibernateUtil.getSession();

			sessao.delete(umLance);
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}

	public Lance recuperaUmLance(long numero) throws ObjetoNaoEncontradoException
	{
		try
		{
			Session sessao = HibernateUtil.getSession();

			Lance umLance = (Lance) sessao.get(Lance.class, new Long(numero));

			if (umLance == null)
			{
				throw new ObjetoNaoEncontradoException();
			}

			return umLance;
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}

	public Lance recuperaUmLanceComProduto(long numero)
			throws ObjetoNaoEncontradoException
	{
		try
		{
			Session sessao = HibernateUtil.getSession();

			String busca = "from Lance l " + "left outer join fetch l.produto "
					+ "where l.id = :id";

			Lance umLance = (Lance) sessao.createQuery(busca).setLong("id", numero)
					.uniqueResult();

			if (umLance == null)
			{
				throw new ObjetoNaoEncontradoException();
			}

			return umLance;
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}

	public List recuperaLances()
	{
		try
		{
			Session sessao = HibernateUtil.getSession();

			List lances = sessao.createQuery("from Lance order by id").list();

			return lances;
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}

	public List recuperaLancesDeUmProduto(Produto produto, double valor)
	{
		Session sessao = HibernateUtil.getSession();

		try
		{
			/* ==> */List lances = sessao.getNamedQuery("recuperaLancesDeUmProduto")
					.setEntity("produto", produto).setDouble("valor", valor).list();

			return lances;
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}

	public List recuperaLancesDeUmProdutoVersao2(Produto produto, double valor)
	{
		Session sessao = HibernateUtil.getSession();

		try
		{
			/* ==> */List lances = sessao.getNamedQuery("recuperaLancesDeUmProduto")
					.setParameter("produto", produto, Hibernate.entity(Produto.class))
					.setParameter("valor", valor, Hibernate.DOUBLE).list();
			return lances;
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}

	public List recuperaLancesDeUmProdutoVersao3(Produto produto, double valor)
	{
		Session sessao = HibernateUtil.getSession();
		/*
		 * Para alguns tipos de parâmetros é possível supor o tipo Hibernate a
		 * partir da classe do valor do parâmetro. Nestes casos não é necessário
		 * especificar o tipo Hibernate explicitamente.
		 * 
		 * Como se pode ver abaixo, isto funciona inclusive com entidades como
		 * produto. Esta abordagem funciona bem para parâmetros do tipo String,
		 * Integer e Boolean, por exemplo, mas não tão bem para Date, onde o tipo
		 * Hibernate pode ser timestamp, date ou time. Nestes casos é preciso
		 * utilizar o método apropriado (setDate(), por exemplo) ou o tipo Hibernate
		 * apropriado (Hibernate.DATE, por exemplo) como o terceiro argumento do
		 * método setParameter().
		 */

		try
		{
			/* ==> */List lances = sessao.getNamedQuery("recuperaLancesDeUmProduto")
					.setParameter("produto", produto).setParameter("valor", valor).list();
			return lances;
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}

	public List recuperaLancesDeUmProdutoEmitidosPorUmCliente(Produto produto,
			Cliente cliente)
	{
		Session sessao = HibernateUtil.getSession();

		try
		{
			Lance lance = new Lance();
			lance.setProduto(produto);
			lance.setCliente(cliente);

			/* ==> */List lances = sessao.getNamedQuery(
					"recuperaLancesDeUmProdutoEmitidosPorUmCliente").setProperties(lance)
					.list();
			return lances;
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}
}
