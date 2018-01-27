package exercicio03;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FabricaDeSessoes
{	private static FabricaDeSessoes fabrica = null;
	private SessionFactory sf = null;
			
	private FabricaDeSessoes()
	{	sf = new Configuration().configure().buildSessionFactory();
	}

	// O m�todo configure() da classe Configuration procura pelo arquivo
	// denominado hibernate.cfg.xml e utiliza os mapeamentos e propriedades
	// especificados neste arquivo. 
	
	// O m�todo buildSessionFactory() constr�i o objeto SessionFactory. 
	// Geralmente uma aplica��o possui um �nico SessionFactory. As Threads
	// que atendem a requisi��es de clientes obt�m sess�es a partir do objeto
	// SessionFactory. SessionFactorys s�o imut�veis. O comportamento de um
	// SessionFactory � controlado pelas propriedades fornecidas em tempo
	// de configura��o.  

	/*
	 * Configuration - Processa o arquivo de configura��o Hibernate.cfg.
	 * xml que fornece informa��es como o banco de dados a ser utilizado 
	 * e permite a gera��o de um SessionFactory.
	 *
	 * SessionFactory - Mant�m o mapeamento entre as classes persistentes 
	 * e o banco de dados e permite a cria��o de um objeto do tipo Session.
	 *
	 * Session - Representa um conjunto de objetos persistentes que est�o 
	 * instanciados em mem�ria, e mant�m o seu estado em sincronia com o 
	 * banco de dados no final de uma transa��o. Possibilita tamb�m a 
	 * cria��o de objetos do tipo Query.
	 *
	 * Query - Possibilita a execu��o de consultas em HQL.
	 *
	 * Transaction - Possibilita que o commit ou o rollback de uma transa��o
	 * seja realizado independentemente do ambiente de execu��o da aplica��o
	 * e de forma coerente com a sincroniza��o de objetos persistentes 
	 * gerenciados pelo objeto Session.
	 *
	 */ 

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