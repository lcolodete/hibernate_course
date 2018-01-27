package exercicio23;

import corejava.Console;
import java.util.List;

public class Principal
{	public static void main (String[] args)
	{	
		ClienteAppService clienteAppService = new ClienteAppService();
	
		String nome;
		double salario;
		Cliente umCliente;
				
		boolean continua = true;
		while (continua)
		{	System.out.println('\n' + "O que você deseja fazer?");
/* ==> */	System.out.println('\n' + "1. Cadastrar um cliente");
/* ==> */	System.out.println("2. Alterar um cliente");
			System.out.println("3. Remover um cliente");
/* ==> */	System.out.println("4. Listar todos os clientes");
			System.out.println("5. Sair");
						
			int opcao = Console.readInt('\n' + "Digite um numero entre 1 e 5:");			
				
			switch (opcao)
			{	case 1:
					nome = Console.readLine('\n' + "Digite o nome do cliente: ");
					salario = Console.readDouble("Digite o salario do cliente: ");

					System.out.println('\n' + "Informe o endereço residencial" + '\n');

					String rua = Console.readLine("Informe a rua: ");
					String numero = Console.readLine("Informe o numero: ");
					String complemento = Console.readLine("Informe o complemento: ");

          Endereco enderecoRes = new Endereco(rua, numero, complemento);

					System.out.println('\n' + "Informe o endereço comercial" + '\n');

					// Scanner sc = new Scanner(System.in);
					// rua = sc.nextLine();
					//   TAMBEM RETORNA VAZIO SE APENAS DIGITARMOS ENTER
					
					rua = Console.readLine("Informe a rua: "); // Retorna String vazio com enter.
															   // Isto é, não retorna null.
					numero = Console.readLine("Informe o numero: ");
					complemento = Console.readLine("Informe o complemento: ");

					rua = "".equals(rua) ? null : rua;		   // Para atribuir null
					numero = "".equals(numero) ? null : numero;		
					complemento = "".equals(complemento) ? null : complemento;	
					
					Endereco enderecoCom = new Endereco(rua, numero, complemento);

					// Cria o objeto cliente
					umCliente = new Cliente(nome, salario, enderecoRes, enderecoCom);

					long id = clienteAppService.inclui(umCliente);

					System.out.println('\n' + "Cliente numero " + 
						    id + " incluido com sucesso!");						

					break;
				
				case 2:
				{	int resposta = Console.readInt('\n' + 
						"Digite o numero do cliente que voce deseja alterar: ");
										
					try
					{	umCliente = clienteAppService
							.recuperaUmCliente(resposta);								
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					Principal.exibeCliente(umCliente);										
																						
					System.out.println('\n' + "O que voce deseja alterar?");
					System.out.println('\n' + "1. Nome");
					System.out.println("2. Salario");
/* ==> */			System.out.println("3. Rua do endereço residencial");

					int opcaoAlteracao = Console.readInt('\n' + 
											"Digite um numero de 1 a 3:");			
				
					switch (opcaoAlteracao)
					{	case 1:
							String novoNome = Console.readLine("Digite o novo nome: ");
							umCliente.setNome(novoNome);
							clienteAppService.altera(umCliente);
							System.out.println('\n' + "Alteracao de nome efetuada com sucesso!");						
							
							break;

						case 2:
							double novoSalario = Console.readDouble("Digite o novo salario: ");
							umCliente.setSalario(novoSalario);
							clienteAppService.altera(umCliente);
							System.out.println('\n' + "Alteracao de salario efetuada com sucesso!");						
							
							break;

						case 3:
/* ==> */					String novaRua = Console.readLine("Digite a nova rua do endereco residencial: ");
							Endereco endRes = umCliente.getEnderecoResidencial();
							
							//se todos os campos que compõem o endereço são null, então o endereço também é null
/* ==> */					if (endRes == null)
							{	endRes = new Endereco();
								// Atribui o endereco residencial ao cliente
                                umCliente.setEnderecoResidencial(endRes);								
							}

							endRes.setRua(novaRua);
							
							//ficou com preguiça de solicitar os outros dados do endereço

							clienteAppService.altera(umCliente);
							System.out.println('\n' + 
								"Alteracao de rua (res) efetuada com sucesso!");						
							
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

					Principal.exibeCliente(umCliente);										
																						
					String resp = Console.readLine('\n' + 
							"Confirma a remocao do cliente?");

					if(resp.equals("s"))
					{	clienteAppService.exclui (umCliente);
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
				{	List arrayClientes = clienteAppService.recuperaClientes();
									
					if (arrayClientes.size() == 0)
					{	System.out.println('\n' + "Nao ha clientes cadastrados.");
						break;
					}
										
					System.out.println("");
					int i;
					for (i = 0; i < arrayClientes.size(); i++)
					{	umCliente = (Cliente)arrayClientes.get(i);
/* ==> */				Principal.exibeCliente(umCliente);										
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

	private static void exibeCliente(Cliente umCliente)
	{
		String ruaRes;
		String numeroRes;
		String complementoRes;
		
		String ruaCom;
		String numeroCom;
		String complementoCom;
					
		Endereco endRes = umCliente.getEnderecoResidencial();
		
		if (endRes == null)
		{	ruaRes = "";
			numeroRes = "";
			complementoRes = "";
		}
		else
		{	ruaRes = endRes.getRua() == null ? "" : endRes.getRua();
			numeroRes = endRes.getNumero() == null ? "" : endRes.getNumero();
			complementoRes = endRes.getComplemento() == null ? "" : endRes.getComplemento();
		}	
		
		Endereco endCom = umCliente.getEnderecoComercial();
		
		if (endCom == null) 		// Se no banco de dados a rua, o numero e o 
		{	ruaCom = "";			// complemento do endereco comercial forem 
			numeroCom = ""; 		// nulos, ao ser recuperado o objeto cliente, 
			complementoCom = "";	// o método getEnderecoComercial() retornará
		}							// null.
		else
		{	ruaCom = endCom.getRua() == null ? "" : endCom.getRua();
			numeroCom = endCom.getNumero() == null ? "" : endCom.getNumero();
			complementoCom = endCom.getComplemento() == null ? "" : endCom.getComplemento();
		}
		
		System.out.println('\n' +
		  "Numero = " + umCliente.getNumero() + '\n' +
		  "  Nome = " + umCliente.getNome() + '\n' +
		  "  Salario = " + umCliente.getSalario() + '\n' +
		  "  Rua (res) = "  + ruaRes + '\n' +
		  "    numero = "  + numeroRes + '\n' +
		  "    complemento = " + complementoRes + '\n' +
		  "  Rua (com) = " + ruaCom + '\n' +
		  "    numero = " + numeroCom + '\n' +
		  "    complemento = " + complementoCom);
	}
}
