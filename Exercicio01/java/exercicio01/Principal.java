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
	    
	    // Nome do logger que será criado:  "exercicio01.Principal"
	     	
		/*
		 * Configuration - O método configure() da classe Configuration lê 
		 * e efetua o parse do arquivo hibernate.cfg.xml e dos arquivos de 
		 * mapeamento mencionados no hibernate.cfg.xml. O objeto 
		 * Configuration permite a geração de um SessionFactory.
		 *
		 * SessionFactory - o método buildSessionFactory() constrói o objeto 
		 * SessionFactory. Geralmente uma aplicação possui um único 
		 * SessionFactory. As Threads que atendem a requisições de clientes 
		 * obtêm sessões a partir do objeto SessionFactory. Objetos do tipo 
		 * SessionFactory são imutáveis. O comportamento de um SessionFactory 
		 * é controlado pelas propriedades fornecidas em tempo de 
		 * configuração. Durante a construção do SessionFactory é criado um 
		 * pool de conexões e aberta uma conexão para este pool utilizando 
		 * os dados fornecidos no arquivo hibernate.cfg.xml. Em seguida são 
		 * criados os Strings relativos aos comandos SQL necessários para 
		 * lidar com as tabelas mencionadas nos arquivos de mapeamento. 
		 * Neste momento não são criados os PreparedStatements, mas apenas 
		 * os Strings. O objeto SessionFactory permite ainda a criação de 
		 * um objeto do tipo Session.
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

			// Abrindo uma sessão Hibernate
			
//==>
			sessao = sf.openSession();
			
			fim = System.currentTimeMillis();

			System.out.println ('\n' + "Milissegundos decorridos abrir uma sessao = " + (fim - inicio));
			Console.readLine('\n' + "Aperte uma tecla para prosseguir...");

			// Iniciando uma Transação
			
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

			// Comitando a transação
			
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
			
			// Efetuando um log só para testar se está funcionando

			logger.error("Efetuando um log só para testar se funciona.");
			
			Console.readLine('\n' + "Aperte uma tecla para prosseguir...");
		}
		catch(HibernateException e)
		{   if (tx != null)
		    {   try
		        {	// Efetuando o rollback da transação
//==>
		    	tx.rollback();
		        }
		        catch(HibernateException he)
		        { }
		    }

		    // efetuar log com o log4j

			logger.error("Ocorreu um erro durante a recuperação dos clientes");

		    throw e;
		}
		finally
		{   try
		    {	// Fechando a sessão Hibernate e o objeto SessionFactory

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
