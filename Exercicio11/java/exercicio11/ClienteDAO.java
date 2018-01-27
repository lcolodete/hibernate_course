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
//   		Inicia uma transação
//==>
			HibernateUtil.beginTransaction();

//			Recupera ou cria uma sessão
//==>
			Session sessao = HibernateUtil.getSession();

//          Salva o objeto cliente
//==>
			sessao.save(umCliente);
			
// 			Comita a transação
//==>
			HibernateUtil.commitTransaction();

//			retorna o número do cliente salvo			
//==>
			return umCliente.getNumero();
		} 
		catch(HibernateException e)
		{	try
			{	/* O método rollbackTransaction() fecha a  sessão 
				 * hibernate,  mas  quando  a  sessão  é  fechada 
				 * novamente no finaly não ocorre nenhum erro.
			     *
			     * Se comentarmos o rollbackTransaction() abaixo,
			     * (quando  um  novo método da classe  ClienteDAO 
			     * for  executado  uma nova sessão  será  aberta, 
			     * mas a  transação que não sofreu rollback  será 
			     * recuperada.  Logo,  quando esta transação  for 
			     * comitada ocorrerá um erro pelo fato da  sessão 
			     * que a criou já ter sido fechada.
			     *
			     * Para testar,  comente o  rollbackTransaction()
			     * de recupera UmCliente(long numero) abaixo. 
			     */
			
//				Efetua rollback da transação
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
//				Fecha a sessão
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