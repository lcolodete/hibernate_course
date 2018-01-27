package exercicio46.controleDao;

import java.lang.reflect.Method;

import exercicio46.dao.ExecutorDeBuscas;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class InterceptadorDeDAO implements MethodInterceptor 
{
	/* 
	 * MethodProxy  -  Classes  geradas pela  classe Enhancer passam 
	 * este objeto para o objeto MethodInterceptor registrado quando
	 * um método  interceptado é  executado.  Ele pode ser utilizado
	 * para  invocar o  método  original,  ou  chamar o mesmo método
	 * sobre um objeto diferente do mesmo tipo.
	 */
	
	public Object intercept (Object objeto, 
    		                 Method metodo, 
    		                 Object[] args, 
                             MethodProxy metodoDoProxy) 
    	throws Throwable 
    {
        ExecutorDeBuscas daoGenerico = (ExecutorDeBuscas)objeto;

        String nomeDoMetodo = metodo.getName();
        
        if (nomeDoMetodo.startsWith("busca"))
        {	return daoGenerico.executaBusca(metodo, args);
        }
        else if (nomeDoMetodo.startsWith("recuperaConjuntoDe"))
        {	return daoGenerico.executaBuscaDeConjunto(metodo, args);
        }
        else if (nomeDoMetodo.startsWith("recupera"))
        {	return daoGenerico.executaBuscaDeLista(metodo, args);
        }
        else 
        {  	return metodoDoProxy.invokeSuper(objeto, args);
        }
    }
}
