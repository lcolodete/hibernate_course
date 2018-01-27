package exercicio46.controleTransacao;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import exercicio46.util.AplicacaoException;
import exercicio46.util.HibernateUtil;
import exercicio46.util.InfraestruturaException;

public class InterceptadorDeAppService implements MethodInterceptor
{
	/*
	 * Observe que este método só iniciará uma transação se o método original
	 * contiver a anotação "@Transacional". O método isTransactional é responsável
	 * por identificar os métodos que são transacionais, isto é, os métodos que
	 * contêm a anotação "@Transacional".
	 * 
	 * Observe também que a sessão Hibernate é sempre fechada.
	 */
	public Object intercept(Object objeto, Method metodo, Object[] args,
			MethodProxy metodoDoProxy) throws Throwable
	{
		try
		{
			Object result = null;

			if (isTransactional(objeto, metodo))
			{
				HibernateUtil.beginTransaction();

				result = metodoDoProxy.invokeSuper(objeto, args);

				HibernateUtil.commitTransaction();
			}
			else
			{
				result = metodoDoProxy.invokeSuper(objeto, args);
			}
			return result;
		}
		catch (AplicacaoException e)
		{
			try
			{
				HibernateUtil.rollbackTransaction();
			}
			catch (InfraestruturaException ie)
			{
			}

			throw e;
		}
		catch (InfraestruturaException e)
		{
			try
			{
				HibernateUtil.rollbackTransaction();
			}
			catch (InfraestruturaException ie)
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
			catch (InfraestruturaException he)
			{
				throw he;
			}
		}
	}

	public boolean isTransactional(Object objeto, Method metodo) throws Exception
	{
		return metodo.isAnnotationPresent(Transacional.class);
	}
}
