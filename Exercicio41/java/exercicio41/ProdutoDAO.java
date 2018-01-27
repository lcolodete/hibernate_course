package exercicio41;

import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
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

			Produto umProduto = (Produto)sessao.get(Produto.class, new Long(numero));
			
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

			List produtos = sessao.createQuery("from Produto order by id").list();

			return produtos;
		} 
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);
		}
	}
	
	public List recuperaProdutosPelaDescricaoComHQL(String descricao)
	{	Session sessao = HibernateUtil.getSession();

		descricao = '%' + descricao.toUpperCase() + '%';

		try
		{   
			
			String busca = "from Produto   where   descricao like :descricao order by   descricao asc";
			// ou          "from Produto p where p.descricao like :descricao order by p.descricao asc";
			
/*          Note  que  a  restri��o  � expressa em termos da propriedade, 
 *          descricao  da  classe Produto  e que  na linha comentada  foi 
 *          utilizada a nota��o orientada a objetos: 
 *
 *          Assim  como  em  Java,  p.descricao pode, embora n�o deva, ser 
 *          abreviada para descricao.
 */

			List produtos = sessao.createQuery(busca)
			                      .setString("descricao", descricao)
			                      .list();
			return produtos;
		} 
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);   
		}
	}

	public List recuperaProdutosPelaDescricaoComCriteria1(String descricao)
	{	Session sessao = HibernateUtil.getSession();

		descricao = '%' + descricao.toUpperCase() + '%';

		try
		{   Criteria criteria = sessao.createCriteria(Produto.class);
   			criteria.add(Expression.like("descricao", descricao));
			List produtos = criteria.list();

//          ou
//
// 			List produtos = sessao
//				.createCriteria(Produto.class)
//				.add(Expression.like("descricao", descricao))
//				.list();

			return produtos;
		} 
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);   
		}
	}

	public List recuperaProdutosPelaDescricaoComCriteria2(String descricao)
	{	Session sessao = HibernateUtil.getSession();

		try
		{   List produtos = sessao
				.createCriteria(Produto.class)
   			    .add(Expression.like("descricao", 
   			                         descricao.toUpperCase(), 
   			                         MatchMode.ANYWHERE))
   			    .list();
   			                      
   			// Os MatchModes poss�veis s�o: START, END, ANYWHERE e EXACT

			return produtos;
		} 
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);   
		}
	}

	public List recuperaProdutosPelaDescricaoComSQL(String descricao)
	{	Session sessao = HibernateUtil.getSession();

		descricao = '%' + descricao.toUpperCase() + '%';

		/* O  resultado de uma busca SQL pode retornar na mesma linha
		 * o estado de mais de  uma inst�ncia de entidades diferentes 
		 * e  at�  mesmo  o  estado  de  v�rias inst�ncias  da  mesma 
		 * entidade (no caso de auto-relacionamento).
		 * 
		 * �  preciso uma  forma de  distinguir  entre as  diferentes 
		 * entidades.  Buscas  nativas em SQL  utilizam  placeholders 
		 * (s�mbolos em uma express�o matem�tica ou l�gica que  podem
		 * ser substitu�dos pelo  nome de  qualquer  elemento  de  um 
		 * conjunto) que s�o os nomes entre chaves. Cada  placeholder
		 * especifica o nome de uma propriedade no estilo HQL. Quando
		 * esta busca � executada no c�digo, � preciso prover o  nome
		 * da classe que � referenciado por prod nos placeholders.
		 */
		 
		try
		{   
			String busca = "select p.ID as {prod.id}, " +
			                      "p.NOME as {prod.nome}, " +
			                      "UPPER(p.DESCRICAO) as {prod.descricao}, " +
			                      "p.LANCE_MINIMO as {prod.lanceMinimo}, " + 
			                      "p.DATA_CADASTRO as {prod.dataCadastro}, " +
			                      "p.DATA_VENDA as {prod.dataVenda} " +
		                   "from PRODUTO p " +
		                   "where p.DESCRICAO like :descricao " +
		                   "order by p.DESCRICAO asc";

			List produtos = sessao
				.createSQLQuery(busca)
			    .addEntity("prod", Produto.class)
			    .setString("descricao", descricao.toUpperCase())
				.list();
							
			/* Uma alternativa simplificada para esta busca:
			 
		  	String busca = "select {prod.*} " +
		                   "from PRODUTO prod " +
		                   "where DESCRICAO like :descricao " +
		                   "order by DESCRICAO asc";
		                   
		    O  placeholder  {prod.*} �   substitu�do  por  uma  lista
		    contendo   os  nomes   das  colunas   mapeadas   e   seus 
		    respectivos   aliases,   que   s�o   as   propriedades da 
		    entidade  Produto.  A  �nica   restri��o  �  que  o  nome  
		    utilizado  para o  placeholder  deve  ser  o  mesmo  nome 
		    que � utilizado como alias da tabela no comando SQL. 
     		*/

			return produtos;
		} 
		catch(HibernateException e)
		{	throw new InfraestruturaException(e);   
		}
	}
}
