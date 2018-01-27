package exercicio11;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import java.util.List;

public class ClienteDAO
{	
	public long inclui(Cliente umCliente) 
	{	try
		{	
//   		Inicia uma transa��o
//==>
			HibernateUtil.beginTransaction();

//			Recupera ou cria uma sess�o
//==>
			Session sessao = HibernateUtil.getSession();

//          Salva o objeto cliente
//==>
			sessao.save(umCliente);
			
// 			Comita a transa��o
//==>
			HibernateUtil.commitTransaction();

//			retorna o n�mero do cliente salvo			
//==>
			return umCliente.getNumero();
		} 
		catch(HibernateException e)
		{	try
			{	/* O m�todo rollbackTransaction() fecha a  sess�o 
				 * hibernate,  mas  quando  a  sess�o  �  fechada 
				 * novamente no finaly n�o ocorre nenhum erro.
			     *
			     * Se comentarmos o rollbackTransaction() abaixo,
			     * (quando  um  novo m�todo da classe  ClienteDAO 
			     * for  executado  uma nova sess�o  ser�  aberta, 
			     * mas a  transa��o que n�o sofreu rollback  ser� 
			     * recuperada.  Logo,  quando esta transa��o  for 
			     * comitada ocorrer� um erro pelo fato da  sess�o 
			     * que a criou j� ter sido fechada.
			     *
			     * Para testar,  comente o  rollbackTransaction()
			     * de recupera UmCliente(long numero) abaixo. 
			     */
			
//				Efetua rollback da transa��o
//==>
				HibernateUtil.rollbackTransaction();

			}
			catch(InfraestruturaException ie)
			{ }

		    throw e;
		}
		finally
		{   try
		    {    
//				Fecha a sess�o
//==>
				HibernateUtil.closeSession();
		    }
		    catch(InfraestruturaException he)
		    {	throw he;
			}
		}
	}

	public boolean altera(Cliente umCliente) 
	{
		try
		{
			HibernateUtil.beginTransaction();
			Session sessao = HibernateUtil.getSession();

			Cliente c = (Cliente)sessao.load(Cliente.class, 
					  						new Long(umCliente.getNumero()),
					  						LockMode.READ);
			
			sessao.evict(c);
			sessao.update(umCliente);
			
			HibernateUtil.commitTransaction();
			return true;
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

			return false;
		}
		catch(HibernateException e)
		{
			try
			{
				HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{
			}

		    throw e;
		}
		finally
		{
			try
		    {
				HibernateUtil.closeSession();
		    }
		    catch(InfraestruturaException he)
		    {
		    	throw he;
		    }
		}
	}

	public boolean exclui(long numero) 
	{	try
		{	HibernateUtil.beginTransaction();

			Session sessao = HibernateUtil.getSession();
		
			Cliente umCliente = (Cliente)sessao
				.load(Cliente.class, new Long(numero));
			sessao.delete(umCliente);

			HibernateUtil.commitTransaction();
			
			return true;
		}
		catch(ObjectNotFoundException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

			return false;
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
		    {	
		    	throw he;
		    }
		}
	}

	public Cliente recuperaUmCliente(long numero) 
		throws AplicacaoException
	{	try
		{	HibernateUtil.beginTransaction();

			Session sessao = HibernateUtil.getSession();

			Cliente umCliente = (Cliente)sessao
				.load(Cliente.class,
					  new Long(numero),
					  LockMode.READ);

			HibernateUtil.commitTransaction();
			
			return umCliente;
		} 
		catch(ObjectNotFoundException e)
		{	try
			{	
				HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

			throw new AplicacaoException("Cliente nao encontrado");
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

	public List recuperaClientes()
	{	try
		{	HibernateUtil.beginTransaction();

			Session sessao = HibernateUtil.getSession();

			List clientes = sessao
				.createQuery("from Cliente order by numero")
				.list();

			HibernateUtil.commitTransaction();
			
			return clientes;
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