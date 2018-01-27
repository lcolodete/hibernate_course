package exercicio42;

import org.hibernate.Session;
import org.hibernate.HibernateException;
import java.util.List;
import java.util.Iterator;

// import static org.hibernate.criterion.Expression.*;
// Uma vez importados os membros estáticos, eles podem ser utilizados sem qualificação.

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
	{	
		try
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

			Produto umProduto = (Produto)sessao
				.get(Produto.class, new Long(numero));
			
			if(umProduto == null)
				throw new ObjetoNaoEncontradoException();

			return umProduto;
		} 
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public List recuperaProdutos()
	{	try
		{	Session sessao = HibernateUtil.getSession();

			List produtos = sessao
				.createQuery("from Produto order by id")
				.list();

			return produtos;
		} 
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}
	
	public Iterator recuperaProdutosComIterate()
	{	try
		{   Session sessao = HibernateUtil.getSession();
		
/* ==> */	Iterator it = sessao
				.createQuery("from Produto as p order by id asc")
				.iterate();

			// it será um objeto do tipo org.hibernate.impl.IteratorImpl. 
		
			/* Se a query  retorna vários resultados, cada  resultado  é 
			 * retornado em uma instância de Object[].  Cada  ocorrência
			 * no Object[]  será um proxy que  irá conter apenas a chave 
			 * primária  da linha correspondente na tabela de Produto.
			 *
			 * As entidades  retornadas no  resultado são  inicializadas 
			 * sob   demanda.   A  primeira  busca  SQL  retorna  apenas 
			 * identificadores.
			 *
			 * Quando  se  utiliza  o método iterate() para executar uma 
			 * busca, o Hibernate  recupera apenas  os valores  de chave
			 * primária  no  primeiro  select;  ele  tenta  encontrar  o 
			 * restante do estado dos objetos no cache, antes de efetuar 
			 * uma  nova  busca  para  recuperar  os  valores das demais 
			 * propriedades. 
			 *  
			*/

			return it;
		} 
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);   
		}
	}
}