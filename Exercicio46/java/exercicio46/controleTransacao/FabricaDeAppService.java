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
		
		// Cria um proxy que � subclasse de classeDoBean (ProdutoAppService,
		// por  exemplo)  e  que  manda  executar  o  m�todo  intercept() da 
		// classe  interceptadora  ou  callback  sempre  que  um  m�todo  do 
		// AppService  �  executado.   A  fun��o  do  m�todo  intercept()  � 
		// verificar se o m�todo do AppService que est� sendo  executado  no 
		// momento � ou n�o transacional.
		
		return object;
	}
}
