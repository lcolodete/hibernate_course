package exercicio05;

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
		Cliente umCliente = null;
	
		boolean continua = true;
		while (continua)
		{	System.out.println('\n' + "O que voce deseja fazer?");
			System.out.println('\n' + "1. Recuperar todos os clientes");
			System.out.println("2. Efetuar o teste 1 - dynamic update em uma unica sessao");
			System.out.println("3. Efetuar o teste 2 - dynamic update em sessoes diferentes");
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
					int i;
					for (i = 0; i < arrayClientes.size(); i++)
					{	umCliente = (Cliente)arrayClientes.get(i);
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
					
					try
					{	String nome = Console.readLine('\n' + "Informe o nome: ");
						double salario = 
							Console.readDouble('\n' + "Informe o salario: ");
					
						// Conceito de objeto transiente: existe em memória, 
						// mas ainda não foi persistido.
					
						umCliente = new Cliente(nome, salario);
			
						sessao = FabricaDeSessoes.criarSessao();
						
						System.out.println('\n' + "====> Abriu uma sessao hibernate.");
						
						tx = sessao.beginTransaction();
			
						sessao.save(umCliente);
			
						String novoNome;
						double novoSalario;
						
						String resposta = "";
						
						while(!resposta.equals("s") && !resposta.equals("n"))
						{	resposta = Console.readLine('\n' + "Deseja alterar o nome? (s/n) ");
						}
						
						if (resposta.equals("s"))
						{	novoNome = Console.readLine('\n' + "Informe o novo nome: ");
							umCliente.setNome(novoNome);
						}
						
						resposta = "";
						
						while(!resposta.equals("s") && !resposta.equals("n"))
						{	resposta = Console.readLine('\n' + "Deseja alterar o salario? (s/n) ");
						}
						
						if (resposta.equals("s"))
						{	novoSalario = Console.readDouble('\n' + "Informe o novo salario: ");
							umCliente.setSalario(novoSalario);
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
							System.out.println('\n' + "====> Fechou sessao hibernate.");
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
					
					try
					{	String nome = Console.readLine('\n' + "Informe o nome: ");
						double salario = Console.
									readDouble('\n' + "Informe o salario: ");
					
						umCliente = new Cliente(nome, salario);
			
						sessao = FabricaDeSessoes.criarSessao();

						System.out.println('\n' + "====> Abriu uma sessao hibernate.");

						tx = sessao.beginTransaction();
			
						sessao.save(umCliente);
			
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
							System.out.println('\n' + "====> Fechou sessao hibernate.");
						}
						catch(HibernateException he)
						{	throw he;
						}
					}


					String novoNome;
					double novoSalario;
					
					String resposta = "";
					
					while(!resposta.equals("s") && !resposta.equals("n"))
					{	resposta = Console.readLine('\n' + "Deseja alterar o nome?");
					}
					
					if (resposta.equals("s"))
					{	novoNome = Console.readLine('\n' + "Informe o novo nome: ");
						umCliente.setNome(novoNome);
					}
					
					resposta = "";
					
					while(!resposta.equals("s") && !resposta.equals("n"))
					{	resposta = Console.readLine('\n' + "Deseja alterar o salario?");
					}
					
					if (resposta.equals("s"))
					{	novoSalario = Console.readDouble('\n' + "Informe o novo salario: ");
						umCliente.setSalario(novoSalario);
					}

					try
					{	sessao = FabricaDeSessoes.criarSessao();

						System.out.println('\n' + "====> Abriu uma sessao hibernate.");

						tx = sessao.beginTransaction();
			
						sessao.update(umCliente);
			
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
							System.out.println('\n' + "====> Fechou sessao hibernate.");
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
