package exercicio22.produto;

import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import java.util.List;
import java.util.Set;
import java.util.LinkedHashSet;

import exercicio22.util.*;
import exercicio22.lance.*;

public class ProdutoDAO
{
	public long inclui(Produto umProduto)
	{
		try
		{
			Session sessao = HibernateUtil.getSession();

			sessao.save(umProduto);

			return umProduto.getId();
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}

	public void altera(Produto umProduto)
	{
		try
		{
			Session sessao = HibernateUtil.getSession();

			sessao.update(umProduto);
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}

	public void exclui(Produto umProduto)
	{
		try
		{
			Session sessao = HibernateUtil.getSession();

			sessao.delete(umProduto);
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}

	public Produto recuperaUmProduto(long numero)
			throws ObjetoNaoEncontradoException
	{
		try
		{
			Session sessao = HibernateUtil.getSession();

			Produto umProduto = (Produto) sessao.get(Produto.class, new Long(numero));

			if (umProduto == null)
			{
				throw new ObjetoNaoEncontradoException();
			}

			return umProduto;
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}

	public Produto recuperaUmProdutoComLock(long numero)
			throws ObjetoNaoEncontradoException
	{
		try
		{
			Session sessao = HibernateUtil.getSession();

			Produto umProduto = (Produto) sessao.get(Produto.class, new Long(numero),
					LockMode.UPGRADE);

			if (umProduto == null)
			{
				throw new ObjetoNaoEncontradoException();
			}

			return umProduto;
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}

	public Produto recuperaUmProdutoELances(long numero)
			throws ObjetoNaoEncontradoException
	{
		try
		{
			Session sessao = HibernateUtil.getSession();

			String busca = "from Produto p left outer join fetch p.lances where p.id = :id";

			Produto umProduto = (Produto) sessao.createQuery(busca).setLong("id",
					numero).uniqueResult();

			if (umProduto == null)
			{
				throw new ObjetoNaoEncontradoException();
			}

			return umProduto;
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}

	public Produto recuperaUmProdutoELanceVencedor(long numero)
			throws ObjetoNaoEncontradoException
	{
		try
		{
			Session sessao = HibernateUtil.getSession();

			String busca = "from Produto p left outer join fetch p.lanceVencedor where p.id = :id";

			Produto umProduto = (Produto) sessao.createQuery(busca).setLong("id",
					numero).uniqueResult();

			if (umProduto == null)
			{
				throw new ObjetoNaoEncontradoException();
			}

			return umProduto;
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}

	public List recuperaProdutos()
	{
		try
		{
			Session sessao = HibernateUtil.getSession();

			List produtos = sessao.createQuery("from Produto order by id").list();

			return produtos;
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public Set recuperaProdutosELances()
	{
		try
		{
			Session sessao = HibernateUtil.getSession();

			List produtos = sessao.createQuery(
					"from Produto p left outer join fetch p.lances order by p.id asc")
					.list();

			return new LinkedHashSet(produtos);
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}

	public double recuperaValorMaiorLance(Produto produto)
	{
		try
		{
			Session sessao = HibernateUtil.getSession();

			sessao.lock(produto, LockMode.UPGRADE);

			String busca = "select max(l.valor) " + "from Lance l "
					+ "where l.produto = :produto";

			Double valorMaiorLance = (Double) sessao.createQuery(busca).setEntity(
					"produto", produto).uniqueResult();

			if (valorMaiorLance == null)
			{
				valorMaiorLance = new Double(produto.getLanceMinimo() - 1);
			}

			return valorMaiorLance.doubleValue();
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}

	public Lance recuperaUltimoLance(Produto produto)
	{
		try
		{
			Session sessao = HibernateUtil.getSession();

			String busca = "select lance " + "from Lance lance "
					+ "inner join lance.produto produto " + "where produto.id = :id "
					+ "and lance.valor = (select max(valor) " + "from Lance l "
					+ "where l.produto.id = :id)";

			Lance umLance = (Lance) sessao.createQuery(busca).setLong("id",
					produto.getId()).uniqueResult();

			return umLance;
		}
		catch (HibernateException e)
		{
			try
			{
				HibernateUtil.rollbackTransaction();
			}
			catch (InfraestruturaException ie)
			{
			}

			throw e;
		}
	}

	public Produto recuperaUmProdutoECategorias(long numero)
			throws ObjetoNaoEncontradoException
	{
		try
		{
			Session sessao = HibernateUtil.getSession();

			/* ==> */String busca = "from Produto p "
					+ "left outer join fetch p.produtoCategorias pc "
					+ "left outer join fetch pc.categoria " + "where p.id = :id";

			Produto umProduto = (Produto) sessao.createQuery(busca).setLong("id",
					numero).uniqueResult();

			if (umProduto == null)
			{
				throw new ObjetoNaoEncontradoException();
			}

			return umProduto;
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}
}