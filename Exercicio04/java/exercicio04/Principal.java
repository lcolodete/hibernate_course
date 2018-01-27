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
					
						// Conceito de objeto transiente: existe em memória, 
						// mas ainda não foi persistido.
					
						Cliente umCliente = new Cliente(nome, salario);
			
						sessao = FabricaDeSessoes.criarSessao();
						tx = sessao.beginTransaction();
			
						// O método save() agenda a emissão do comando SQL 
						// INSERT e o objeto umCliente se torna persistente. 
						// Como o objeto umCliente passa a ser persistente 
						// após a emissão do método save(), se após sua 
						// emissão o objeto umCliente for alterado, esta 
						// alteração será persistida ao final da transação 
						// com um comando SQL UPDATE.
						
						sessao.save(umCliente);
						
						System.out.println('\n' + "umCliente.getNumero() = " + 
						                   umCliente.getNumero());
			
						/* 
					 	 * Observe que a alteração do nome abaixo faz com que
					 	 * seja emitido um comando update após o insert, na hora do
					 	 * commit.
					 	 *
					 	 */
			
						String outroNome = Console.readLine('\n' + "Informe outro nome: ");
						
						umCliente.setNome(outroNome);
					
					    tx.commit();

						// Como a tabela CLIENTE deste exercício possui uma 
						// constraint UNIQUE para a coluna NOME, observe
						// que se cadastrarmos dois clientes com o mesmo 
						// nome, a exceção só será propagada quando o método
						// tx.commit() for executado, pois apenas neste 
						// momento os comandos SQL serão emitidos. Se o 
						// segundo comando SQL falhar a transação não sofre 
						// rollback automático: é preciso efetuar o rollback
						// ao capturar a exceção, conforme vem abaixo.
					}
					catch(HibernateException e)
					{	if (tx != null)
						{	try
							{	tx.rollback();
								System.out.println("2 --> ROLLBACK!!!");
			
								/*
								 * Se o insert funcionar e o update falhar em
								 * função do nome ser duplicado, entrará aqui.
								 * 
								 * Se aqui for efetuado um tx.commit() o 
								 * insert será comitado. Para que o insert
								 * não aconteça é preciso efetuar um rollback.
								 *
								 * Se aqui não for feito nada, isto é, nem 
								 * commit e nem rollback, o comportamento 
								 * será o seguinte: se o programa encerrar 
								 * sem efetuar um close no SessionFactory, 
								 * será feito um rollback.
								 *
								 * Se o programa encerrar efetuando um close
								 * no SessionFactory ou se ele continuar,
								 * abrindo outra sessão, será efetuado um
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
						throw e;  // Ao propagarmos esta Excecao a execução
								  // será encerrada. 
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
							{	// Comitando para a inclusão acontecer.
								tx.commit();
							}
							catch(HibernateException he)
							{ }
						}
						
						System.out.println('\n' + "A linha foi inserida uma " +
						                   "vez que o metodo tx.commit() foi " +
						                   "executado.");

						// Agora a excecao é esperada e por este motivo
						// a transação está sendo comitada mesmo se o
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
						
						// Agora, se a inserção falhar o outro nome abaixo 
						// nao será solicitado. Isso ajuda na depuração e 
						// evita que o usuário tenha que informar dados 
						// inúteis caso um erro aconteça. 
						
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
						 * Quando a exceção é criada, ela é criada com uma 
						 * mensagem e com uma causa, assim: 
						 * throw new HibernateException("mensagem", causa);
						 * onde "causa" representa o objeto do tipo SQLException 
						 * que fez com que a exceção HibernateException fosse 
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
