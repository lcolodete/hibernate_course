package exercicio03;

import corejava.*;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;

public class Principal
{	
	public static void main (String[] args)
	{	
		ClienteDAO clienteDAO = new ClienteDAO();
	
		boolean continua = true;
		while (continua)
		{	System.out.println('\n' + "O que voce deseja fazer?");
			System.out.println('\n' + "1. Recuperar todos os clientes");
			System.out.println("2. Efetuar o teste 1 (Uma sessao com duas transacoes)");
			System.out.println("3. Efetuar o teste 2 (Duas sessoes: cada uma com uma transacao)");
			System.out.println("4. Sair");
						
			int opcao = Console.readInt('\n' + 
				"Digite um numero entre 1 e 4:");			
				
			switch (opcao)
			{	case 1:
				{	List arrayClientes = clienteDAO.recuperaClientes();
									
					if (arrayClientes.size() == 0)
					{	System.out.println('\n' + 
							"Nao ha clientes cadastrados.");
						break;
					}
										
					System.out.println("");

					for (int i = 0; i < arrayClientes.size(); i++)
					{	Cliente umCliente = (Cliente)arrayClientes.get(i);
						System.out.println(	
							"Numero = " + umCliente.getNumero() + 
						   	"  Nome = " + umCliente.getNome() +
							"  Salario = " + umCliente.getSalario());
					}
										
					break;
				}

				case 2:
				{	Session sessao = null;
					Transaction tx = null;
					
					Cliente c1, c2 = null;
					
					try
					{	sessao = FabricaDeSessoes.criarSessao();
						tx = sessao.beginTransaction();
			
						long numero = Console.readInt ('\n' + 
								"Informe o numero do cliente: ");
				
						c1 = (Cliente) sessao.get(Cliente.class, 
												  new Long(numero));
						
						c2 = (Cliente) sessao.get(Cliente.class, 
						 						  new Long(numero));
				
						if(c1 == null)
						{	System.out.println('\n' + 
									"Cliente inexistente.");
							tx.commit();
							return;
						}
						
						if (c1 == c2)
						{	System.out.println('\n' + 
							   "c1 e c2 apontam para o " + 
							   "mesmo objeto em memoria" + '\n');
						}
						
					    tx.commit();
					    
					    // ***********************************************************
			
						tx = sessao.beginTransaction();
			
						numero = Console.readInt ('\n' + 
							"Informe o numero do cliente: ");
						
						c2 = (Cliente) sessao.get(Cliente.class, 
												  new Long(numero));
				
						if(c2 == null)
						{	System.out.println('\n' + "Cliente inexistente.");
							tx.commit();	
							return;
						}
						else
						{	if (c1 == c2)
								System.out.println('\n' + 
									"c1 e c2 apontam para o " +
								    "mesmo objeto em memoria" + '\n');
							else
								System.out.println('\n' + 
									"c1 e c2 nao apontam para o " +
								    "mesmo objeto em memoria" + '\n');
						}		

					    tx.commit();
					}
					catch(HibernateException e)
					{	if (tx != null)
						{	try
							{	tx.rollback();
							}
							catch(HibernateException he)
							{ }
						}
					    
						throw e;
					}
					finally
					{	try
						{	sessao.close();
						}
						catch(HibernateException he)
						{	throw he;
						}
					}

					break;
				}

				case 3:
				{	Session sessao = null;
					Transaction tx = null;
					
					Cliente c1, c2 = null;
					
					try
					{	sessao = FabricaDeSessoes.criarSessao();
						tx = sessao.beginTransaction();
			
						long numero = Console.readInt ('\n' + 
											"Informe o numero do cliente: ");
				
						c1 = (Cliente) sessao.get(Cliente.class, 
												  new Long(numero));
						
						c2 = (Cliente) sessao.get(Cliente.class, 
						  						  new Long(numero));
				
						if(c1 == null)
						{	System.out.println('\n' + "Cliente inexistente.");
							tx.commit();
							return;
						}
						
						if (c1 == c2)
							System.out.println('\n' + 
							   "c1 e c2 apontam para o " +
							   "mesmo objeto em memoria" + '\n');
			
					    tx.commit();
					}
					catch(HibernateException e)
					{	if (tx != null)
						{	try
							{	tx.rollback();
							}
							catch(HibernateException he)
							{ }
						}
					    
						throw e;
					}
					finally
					{	try
						{	sessao.close();
						}
						catch(HibernateException he)
						{	throw he;
						}
					}

					try
					{	sessao = FabricaDeSessoes.criarSessao();
						tx = sessao.beginTransaction();
			
						long numero = Console.readInt ('\n' + 
										"Informe o numero do cliente: ");
						
						c2 = (Cliente) sessao.get(Cliente.class, 
												  new Long(numero));
				
						if(c2 == null)
						{	System.out.println('\n' + 
									"Cliente inexistente.");
							tx.commit();	
							return;
						}
						else
						{	if (c1 == c2)
								System.out.println('\n' + 
									"c1 e c2 apontam para o " +
								    "mesmo objeto em memoria" + '\n');
							else
								System.out.println('\n' + 
									"c1 e c2 nao apontam para o " +
								    "mesmo objeto em memoria" + '\n');
						}		
			
					    tx.commit();
					}
					catch(HibernateException e)
					{	if (tx != null)
						{	try
							{	tx.rollback();
							}
							catch(HibernateException he)
							{ }
						}
					    
						throw e;
					}
					finally
					{	try
						{	sessao.close();
						}
						catch(HibernateException he)
						{	throw he;
						}
					}

					break;
				}

				case 4:
					continua = false;
					break;

				default:
					System.out.println('\n' + "Opcao invalida!");						
			}
		}		
	}
}