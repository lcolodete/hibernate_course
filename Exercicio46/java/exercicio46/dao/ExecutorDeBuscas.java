package exercicio46.dao;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import exercicio46.util.ObjetoNaoEncontradoException;

public interface ExecutorDeBuscas<T>
{
    public List<T> executaBuscaDeLista(Method method, Object[] queryArgs);
        
    public T executaBusca(Method method, Object[] queryArgs) 
    	throws ObjetoNaoEncontradoException;
    
    public Set<T> executaBuscaDeConjunto(Method method, Object[] queryArgs);
}
