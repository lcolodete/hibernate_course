package exercicio45.controleTransacao;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import exercicio45.util.AplicacaoException;
import exercicio45.util.HibernateUtil;
import exercicio45.util.InfraestruturaException;

public class InterceptadorDeAppService implements MethodInterceptor
{
	/*
	 * Observe que o m�todo intercept() s� iniciar� uma transa��o se o m�todo
	 * original contiver a anota��o "@Transacional". O m�todo isTransacional �
	 * respons�vel por identificar os m�todos que s�o transacionais, isto �, os
	 * m�todos que cont�m a anota��o "@Transacional".
	 * 
	 * Observe tamb�m que a sess�o Hibernate � sempre fechada.
	 */

	/*
	 * Parametros:
	 * 
	 * objeto - "this", o objeto "enhanced", isto �, o proxy. metodo - o m�todo
	 * interceptado. args - um array de args; tipos primitivos s�o empacotados.
	 * metodoProxy - utilizado para executar um m�todo super.
	 * 
	 * MethodProxy - Classes geradas pela classe Enhancer passam este objeto para
	 * o objeto MethodInterceptor registrado quando um m�todo interceptado �
	 * executado. Ele pode ser utilizado para invocar o m�todo original, ou chamar
	 * o mesmo m�todo sobre um objeto diferente do mesmo tipo.
	 */
	public Object intercept(Object objeto, Method metodo, Object[] args,
			MethodProxy metodoProxy) throws Throwable
	{
		try
		{
			Object result = null;

			if (isTransacional(metodo))
			{
				HibernateUtil.beginTransaction();

				result = metodoProxy.invokeSuper(objeto, args);

				HibernateUtil.commitTransaction();
			}
			else
			{
				result = metodoProxy.invokeSuper(objeto, args);
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

	public boolean isTransacional(Method metodo) throws Exception
	{
		return metodo.isAnnotationPresent(Transacional.class);
	}
}
