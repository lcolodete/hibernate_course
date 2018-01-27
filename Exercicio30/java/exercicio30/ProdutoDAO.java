package exercicio30;

import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.LockMode;
import java.util.List;

public class ProdutoDAO
{	
	public long inclui(Produto umProduto) 
	{	try
		{	Session sessao = HibernateUtil.getSession();

			sessao.save(umProduto);
			
			return umProduto.getId();
		} 
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public void altera(Produto umProduto) 
	{	try
		{	Session sessao = HibernateUtil.getSession();

			sessao.update(umProduto);
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public void exclui(Produto umProduto) 
	{	try
		{	Session sessao = HibernateUtil.getSession();
		
			sessao.delete(umProduto);
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public Produto recuperaUmProduto(long numero) 
		throws ObjetoNaoEncontradoException
	{	try
		{	Session sessao = HibernateUtil.getSession();

			Produto umProduto = (Produto)sessao.get(Produto.class, new Long(numero));
			
			if (umProduto == null)
			{	throw new ObjetoNaoEncontradoException();
			}

			return umProduto;
		} 
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public Produto recuperaUmProdutoComLock(long numero) 
		throws ObjetoNaoEncontradoException
	{	try
		{	Session sessao = HibernateUtil.getSession();

			Produto umProduto = (Produto)sessao
										.load(Produto.class, 
										      new Long(numero), 
										      LockMode.UPGRADE);
			return umProduto;
		} 
		catch(ObjectNotFoundException e)  // subclasse de HibernateException
		{	throw new ObjetoNaoEncontradoException();
		}
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public List recuperaProdutos()
	{	try
		{	Session sessao = HibernateUtil.getSession();

			List produtos = sessao.createQuery("from Produto order by id").list();

			return produtos;
		} 
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}
}