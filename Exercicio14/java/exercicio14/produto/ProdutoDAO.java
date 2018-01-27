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
			 * palavra  join no  contexto  de  bancos  de dados SQL  � 
			 * um inner join.  Um  inner  join � o  tipo de join  mais 
			 * simples.
			 *
			 * Por exemplo, para se  recuperar  todos os  produtos que 
			 * possuem lances, � preciso utilizar um inner join. Neste 
			 * caso apenas produtos que possuem lances s�o recuperados. 
			 * Mas se desejarmos recuperar os produtos e valores nulos
			 * para os  dados dos  lances  quando o  produto n�o tiver 
			 * lances,  neste caso  utilizaremos um  left  outer join. 
			 * (estilo ANSI).
			 *
			 * Se fizermos a  jun��o de  duas tabelas PRODUTO e LANCE, 
			 * utilizando um inner join obteremos  todos os produtos e 
			 * seus lances na tabela resultante.  No caso de um  "left 
			 * outer join", cada  linha da  tabela a  esquerda (left - 
			 * tabela PRODUTO) que nunca satisfaz a condi��o de jun��o
			 * tamb�m  �  inclu�da  no  resultado  com  valores  nulos 
			 * retornados para todas as colunas da tabela LANCE.
			 * 
			 * Um "right outer join" recuperaria  todos os lances  com 
			 * um valor nulo para o produto se o lance n�o tem rela��o
			 * com nenhum produto.
			 * 
			 * A condi��o de  join deve ser  especificada na  cl�usula 
			 * "on" para  uma  jun��o no  estilo "ANSI" ou na cl�usula 
			 * "where" para uma jun��o no estilo "theta". 
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

//			A busca retorna um resultado �nico (uniqueResult()).
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
			 * Um objeto do tipo  LinketHashSet � um Set. Esta  classe
			 * implementa  uma "Hash table" e  uma "linked list"  para 
			 * que   a  ordem  de   itera��o  seja  previs�vel.   Esta 
			 * implementa��o  difere de  HashSet por  manter uma lista 
			 * duplamente  encadeada que  passa por todas as  entradas 
			 * do Set.  A lista encadeada  define a ordem de itera��o, 
			 * que � a ordem de inser��o dos elementos no Set. 
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

			// Adiciona o lance � cole��o de lances do produto
//==>
			umProduto.getLances().add(umLance);

  			/* O m�todo add() retorna true se o elemento �  adicionado
  			 * ao Set, e  retorna  false se o  elemento j�  existe  no 
  			 * Set.
  		 	 */
  		      
			/* Como em  nenhum  momento estamos  persistindo  o  lance 
			 * atrav�s do m�todo save(), a �nica forma de  persist�-lo 
			 * � adicionando-o �  cole��o de lances  de  Produto,  uma 
			 * vez  que  cascade="save-update" foi  especificado  para 
			 * esta cole��o.  No entanto, � preciso executar o  m�todo
			 * umLance.setProduto(umProduto),  caso  contr�rio  quando 
			 * o  lance  for  inserido  no  banco  de  dados  o  valor 
			 * especificado para  produto  ser� null, o que  provocar�
			 * um erro.
		 	 * 
		 	 * umProduto.getLances().add(umLance) ==> provoca o insert
		 	 *                       no  banco de  dados em  fun��o do 
		 	 *                       cascade="save-update".
		 	 *
		 	 * umLance.setProduto(umProduto);  ir� provocar um update, 
		 	 *                       na tabela LANCE  caso o  atributo
		 	 *                       inverse="true"  n�o  tenha   sido 
		 	 *                       especificado no mapeamento do set
		 	 *                       lances no  arquivo de  mapeamento 
		 	 *                       da   classe   Produto.   Embora a 
		 	 *                       execu��o  do m�todo  setProduto() 
		 	 *                       seja   anterior   �   execu��o do 
		 	 *                       m�todo  add(),  primeiramente   o 
		 	 *                       lance  �  inserido,  e em seguida 
		 	 *                       ele � atualizado.
		 	 *
		 	 * O  m�todo  add(umLance) acima provoca a recupera��o  de 
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