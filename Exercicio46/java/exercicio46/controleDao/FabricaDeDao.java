package exercicio46.controleDao;

import exercicio46.dao.HibernateDaoGenerico;
import net.sf.cglib.proxy.Enhancer;

public class FabricaDeDao
{
    private static Class classeDoDao = HibernateDaoGenerico.class; 
    private static Class classeDoInterceptador = InterceptadorDeDAO.class; 

    //produtoDAO = FabricaDeDao.<ProdutoDAO>getDao(ProdutoDAO.class, Produto.class);
    
	@SuppressWarnings("unchecked")  
    public static <T> T getDao(Class<T> interfaceDoDao, Class tipoDoDao) 
        throws Exception 
    {
        InterceptadorDeDAO interceptador = 
            (InterceptadorDeDAO) classeDoInterceptador.newInstance();

        Enhancer enhancer = new Enhancer();

        // O proxy deve implementar uma interface (ProdutoDAO por exemplo),
        // deve  estender  a classe  HibernateDaoGenerico e  deve chamar  o
        // método  intercept()  da  classe   interceptadora,  isto  é,   da
        // classe InterceptadorDeDAO (classe callback).
        
        enhancer.setInterfaces(
        		new Class[] { interfaceDoDao }); // ProdutoDAO, por exemplo
        enhancer.setSuperclass(classeDoDao);     // HibernateDaoGenerico  
        enhancer.setCallback(interceptador);     // InterceptadorDeDAO

        // Como a classe Proxy que será criada estende HibernateDaoGenerico
        // é  preciso  que  o  proxy  possua  um  construtor  semelhante ao 
        // construtor de HibernateDaoGenerico(Class class). 
        
        // O  método  create()  abaixo  recebe  como  parâmetro  o tipo  do 
        // argumento   do construtor de  HibernateDaoGenerico e  o valor do
        // argumento que deve ser passado para ele.
        
        // Documentação do método create() da classe Enhancer:
        // create(java.lang.Class[] argumentTypes, 
        //        java.lang.Object[] arguments) 
        
        return (T) enhancer.create(new Class[] { Class.class }, 
        		                   new Object[] { tipoDoDao });
    }
}
