package exercicio44.cliente;

import corejava.Console;
import exercicio44.lance.Lance;
import exercicio44.util.*;

import java.util.Set;
import java.util.Iterator;


public class PrincipalCliente
{	public static void main (String[] args)
	{	
		ClienteAppService clienteAppService = new ClienteAppService();
	
		String nome;
		double salario;
		Cliente umCliente;
				
		boolean continua = true;
		while (continua)
		{	System.out.println('\n' + "O que você deseja fazer?");
			System.out.println('\n' + "1. Cadastrar um cliente");
			System.out.println("2. Alterar um cliente");
			System.out.println("3. Remover um cliente");
			System.out.println("4. Listar todos os clientes e seus lances");
			System.out.println("5. Sair");
						
			int opcao = Console.readInt('\n' + "Digite um numero entre 1 e 5:");			
				
			switch (opcao)
			{	case 1:
					nome = Console.readLine('\n' + 
										"Digite o nome do cliente: ");
					salario = Console.readDouble(
										"Digite o salario do cliente: ");

					umCliente = new Cliente(nome, salario);

					long numero = clienteAppService.inclui(umCliente);

					System.out.println('\n' + "Cliente numero " + 
						    numero + " incluido com sucesso!");						

					break;
				
				case 2:
				{	int resposta = Console.readInt('\n' + 
						"Digite o numero do cliente que voce deseja alterar: ");
										
					try
					{	umCliente = clienteAppService.recuperaUmCliente(resposta);
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"Número = " + umCliente.getNumero() + 
						"    Nome = " + umCliente.getNome() +
						"    Salario = " + umCliente.getSalario());
																						
					System.out.println('\n' + "O que voce deseja alterar?");
					System.out.println('\n' + "1. Nome");
					System.out.println("2. Salario");

					int opcaoAlteracao = Console.readInt('\n' + 
											"Digite um numero de 1 a 2:");			
					
					switch (opcaoAlteracao)
					{	case 1:
							String novoNome = Console.
										readLine("Digite o novo nome: ");

							umCliente.setNome(novoNome);

							clienteAppService.altera(umCliente);

							System.out.println('\n' + 
								"Alteracao de nome efetuada com sucesso!");						

							break;
						case 2:
							double novoSalario = Console.
									readDouble("Digite o novo salario: ");

							umCliente.setSalario(novoSalario);

							clienteAppService.altera(umCliente);

							System.out.println('\n' + 
								"Alteracao de salario efetuada com sucesso!");						
								
							break;

						default:
							System.out.println('\n' + "Opcao invalida!");						
							break;
					}

					break;
				}

				case 3:
				{	int resposta = Console.readInt('\n' + 
						"Digite o numero do cliente que voce deseja remover: ");
									
					try
					{	umCliente = clienteAppService.recuperaUmCliente(resposta);
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"Número = " + umCliente.getNumero() + 
						"    Nome = " + umCliente.getNome() +
						"    Salario = " + umCliente.getSalario());
																						
					String resp = Console.readLine('\n' + 
						"Confirma a remocao do cliente?");

					if(resp.equals("s"))
					{	
						try
						{	clienteAppService.exclui(umCliente);
						
							System.out.println('\n' + 
								"Cliente removido com sucesso!");
						}
						catch(AplicacaoException e)
						{	System.out.println('\n' + e.getMessage());
							break;
						}
						
						System.out.println('\n' + 
							"Cliente removido com sucesso!");
					}
					else
					{	System.out.println('\n' + 
							"Cliente nao removido.");
					}
					
					break;
				}

				case 4:
				{	Set clientes = clienteAppService.recuperaClientesELances();
									
					if (clientes.size() == 0)
					{	System.out.println('\n' + "Nao ha clientes cadastrados.");
						break;
					}
										
					System.out.println("");

					for (Iterator iter = clientes.iterator(); iter.hasNext(); )
					{	umCliente = (Cliente) iter.next();
						System.out.println('\n' +	
						               "Numero = " + umCliente.getNumero() + 
									   "  Nome = " + umCliente.getNome() +
									   "  Salario = " + umCliente.getSalario());
									   
						Set lances = umCliente.getLances();

						for (Iterator it = lances.iterator(); it.hasNext(); )
						{	Lance umLance = (Lance) it.next();
							System.out.println('\n' +	
								"    Numero = " + umLance.getId() + 
								"  Valor = " + umLance.getValor() +
								"  Data de Emissao = " + umLance.getDataCriacao());
						}
					}	
										
					break;
				}

				case 5:
					continua = false;
					break;

				default:
					System.out.println('\n' + "Opcao invalida!");						
			}
		}		
	}
}
