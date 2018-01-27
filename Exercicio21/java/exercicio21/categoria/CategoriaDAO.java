package exercicio21.categoria;

import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.LockMode;

import exercicio21.util.HibernateUtil;
import exercicio21.util.InfraestruturaException;
import exercicio21.util.ObjetoNaoEncontradoException;

import java.util.List;
import java.util.Set;
import java.util.LinkedHashSet;

public class CategoriaDAO
{	
	public long inclui(Categoria umaCategoria) 
	{	try
		{	Session sessao = HibernateUtil.getSession();

			sessao.save(umaCategoria);
			
			return umaCategoria.getId();
		} 
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public void altera(Categoria umaCategoria) 
	{	try
		{	Session sessao = HibernateUtil.getSession();

			sessao.update(umaCategoria);
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public void exclui(Categoria umaCategoria) 
	{	try
		{	Session sessao = HibernateUtil.getSession();
		
			sessao.delete(umaCategoria);
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public Categoria recuperaUmaCategoria(long numero)
		throws ObjetoNaoEncontradoException	
	{	try
		{	Session sessao = HibernateUtil.getSession();

			Categoria umaCategoria = (Categoria)sessao.get(Categoria.class, 
			                                               new Long(numero));

			if(umaCategoria == null)
			{	throw new ObjetoNaoEncontradoException();
			}

			return umaCategoria;
		} 
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public Categoria recuperaUmaCategoriaComLock(long numero) 
		throws ObjetoNaoEncontradoException	
	{	try
		{	Session sessao = HibernateUtil.getSession();

			Categoria umaCategoria = (Categoria)sessao
										.load(Categoria.class, 
										      new Long(numero), 
										      LockMode.UPGRADE);
			return umaCategoria;
		} 
		catch(ObjectNotFoundException e)  // subclasse de HibernateException
		{	throw new ObjetoNaoEncontradoException();
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public Categoria recuperaUmaCategoriaESubCategorias(long numero)
		throws ObjetoNaoEncontradoException	
	{	try
		{	Session sessao = HibernateUtil.getSession();

			String busca = "from Categoria c " +
			               "left outer join fetch c.subCategorias " +
			               "where c.id = :id";

			Categoria umaCategoria =
				(Categoria) sessao.createQuery(busca)
							    .setLong("id", numero)
							    .uniqueResult();

			if(umaCategoria == null)
			{	throw new ObjetoNaoEncontradoException();
			}

			return umaCategoria;
		} 
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public Set recuperaSubCategorias(long numero) 
		throws ObjetoNaoEncontradoException	
	{	try
		{	Session sessao = HibernateUtil.getSession();

			String busca = "from Categoria c " +
			               "left outer join fetch c.subCategorias " +
			               "where c.id = :id";

			Categoria umaCategoria =
				(Categoria) sessao.createQuery(busca)
							    .setLong("id", numero)
							    .uniqueResult();

			if(umaCategoria == null)
			{	throw new ObjetoNaoEncontradoException();
			}

			return umaCategoria.getSubCategorias();
		} 
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public List recuperaCategorias()
	{	try
		{	Session sessao = HibernateUtil.getSession();

			List categorias = sessao
				.createQuery("from Categoria order by id")
				.list();

			return categorias;
		} 
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public Set recuperaCategoriasESubCategorias()
	{	try
		{	Session sessao = HibernateUtil.getSession();

			List categorias = sessao
				.createQuery("from Categoria c " +
				             "left outer join fetch c.subCategorias " +
				             "order by c.id asc")
				.list();

			return new LinkedHashSet(categorias);
		} 
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public Categoria recuperaUmaCategoriaEProdutos(long numero) 
		throws ObjetoNaoEncontradoException	
	{	try
		{	Session sessao = HibernateUtil.getSession();

			String busca = "from Categoria c " +
			               "left outer join fetch c.produtos " +
			               "where c.id = :id";

			Categoria umaCategoria =
				(Categoria) sessao.createQuery(busca)
							    .setLong("id", numero)
							    .uniqueResult();

			if(umaCategoria == null)
			{	throw new ObjetoNaoEncontradoException();
			}

			return umaCategoria;
		} 
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}
}