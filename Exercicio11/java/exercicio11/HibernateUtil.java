package exercicio11;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil 
{	private static final SessionFactory sessionFactory;
	private static final ThreadLocal<Session> threadSession = new ThreadLocal<Session>();
	private static final ThreadLocal<Transaction> threadTransaction = new ThreadLocal<Transaction>();

	static 
	{	sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	}

	public static Session getSession() 
	{	// System.out.println("Abriu ou recuperou sessão");
	
		Session s = (Session) threadSession.get();
		// Abre uma nova Sessão, se a thread ainda não possui uma.
		try 
		{	if (s == null) 
			{	s = sessionFactory.openSession();
				threadSession.set(s);
				//System.out.println("criou sessao");
			}
		} 
		catch (HibernateException ex) 
		{	throw new InfraestruturaException(ex);
		}
		return s;
	}

	public static void closeSession() 
	{	//System.out.println("Fechou sessão");

		try 
		{	Session s = (Session) threadSession.get();
			threadSession.set(null);
			if (s != null && s.isOpen())
				s.close();
		} 	
		catch (HibernateException ex) 
		{	throw new InfraestruturaException(ex);
		}
	}

	public static void beginTransaction() 
	{	//System.out.println("Vai criar transacao");

		Transaction tx = (Transaction) threadTransaction.get();
		try 
		{	if (tx == null) 
			{	tx = getSession().beginTransaction();
				threadTransaction.set(tx);
				//System.out.println("Criou transacao");
			}
			else
			{	//System.out.println("Nao criou transacao");
			}
		} 
		catch (HibernateException ex) 
		{	throw new InfraestruturaException(ex);
		}
	}

	public static void commitTransaction() 
	{	Transaction tx = (Transaction) threadTransaction.get();
		try 
		{	if ( tx != null && !tx.wasCommitted() && !tx.wasRolledBack() )
			{	tx.commit();
				//System.out.println("Comitou transacao");
			}
			threadTransaction.set(null);
		} 
		catch (HibernateException ex) 
		{	rollbackTransaction();
			throw new InfraestruturaException(ex);
		}
	}

	public static void rollbackTransaction() 
	{	//System.out.println("Vai efetuar rollback de transacao");
	
		Transaction tx = (Transaction) threadTransaction.get();
		try 
		{	threadTransaction.set(null);
			if ( tx != null && !tx.wasCommitted() && !tx.wasRolledBack() ) 
			{	tx.rollback();
			}
		} 
		catch (HibernateException ex) 
		{	throw new InfraestruturaException(ex);
		} 
		finally 
		{	closeSession();
		}
	}
}