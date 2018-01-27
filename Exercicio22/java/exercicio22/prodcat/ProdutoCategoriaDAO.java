package exercicio22.prodcat;

import org.hibernate.Session;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import java.util.List;

import exercicio22.util.*;

public class ProdutoCategoriaDAO
{
	public ProdutoCategoriaDAO()
	{
	}

	public IdProdutoCategoria inclui(ProdutoCategoria produtoCategoria)
	{
		try
		{
			Session sessao = HibernateUtil.getSession();

			sessao.save(produtoCategoria);

			return produtoCategoria.getId();
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}

	public void altera(ProdutoCategoria produtoCategoria)
	{
		try
		{
			Session sessao = HibernateUtil.getSession();

			sessao.update(produtoCategoria);
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}

	public void exclui(IdProdutoCategoria idProdutoCategoria)
	{
		try
		{
			Session sessao = HibernateUtil.getSession();

			ProdutoCategoria pc = (ProdutoCategoria) sessao.get(
					ProdutoCategoria.class, idProdutoCategoria);
			// A execu��o do m�todo get() acima n�o provoca um acesso a
			// banco de dados, uma vez que o objeto j� se encontra no
			// primeiro n�vel de cache: a sess�o.

			sessao.delete(pc);
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}

	public void exclui(ProdutoCategoria pc)
	{
		try
		{
			Session sessao = HibernateUtil.getSession();

			sessao.delete(pc);
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}

	public ProdutoCategoria recuperaUmProdutoCategoriaComLock(
			IdProdutoCategoria idProdutoCategoria)
			throws ObjetoNaoEncontradoException
	{
		try
		{
			Session sessao = HibernateUtil.getSession();

			return (ProdutoCategoria) sessao.load(ProdutoCategoria.class,
					idProdutoCategoria, LockMode.UPGRADE);
		}
		catch (ObjectNotFoundException e)
		{
			throw new ObjetoNaoEncontradoException();
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}

	public ProdutoCategoria recuperaUmProdutoCategoria(
			IdProdutoCategoria idProdutoCategoria)
			throws ObjetoNaoEncontradoException
	{
		try
		{
			Session sessao = HibernateUtil.getSession();

			ProdutoCategoria pc = (ProdutoCategoria) sessao.get(
					ProdutoCategoria.class, idProdutoCategoria);

			if (pc == null)
			{
				throw new ObjetoNaoEncontradoException();
			}

			return pc;
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}

	public List recuperaProdutoCategorias()
	{
		try
		{
			Session sessao = HibernateUtil.getSession();

			List produtoCategorias = sessao.createQuery(
					"from ProdutoCategoria as pc order by id asc").list();
			return produtoCategorias;
		}
		catch (HibernateException e)
		{
			throw new InfraestruturaException(e);
		}
	}
}
