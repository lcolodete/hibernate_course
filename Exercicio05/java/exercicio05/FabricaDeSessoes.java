package exercicio05;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FabricaDeSessoes
{	private static FabricaDeSessoes fabrica = null;
	private SessionFactory sf = null;
			
	private FabricaDeSessoes()
	{	sf = new Configuration()
				.configure("hibernate.cfg.xml")
				.buildSessionFactory();
	}

	public static Session criarSessao()
	{	if (fabrica == null)
		{	fabrica = new FabricaDeSessoes();
		}	

		return fabrica.sf.openSession();
	}

	public static void fecharFabricaDeSessoes()
	{	if (fabrica != null)
			if (fabrica.sf != null)
				fabrica.sf.close();
	}
}