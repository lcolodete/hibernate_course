package exercicio40.clienteContaCartao;

import corejava.*;

import exercicio40.cartao.*;
import exercicio40.cliente.*;
import exercicio40.conta.*;
import exercicio40.util.*;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Principal
{	public static void main (String[] args) 
	{	
		ClienteAppService clienteAppService = new ClienteAppService();
		ContaAppService contaAppService = new ContaAppService();
		CartaoAppService cartaoAppService = new CartaoAppService();
		ClienteContaCartaoAppService clienteContaCartaoAppService = new ClienteContaCartaoAppService();

		boolean continua = true;
		while (continua)
		{	System.out.println('\n' + "O que você deseja fazer?");
			System.out.println('\n' + "1. Cadastrar um cliente, conta e cartao novos");
			System.out.println("2. Cadastrar um novo cartao para um cliente e uma conta");
			System.out.println("3. Cadastrar uma nova conta e cartao para um determinado cliente");
			System.out.println("4. Cadastrar um novo cliente com um novo cartao em determinada conta");
			System.out.println("5. Remover um cliente");
			System.out.println("6. Listar todos os Clientes");
			System.out.println("7. Listar todas as Contas");
			System.out.println("8. Listar todos os Cartoes");
			System.out.println("9. Listar todos os Clientes, Contas e Cartoes");
			System.out.println("10. Sair");
						
			int opcao = Console.readInt('\n' + "Digite um numero entre 1 e 10:");			
					
			switch (opcao)
			{	
				case 1:
				{
					String nome  = Console
						.readLine('\n' + "Informe o nome do cliente: ");
					Cliente umCliente = new Cliente(nome);
					
					Date dataAbertura = Util.strToDate(Console
							.readLine("Informe a data de abertura da conta: "));
					Conta umaConta = new Conta(dataAbertura);
							
					Date dataEmissao = Util.strToDate(Console
							.readLine("Informe a data de emissao do cartao: "));
					Cartao umCartao = new Cartao(dataEmissao);

					clienteAppService.inclui(umCliente);
					contaAppService.inclui(umaConta);
					cartaoAppService.inclui(umCartao);
						
					ClienteContaCartao ccc = new ClienteContaCartao(umCliente,
					                                                umaConta,
					                                                umCartao);

/* ==> */			clienteContaCartaoAppService.inclui(ccc);

					System.out.println('\n' + "Cliente " + umCliente.getId() +
					                          " Conta " + umaConta.getId() + 
					                          " Cartao " + umCartao.getId() +
					                          " cadastrados com sucesso.");
					
					break;
				}

				case 2:
				{	// Cadastrar um novo cartao para um cliente e uma conta	
				
					int idCliente = Console.readInt('\n' + 
						"Digite o numero do cliente: ");
					int idConta = Console.readInt('\n' + 
						"Digite o numero da conta: ");
										
/* ==> */			List cccs = clienteContaCartaoAppService.
							recuperaClienteContaCartoes(idCliente, idConta);								

					if(cccs.size() == 0)
					{	System.out.println('\n' + "O cliente " + idCliente + 
					                              " nao possui a conta " +
					                              idConta);
						break;
					}
										
					Date dataEmissao = Util.strToDate(Console
							.readLine("Informe a data de emissao do cartao: "));
/* ==> */			Cartao umCartao = new Cartao(dataEmissao);

/* ==> */			cartaoAppService.inclui(umCartao);

/* ==> */			Cliente umCliente = ((ClienteContaCartao)cccs.get(0)).getCliente();
/* ==> */			Conta umaConta = ((ClienteContaCartao)cccs.get(0)).getConta();
						
/* ==> */			ClienteContaCartao ccc = 
						new ClienteContaCartao(umCliente, umaConta, umCartao);

/* ==> */			clienteContaCartaoAppService.inclui(ccc);

					System.out.println('\n' + "Clinte " + umCliente.getId() +
					                          " Conta " + umaConta.getId() + 
					                          " Cartao " + umCartao.getId() +
					                          " cadastrado com sucesso.");
								
					break;
				}

				case 3: // Cadastrar uma nova conta e cartao para um determinado cliente
				{	int idCliente = Console.readInt('\n' + 
						"Digite o numero do cliente: ");
										
					Cliente umCliente;
					
					try
/* ==> */			{	umCliente = clienteAppService.
							recuperaUmCliente(idCliente);								
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					Date dataAbertura = Util.strToDate(Console.readLine("Informe a data de abertura da conta: "));
/* ==> */			Conta umaConta = new Conta(dataAbertura);
							
					Date dataEmissao = Util.strToDate(Console.readLine("Informe a data de emissao do cartao: "));
/* ==> */			Cartao umCartao = new Cartao(dataEmissao);

/* ==> */			contaAppService.inclui(umaConta);
/* ==> */			cartaoAppService.inclui(umCartao);
						
/* ==> */			ClienteContaCartao ccc = 
						new ClienteContaCartao(umCliente, umaConta, umCartao);

/* ==> */			clienteContaCartaoAppService.inclui(ccc);

					System.out.println('\n' + "Clinte " + umCliente.getId() +
					                          " Conta " + umaConta.getId() + 
					                          " Cartao " + umCartao.getId() +
					                          " cadastrado com sucesso.");
								
					break;
				}

				case 4: // Cadastrar um novo cliente com um novo cartao em determinada conta
				{	int idConta = Console.readInt('\n' + 
						"Digite o numero da conta: ");
										
					Conta umaConta;
					
					try
/* ==> */			{	umaConta = contaAppService.
							recuperaUmaConta(idConta);								
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					String nome  = Console.readLine('\n' + "Informe o nome do cliente: ");
/* ==> */			Cliente umCliente = new Cliente(nome);
					
					Date dataEmissao = Util.strToDate(Console.readLine("Informe a data de emissao do cartao: "));
/* ==> */			Cartao umCartao = new Cartao(dataEmissao);

/* ==> */			clienteAppService.inclui(umCliente);
/* ==> */			cartaoAppService.inclui(umCartao);
						
/* ==> */			ClienteContaCartao ccc = new ClienteContaCartao(umCliente,
					                                                umaConta,
					                                                umCartao);

/* ==> */			clienteContaCartaoAppService.inclui(ccc);

					System.out.println('\n' + "Clinte " + umCliente.getId() +
					                          " Conta " + umaConta.getId() + 
					                          " Cartao " + umCartao.getId() +
					                          " cadastrado com sucesso.");
					
					break;
				}

				case 5: // Remover um cliente
				{	int idCliente = Console.readInt('\n' + 
						"Digite o numero do cliente: ");

					Cliente umCliente;
										
					try
/* ==> */			{	umCliente = clienteAppService.
										recuperaUmCliente(idCliente);
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"Número = " + umCliente.getId() + 
						"    Nome = " + umCliente.getNome());
																						
					String resp = Console.readLine('\n' + 
						"Confirma a remocao do cliente?");

					if(resp.equals("s"))
					{	try
/* ==> */				{	clienteAppService.exclui(umCliente);

							System.out.println('\n' + 
								"Cliente removido com sucesso!");
						}
						catch(AplicacaoException e)
						{	System.out.println('\n' + e.getMessage());
							break;
						}
					}
					else
					{	System.out.println('\n' + "Cliente nao removido.");
					}
					
					break;
				}

				case 6:
				{
					List clientes = clienteAppService.recuperaClientes();
						
					if (clientes.size() != 0)
					{	System.out.println("");

						for (Iterator it = clientes.iterator(); it.hasNext(); )
						{	Cliente umCliente = (Cliente) it.next();
							System.out.println('\n' + 
								"Cliente numero = "  + umCliente.getId() + 
								"  nome = "  + umCliente.getNome());
                    	} 
					}
					else
					{	System.out.println('\n' + 
							"Nao ha Cientes cadastrados.");
					}

					break;
				}

				case 7:
				{
					List contas = contaAppService.recuperaContas();
						
					if (contas.size() != 0)
					{	System.out.println("");

						for (Iterator it = contas.iterator(); it.hasNext(); )
						{	Conta umaConta = (Conta) it.next();
							System.out.println('\n' + 
								"Conta numero = "  + umaConta.getId() + 
								"  data emissao = "  + umaConta.getDataAbertura());
                    	} 
					}
					else
					{	System.out.println('\n' + 
							"Nao ha Contas cadastradas.");
					}

					break;
				}

				case 8:
				{
					List cartoes = cartaoAppService.recuperaCartoes();
						
					if (cartoes.size() != 0)
					{	System.out.println("");

						for (Iterator it = cartoes.iterator(); it.hasNext(); )
						{	Cartao umCartao = (Cartao) it.next();
							System.out.println('\n' + 
								"Cartao numero = "  + umCartao.getId() + 
								"  data emissao = "  + umCartao.getDataEmissao());
                    	} 
					}
					else
					{	System.out.println('\n' + 
							"Nao ha Cartoes cadastrados.");
					}

					break;
				}

				case 9:
				{
					List cccs = clienteContaCartaoAppService.recuperaClienteContaCartoes();
						
					if (cccs.size() != 0)
					{	System.out.println("");

						for (Iterator it = cccs.iterator(); it.hasNext(); )
						{	ClienteContaCartao ccc = (ClienteContaCartao) it.next();
							System.out.println('\n' + 
								"Cliente numero = "  + ccc.getCliente().getId() + 
								"  Conta numero = "  + ccc.getConta().getId() +
								"  Cartao numero = "  + ccc.getCartao().getId());
                    	} 
					}
					else
					{	System.out.println('\n' + 
							"Nao ha Cientes, Contas e Cartoes cadastrados.");
					}

					break;
				}

				case 10:
				{	continua = false;
					break;
				}

				default:
					System.out.println('\n' + "Opção inválida!");						
			}
		}		
	}
}
