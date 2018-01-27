package exercicio14.produto;

import org.hibernate.LockMode;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.HibernateException;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import exercicio14.util.*;
import exercicio14.lance.*;

public class ProdutoDAO
{	
	public long inclui(Produto umProduto) 
	{	try
		{	HibernateUtil.beginTransaction();
			Session sessao = HibernateUtil.getSession();

			sessao.save(umProduto);
			
			HibernateUtil.commitTransaction();
			return umProduto.getId();
		} 
		catch(HibernateException e)
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

	public void altera(Produto umProduto) 
	{	try
		{	HibernateUtil.beginTransaction();
			Session sessao = HibernateUtil.getSession();

			sessao.update(umProduto);
			
			HibernateUtil.commitTransaction();
		}
		catch(HibernateException e)
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

	public void exclui(Produto umProduto) 
	{	try
		{	HibernateUtil.beginTransaction();
			Session sessao = HibernateUtil.getSession();
		
			sessao.delete(umProduto);

			HibernateUtil.commitTransaction();
		}
		catch(HibernateException e)
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
		    catch(HibernateException he)
		    {	throw he;
		    }
		}
	}

	public Produto recuperaUmProduto(long numero) 
		throws AplicacaoException
	{	
		try
		{
			HibernateUtil.beginTransaction();

			Session sessao = HibernateUtil.getSession();

			Produto umProduto = (Produto)sessao
				.load(Produto.class, 
					  new Long(numero),
					  LockMode.READ);

			HibernateUtil.commitTransaction();
			
			return umProduto;
		} 
		catch(ObjectNotFoundException e)
		{	
			try
			{
				HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{
			}

			throw new AplicacaoException("Produto nao encontrado");
		}
		catch(HibernateException e)
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

	public Produto recuperaUmProdutoELances(long numero) 
		throws AplicacaoException
	{	
		try
		{
			HibernateUtil.beginTransaction();

			Session sessao = HibernateUtil.getSession();

			/* O  que a  maioria das  pessoas pensam  quando escutam a 
			 * palavra  join no  contexto  de  bancos  de dados SQL  é 
			 * um inner join.  Um  inner  join é o  tipo de join  mais 
			 * simples.
			 *
			 * Por exemplo, para se  recuperar  todos os  produtos que 
			 * possuem lances, é preciso utilizar um inner join. Neste 
			 * caso apenas produtos que possuem lances são recuperados. 
			 * Mas se desejarmos recuperar os produtos e valores nulos
			 * para os  dados dos  lances  quando o  produto não tiver 
			 * lances,  neste caso  utilizaremos um  left  outer join. 
			 * (estilo ANSI).
			 *
			 * Se fizermos a  junção de  duas tabelas PRODUTO e LANCE, 
			 * utilizando um inner join obteremos  todos os produtos e 
			 * seus lances na tabela resultante.  No caso de um  "left 
			 * outer join", cada  linha da  tabela a  esquerda (left - 
			 * tabela PRODUTO) que nunca satisfaz a condição de junção
			 * também  é  incluída  no  resultado  com  valores  nulos 
			 * retornados para todas as colunas da tabela LANCE.
			 * 
			 * Um "right outer join" recuperaria  todos os lances  com 
			 * um valor nulo para o produto se o lance não tem relação
			 * com nenhum produto.
			 * 
			 * A condição de  join deve ser  especificada na  cláusula 
			 * "on" para  uma  junção no  estilo "ANSI" ou na cláusula 
			 * "where" para uma junção no estilo "theta". 
			 * 
			 * Exemplo: P.PRODUTO_ID = L.PRODUTO_ID.
			 *
			 * Left Outer Join no Oracle:
			 *
			 * SELECT P.ID, P.NOME, L.ID, L.VALOR
			 * FROM PRODUTO P, LANCE L
			 * WHERE P.ID = L.PRODUTO_ID(+)
			 * ORDER BY P.ID, L.VALOR;
			 */

//==>
			String busca = "FROM Produto p left outer join fetch p.lances l where p.id = :id order by p.id asc"; 

//			A busca retorna um resultado único (uniqueResult()).
//==>			
			Produto umProduto =	(Produto) sessao.createQuery(busca).setLong("id", numero).uniqueResult();

			if(umProduto == null)
			{
				throw new ObjetoNaoEncontradoException(); 
			}

	/*
			Set lances = umProduto.getLances();
			
			// System.out.println("*******>>> " + 
			// 				lances.getClass().getName());
			// retorna: org.hibernate.collection.PersistentSet
	
			
			for (Iterator it = lances.iterator(); it.hasNext(); )
			{	Lance lance = (Lance) it.next();
				System.out.println(	'\n' + 
					"      Lance numero = "  + lance.getId() + 
					"  valor = "  + lance.getValor() +
					"  Data = "  + lance.getDataCriacaoMasc());
			}	
	*/
			HibernateUtil.commitTransaction();

			return umProduto;
		} 
		catch(ObjetoNaoEncontradoException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

			throw new AplicacaoException("Produto nao encontrado");
		}
		catch(HibernateException e)
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

//	public List recuperaProdutos()
//	{	try
//		{	HibernateUtil.beginTransaction();
//
//			Session sessao = HibernateUtil.getSession();
//
//			List produtos = sessao
//==>
//
//			HibernateUtil.commitTransaction();
//			
//			return produtos;
//		} 
//		catch(HibernateException e)
//		{	try
//			{	HibernateUtil.rollbackTransaction();
//			}
//			catch(InfraestruturaException ie)
//			{ }
//
//		    throw e;
//		}
//		finally
//		{   try
//		    {   HibernateUtil.closeSession();
//		    } 
//		    catch(InfraestruturaException he)
//		    {	throw he;
//		    }
//		}
//	}

	@SuppressWarnings("unchecked")
	public Set recuperaProdutosELances()
	{
		try
		{
			HibernateUtil.beginTransaction();

			Session sessao = HibernateUtil.getSession();

//==>
			List produtos = sessao.createQuery("FROM Produto p left join fetch p.lances l order by p.id asc, l.id asc").list();
/*   	
			System.out.println(produtos.getClass().getName());
			
			for (int i = 0; i < produtos.size(); i++)
			{	Produto produto = (Produto)produtos.get(i);
				System.out.println("Id do produto = " + produto.getId());  
				
				Set lances = produto.getLances();
				
			 	// System.out.println("*******>>> " + 
			 	// 				lances.getClass().getName());

			 	// retorna: org.hibernate.collection.PersistentSet
				
				for (Iterator it = lances.iterator(); it.hasNext(); )
				{	Lance lance = (Lance) it.next();
					System.out.println(	'\n' + 
						"      Lance numero = "  + lance.getId() + 
						"  valor = "  + lance.getValor() +
						"  Data = "  + lance.getDataCriacaoMasc());
				}	
			}
*/   
			HibernateUtil.commitTransaction();
			
			return new LinkedHashSet(produtos);
			
//==>
			/*
			 * Um objeto do tipo  LinketHashSet é um Set. Esta  classe
			 * implementa  uma "Hash table" e  uma "linked list"  para 
			 * que   a  ordem  de   iteração  seja  previsível.   Esta 
			 * implementação  difere de  HashSet por  manter uma lista 
			 * duplamente  encadeada que  passa por todas as  entradas 
			 * do Set.  A lista encadeada  define a ordem de iteração, 
			 * que é a ordem de inserção dos elementos no Set. 
			 */
			
		} 
		catch(HibernateException e)
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

	@SuppressWarnings("unchecked")
	public void adicionarLance(long idProduto, Lance umLance) 
	{	
		try
		{
			HibernateUtil.beginTransaction();

			Session sessao = HibernateUtil.getSession();

			Produto umProduto = (Produto)sessao.get(Produto.class, 
												new Long(idProduto));

			// Designa o produto ao lance corrente
//==>		
			umLance.setProduto(umProduto);

			// Adiciona o lance à coleção de lances do produto
//==>
			umProduto.getLances().add(umLance);

  			/* O método add() retorna true se o elemento é  adicionado
  			 * ao Set, e  retorna  false se o  elemento já  existe  no 
  			 * Set.
  		 	 */
  		      
			/* Como em  nenhum  momento estamos  persistindo  o  lance 
			 * através do método save(), a única forma de  persistí-lo 
			 * é adicionando-o à  coleção de lances  de  Produto,  uma 
			 * vez  que  cascade="save-update" foi  especificado  para 
			 * esta coleção.  No entanto, é preciso executar o  método
			 * umLance.setProduto(umProduto),  caso  contrário  quando 
			 * o  lance  for  inserido  no  banco  de  dados  o  valor 
			 * especificado para  produto  será null, o que  provocará
			 * um erro.
		 	 * 
		 	 * umProduto.getLances().add(umLance) ==> provoca o insert
		 	 *                       no  banco de  dados em  função do 
		 	 *                       cascade="save-update".
		 	 *
		 	 * umLance.setProduto(umProduto);  irá provocar um update, 
		 	 *                       na tabela LANCE  caso o  atributo
		 	 *                       inverse="true"  não  tenha   sido 
		 	 *                       especificado no mapeamento do set
		 	 *                       lances no  arquivo de  mapeamento 
		 	 *                       da   classe   Produto.   Embora a 
		 	 *                       execução  do método  setProduto() 
		 	 *                       seja   anterior   à   execução do 
		 	 *                       método  add(),  primeiramente   o 
		 	 *                       lance  é  inserido,  e em seguida 
		 	 *                       ele é atualizado.
		 	 *
		 	 * O  método  add(umLance) acima provoca a recuperação  de 
		 	 * todos  os  lances de  um  produto (conforme vem abaixo) 
		 	 * para poder  adicionar o lance  corrente ao conjunto  de 
		 	 * lances:
		 	 * 
		 	 * select lances0_.PRODUTO_ID as PRODUTO4_1_, 
		 	 *        lances0_.ID as ID1_, lances0_.ID as ID1_0_, 
		 	 *        lances0_.VALOR as VALOR1_0_, 
		 	 *        lances0_.DATA_CRIACAO as DATA3_1_0_, 
		 	 *        lances0_.PRODUTO_ID as PRODUTO4_1_0_ 
		 	 * from LANCE lances0_ 
		 	 * where lances0_.PRODUTO_ID=?
		 	 */

			HibernateUtil.commitTransaction();
		} 
		catch(HibernateException e)
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
}