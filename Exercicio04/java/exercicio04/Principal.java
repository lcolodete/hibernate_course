package exercicio04;

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
			System.out.println("2. Efetuar o teste 1 (Insere e altera nome na mesma transacao. " + '\n' +
			                   "   Emite rollback se acontece um erro.)");
			System.out.println("3. Efetuar o teste 2 (Insere e altera nome na mesma transacao. " + '\n' +
			                   "   Emite commit se acontece um erro.)");
			System.out.println("4. Efetuar o teste 3 (Utiliza flush e mostra que podemos descobrir " + '\n' +
			                   "   a causa de um HibernateException)");
			System.out.println("5. Sair");
						
			int opcao = Console.readInt('\n' + 
				"Digite um numero entre 1 e 5:");			
				
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
					
					try
					{	String nome = Console.readLine('\n' + "Informe o nome: ");
						double salario = Console.readDouble('\n' + "Informe o salario: ");
					
						// Conceito de objeto transiente: existe em mem�ria, 
						// mas ainda n�o foi persistido.
					
						Cliente umCliente = new Cliente(nome, salario);
			
						sessao = FabricaDeSessoes.criarSessao();
						tx = sessao.beginTransaction();
			
						// O m�todo save() agenda a emiss�o do comando SQL 
						// INSERT e o objeto umCliente se torna persistente. 
						// Como o objeto umCliente passa a ser persistente 
						// ap�s a emiss�o do m�todo save(), se ap�s sua 
						// emiss�o o objeto umCliente for alterado, esta 
						// altera��o ser� persistida ao final da transa��o 
						// com um comando SQL UPDATE.
						
						sessao.save(umCliente);
						
						System.out.println('\n' + "umCliente.getNumero() = " + 
						                   umCliente.getNumero());
			
						/* 
					 	 * Observe que a altera��o do nome abaixo faz com que
					 	 * seja emitido um comando update ap�s o insert, na hora do
					 	 * commit.
					 	 *
					 	 */
			
						String outroNome = Console.readLine('\n' + "Informe outro nome: ");
						
						umCliente.setNome(outroNome);
					
					    tx.commit();

						// Como a tabela CLIENTE deste exerc�cio possui uma 
						// constraint UNIQUE para a coluna NOME, observe
						// que se cadastrarmos dois clientes com o mesmo 
						// nome, a exce��o s� ser� propagada quando o m�todo
						// tx.commit() for executado, pois apenas neste 
						// momento os comandos SQL ser�o emitidos. Se o 
						// segundo comando SQL falhar a transa��o n�o sofre 
						// rollback autom�tico: � preciso efetuar o rollback
						// ao capturar a exce��o, conforme vem abaixo.
					}
					catch(HibernateException e)
					{	if (tx != null)
						{	try
							{	tx.rollback();
								System.out.println("2 --> ROLLBACK!!!");
			
								/*
								 * Se o insert funcionar e o update falhar em
								 * fun��o do nome ser duplicado, entrar� aqui.
								 * 
								 * Se aqui for efetuado um tx.commit() o 
								 * insert ser� comitado. Para que o insert
								 * n�o aconte�a � preciso efetuar um rollback.
								 *
								 * Se aqui n�o for feito nada, isto �, nem 
								 * commit e nem rollback, o comportamento 
								 * ser� o seguinte: se o programa encerrar 
								 * sem efetuar um close no SessionFactory, 
								 * ser� feito um rollback.
								 *
								 * Se o programa encerrar efetuando um close
								 * no SessionFactory ou se ele continuar,
								 * abrindo outra sess�o, ser� efetuado um
								 * commit.
								 *
								 */
							}
							catch(HibernateException he)
							{ }
						}
					    
						System.out.println('\n' + "A linha nao foi inserida uma " +
						                   "vez que o metodo tx.rollback() foi " +
						                   "executado.");
						throw e;  // Ao propagarmos esta Excecao a execu��o
								  // ser� encerrada. 
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
					
					try
					{	String nome = Console.readLine('\n' + "Informe o nome: ");
						double salario = Console.readDouble('\n' + "Informe o salario: ");
					
						Cliente umCliente = new Cliente(nome, salario);
			
						sessao = FabricaDeSessoes.criarSessao();
						tx = sessao.beginTransaction();
			
						sessao.save(umCliente);
			
						String outroNome = Console.readLine('\n' + "Informe outro nome: ");
						
						umCliente.setNome(outroNome);
					
					    tx.commit();
					}
					catch(HibernateException e)
					{	if (tx != null)
						{	try
							{	// Comitando para a inclus�o acontecer.
								tx.commit();
							}
							catch(HibernateException he)
							{ }
						}
						
						System.out.println('\n' + "A linha foi inserida uma " +
						                   "vez que o metodo tx.commit() foi " +
						                   "executado.");

						// Agora a excecao � esperada e por este motivo
						// a transa��o est� sendo comitada mesmo se o
						// erro acontecer. 
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
				{	Session sessao = null;
					Transaction tx = null;
					
					try
					{	String nome = Console.readLine('\n' + "Informe o nome: ");
						double salario = Console.
									readDouble('\n' + "Informe o salario: ");
					
						Cliente umCliente = new Cliente(nome, salario);
			
						sessao = FabricaDeSessoes.criarSessao();
						tx = sessao.beginTransaction();
			
						sessao.save(umCliente);
						sessao.flush();
						
						// Agora, se a inser��o falhar o outro nome abaixo 
						// nao ser� solicitado. Isso ajuda na depura��o e 
						// evita que o usu�rio tenha que informar dados 
						// in�teis caso um erro aconte�a. 
						
						String outroNome = Console
								.readLine('\n' + "Informe outro nome: ");
						
						umCliente.setNome(outroNome);
					
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
						
						System.out.println 
							('\n' + "************>> Erro ocorrido: " + e.getMessage());
						
						if (e.getCause() != null)
						{	System.out.println ('\n' + "************>> Causa do erro: " + 
									e.getCause().getMessage());
						}
						
						/* 
						 * Quando a exce��o � criada, ela � criada com uma 
						 * mensagem e com uma causa, assim: 
						 * throw new HibernateException("mensagem", causa);
						 * onde "causa" representa o objeto do tipo SQLException 
						 * que fez com que a exce��o HibernateException fosse 
						 * propagada.
						 */
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

				case 5:
					FabricaDeSessoes.fecharFabricaDeSessoes();
					continua = false;
					break;

				default:
					System.out.println('\n' + "Opcao invalida!");						
			}
		}		
	}
}
