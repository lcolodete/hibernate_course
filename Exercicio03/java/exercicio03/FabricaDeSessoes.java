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

	// O método configure() da classe Configuration procura pelo arquivo
	// denominado hibernate.cfg.xml e utiliza os mapeamentos e propriedades
	// especificados neste arquivo. 
	
	// O método buildSessionFactory() constrói o objeto SessionFactory. 
	// Geralmente uma aplicação possui um único SessionFactory. As Threads
	// que atendem a requisições de clientes obtêm sessões a partir do objeto
	// SessionFactory. SessionFactorys são imutáveis. O comportamento de um
	// SessionFactory é controlado pelas propriedades fornecidas em tempo
	// de configuração.  

	/*
	 * Configuration - Processa o arquivo de configuração Hibernate.cfg.
	 * xml que fornece informações como o banco de dados a ser utilizado 
	 * e permite a geração de um SessionFactory.
	 *
	 * SessionFactory - Mantém o mapeamento entre as classes persistentes 
	 * e o banco de dados e permite a criação de um objeto do tipo Session.
	 *
	 * Session - Representa um conjunto de objetos persistentes que estão 
	 * instanciados em memória, e mantém o seu estado em sincronia com o 
	 * banco de dados no final de uma transação. Possibilita também a 
	 * criação de objetos do tipo Query.
	 *
	 * Query - Possibilita a execução de consultas em HQL.
	 *
	 * Transaction - Possibilita que o commit ou o rollback de uma transação
	 * seja realizado independentemente do ambiente de execução da aplicação
	 * e de forma coerente com a sincronização de objetos persistentes 
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