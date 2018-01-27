package exercicio01;

import corejava.Console;
import java.util.List;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;

import org.apache.log4j.Logger;

public class Principal
{	
	public static void main (String[] args)
	{	
		// Criando um logger

//==>  
		
		Logger logger = Logger.getLogger(new Principal().getClass().getName());
	    
	    // Nome do logger que ser� criado:  "exercicio01.Principal"
	     	
		/*
		 * Configuration - O m�todo configure() da classe Configuration l� 
		 * e efetua o parse do arquivo hibernate.cfg.xml e dos arquivos de 
		 * mapeamento mencionados no hibernate.cfg.xml. O objeto 
		 * Configuration permite a gera��o de um SessionFactory.
		 *
		 * SessionFactory - o m�todo buildSessionFactory() constr�i o objeto 
		 * SessionFactory. Geralmente uma aplica��o possui um �nico 
		 * SessionFactory. As Threads que atendem a requisi��es de clientes 
		 * obt�m sess�es a partir do objeto SessionFactory. Objetos do tipo 
		 * SessionFactory s�o imut�veis. O comportamento de um SessionFactory 
		 * � controlado pelas propriedades fornecidas em tempo de 
		 * configura��o. Durante a constru��o do SessionFactory � criado um 
		 * pool de conex�es e aberta uma conex�o para este pool utilizando 
		 * os dados fornecidos no arquivo hibernate.cfg.xml. Em seguida s�o 
		 * criados os Strings relativos aos comandos SQL necess�rios para 
		 * lidar com as tabelas mencionadas nos arquivos de mapeamento. 
		 * Neste momento n�o s�o criados os PreparedStatements, mas apenas 
		 * os Strings. O objeto SessionFactory permite ainda a cria��o de 
		 * um objeto do tipo Session.
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

		SessionFactory sf = null;
		Session sessao = null;
		Transaction tx = null;
		
		try
		{	long inicio, fim;
		
			Console.readLine('\n' + "Aperte uma tecla para prosseguir...");
		
			inicio = System.currentTimeMillis();

			// Criando o objeto Configutation
			
//==>
			
			Configuration cfg1 = new Configuration();
			
			fim = System.currentTimeMillis();
			
			System.out.println ('\n' + "Milissegundos decorridos para new Configuration() = " + (fim - inicio));
			Console.readLine('\n' + "Aperte uma tecla para prosseguir...");
			
			inicio = System.currentTimeMillis();

			// Lendo o arquivo hibernate.cfg.xml e o arquivo de mapeamento Cliente.hbm.xml 

//==>		

			Configuration cfg2 = cfg1.configure("hibernate.cfg.xml");
			
			fim = System.currentTimeMillis();
			
			System.out.println ('\n' + "Milissegundos decorridos para configure(\"hibernate.cfg.xml\") = " + (fim - inicio));
			Console.readLine('\n' + "Aperte uma tecla para prosseguir...");

			inicio = System.currentTimeMillis();

			// Criando o objeto SessionFactory
			
//==>		
	
			sf = cfg2.buildSessionFactory();
			
			fim = System.currentTimeMillis();
			
			System.out.println ('\n' + "Milissegundos decorridos para buildSessionFactory() = " + (fim - inicio));
			Console.readLine('\n' + "Aperte uma tecla para prosseguir...");

			inicio = System.currentTimeMillis();

			// Abrindo uma sess�o Hibernate
			
//==>
			sessao = sf.openSession();
			
			fim = System.currentTimeMillis();

			System.out.println ('\n' + "Milissegundos decorridos abrir uma sessao = " + (fim - inicio));
			Console.readLine('\n' + "Aperte uma tecla para prosseguir...");

			// Iniciando uma Transa��o
			
//==>
			tx = sessao.beginTransaction();

			inicio = System.currentTimeMillis();
			
			// Recuperando um List de objetos do tipo Cliente

//==>
			
			List clientes = sessao.createQuery("FROM Cliente ORDER BY NUMERO").list(); 
			
			fim = System.currentTimeMillis();

			System.out.println ('\n' + "Milissegundos decorridos recuperar clientes com HQL = " + (fim - inicio));
			Console.readLine('\n' + "Aperte uma tecla para prosseguir...");

			System.out.println('\n' + "======> " + clientes.getClass().getName());

			// Comitando a transa��o
			
//==>
			tx.commit();

			if (clientes.isEmpty())
			{	System.out.println('\n' + "Nao ha clientes cadastrados.");
			}
			else
			{	System.out.println("");

				// Exibindo os Clientes que foram recuperados
				
				for (int i = 0; i < clientes.size(); i++)
				{	Cliente umCliente = (Cliente)clientes.get(i);
					System.out.println(	
				    	"Numero = " + umCliente.getNumero() + 
						"  Nome = " + umCliente.getNome() +
						"  Salario = " + umCliente.getSalario());
				}
			} 
			
			// Efetuando um log s� para testar se est� funcionando

			logger.error("Efetuando um log s� para testar se funciona.");
			
			Console.readLine('\n' + "Aperte uma tecla para prosseguir...");
		}
		catch(HibernateException e)
		{   if (tx != null)
		    {   try
		        {	// Efetuando o rollback da transa��o
//==>
		    	tx.rollback();
		        }
		        catch(HibernateException he)
		        { }
		    }

		    // efetuar log com o log4j

			logger.error("Ocorreu um erro durante a recupera��o dos clientes");

		    throw e;
		}
		finally
		{   try
		    {	// Fechando a sess�o Hibernate e o objeto SessionFactory

//==>

			sessao.close();
			sf.close();
			
//==>			
		    }
		    catch(HibernateException he)
		    {	
		     	// efetuar log com o log4j

				logger.error("Ocorreu um erro ao fechar a Session ou o SessionFactory");

				throw he;
			}
		}
	}
}
