package exercicio46.controleTransacao;

import net.sf.cglib.proxy.Enhancer;

public class FabricaDeAppService 
{
	private static Class classeDoInterceptador = InterceptadorDeAppService.class;
	
	public static Object getAppService(Class classeDoBean) 
		throws Exception 
    {
		InterceptadorDeAppService interceptador = 
            (InterceptadorDeAppService) classeDoInterceptador.newInstance();
		
		Object object = Enhancer.create(classeDoBean, interceptador);
		
		// Cria um proxy que é subclasse de classeDoBean (ProdutoAppService,
		// por  exemplo)  e  que  manda  executar  o  método  intercept() da 
		// classe  interceptadora  ou  callback  sempre  que  um  método  do 
		// AppService  é  executado.   A  função  do  método  intercept()  é 
		// verificar se o método do AppService que está sendo  executado  no 
		// momento é ou não transacional.
		
		return object;
	}
}
