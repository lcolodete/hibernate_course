package exercicio27;

import corejava.*;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;

public class Principal
{	public static void main (String[] args) 
	{	
		PagamentoAppService pagamentoAppService = new PagamentoAppService();

		boolean continua = true;
		while (continua)
		{	System.out.println('\n' + "O que voc� deseja fazer?");
			System.out.println('\n' + "1. Cadastrar um pagamento");
			System.out.println("2. Alterar um pagamento");
			System.out.println("3. Remover um pagamento");
/* ==> */	System.out.println("4. Recupera um pagamento efetuado atraves de cartao");
/* ==> */	System.out.println("5. Recupera um pagamento sem saber se foi feito por cartao ou conta");
			System.out.println("6. Listar todos os pagamentos em Cartao");
			System.out.println("7. Listar todos os pagamentos");
			System.out.println("8. Sair");
						
			int opcao = Console.readInt('\n' + "Digite um numero entre 1 e 8:");
					
			switch (opcao)
			{	case 1:
				{
					double valor = Console.
						readDouble('\n' + "Informe o valor do pagamento: ");
					String data = Console.
						readLine("Informe a data do pagamento: ");

					Date dataPagto = Util.strToDate(data);
					
					System.out.println('\n' + "Como o pagamento foi efetuado?");
					System.out.println('\n' + "1. Cartao de credito");
					System.out.println("2. Cheque");

					int resposta = Console.readInt('\n' + "Digite 1 ou 2:");			
		
					Pagamento pagamento = null;
					
					switch (resposta)
					{	case 1:
						{
							String numero = Console.
								readLine("Informe o numero do cartao: ");
							String mesExp = Console.
								readLine("Informe o mes de expiracao do cartao: ");
							String anoExp = Console.
								readLine("Informe o ano de expiracao do cartao: ");
	                    
	                    	pagamento = new Cartao(valor, 
	                    	                       dataPagto, 
	                    	                       numero, 
	                    	                       mesExp, 
	                    	                       anoExp);
	                    	break;
						}

						case 2:
						{	String banco = Console.readLine("Informe o banco: ");
							String agencia = Console.readLine("Informe a agencia: ");
							String conta = Console.readLine("Informe a conta: ");
	                    
	                    	pagamento = new Conta(valor, 
	                    	                      dataPagto, 
	                    	                      banco, 
	                    	                      agencia, 
	                    	                      conta);
	                    	break;
						}

						default:
						{	System.out.println('\n' + "Opcao de pagamento inv�lida.");
						}
					}
					
					if (resposta == 1 || resposta == 2)
					{	long id = pagamentoAppService.inclui(pagamento);

						System.out.println('\n' + "Pagamento numero " + 
						    id + " incluido com sucesso!");						
					}						

					break;
				}

				case 2:
				{
					long id = Console.readInt('\n' + 
						"Informe o numero do pagamento que voce deseja alterar: ");
					
					Pagamento umPagamento;
					
					try
					{	umPagamento = pagamentoAppService.recuperaUmPagamento(id);
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
						
					if(umPagamento instanceof Conta)
					{	
						System.out.println(	'\n' + 
							"Pagamento numero = "  + umPagamento.getId() + 
						   	"  Valor = "  + umPagamento.getValor() +  
							"  Banco = "  + ((Conta)umPagamento).getBanco());
					
						System.out.println('\n' + "O que voc� deseja alterar?");
						
						System.out.println('\n' + "1. Valor");
						System.out.println("2. Banco");
						
						int opcaoAlteracao = Console.readInt('\n' + "Digite 1 ou 2:");			
				
						switch (opcaoAlteracao)
						{	case 1:
								double novoValor = Console.readDouble
									('\n' + "Digite o novo valor: ");
									
								((Conta)umPagamento).setValor(novoValor);

								pagamentoAppService.altera(umPagamento);

								System.out.println('\n' + 
									"Alteracao de valor efetuada " +
									"com sucesso!");						

								break;

							case 2:
								String novoBanco = Console.readLine
									('\n' + "Digite o novo banco: ");
									
								((Conta)umPagamento).setBanco(novoBanco);

								pagamentoAppService.altera(umPagamento);

								System.out.println('\n' + 
									"Alteracao de conta efetuada " +
									"com sucesso!");						

								break;

							default:
								System.out.println('\n' + "Op��o inv�lida!");
						}
					}
					else	
					{	
						System.out.println(	'\n' + 
							"Pagamento numero = "  + umPagamento.getId() + 
						   	"  Valor = "  + umPagamento.getValor() +  
							"  Numero do Cartao = "  + 
							   ((Cartao)umPagamento).getNumero());
						
						System.out.println('\n' + "O que voc� deseja alterar?");

						System.out.println('\n' + "1. Valor");
						System.out.println("2. Numero do cartao");
						
						int opcaoAlteracao = Console.readInt('\n' + "Digite 1 ou 2:");			
				
						switch (opcaoAlteracao)
						{	case 1:
								double novoValor = Console.
									readDouble("Digite o novo valor: ");
									
								((Cartao)umPagamento).setValor(novoValor);

								pagamentoAppService.altera(umPagamento);

								System.out.println('\n' + 
									"Alteracao de valor efetuada " +
									"com sucesso!");						
								
								break;

							case 2:
								String novoNumero = Console.
									readLine("Digite o novo numero do cartao: ");
									
								((Cartao)umPagamento).setNumero(novoNumero);

								pagamentoAppService.altera(umPagamento);

								System.out.println('\n' + 
									"Alteracao de numero do cartao " +
									"efetuada com sucesso!");						
								
								break;

							default:
								System.out.println('\n' + "Op��o inv�lida!");
															
						}
					}
					
					break;						
				}

				case 3:
				{	int resposta = Console.readInt('\n' + 
						"Digite o numero do pagamento que voce deseja remover: ");
									
					Pagamento umPagamento;
					
					try
					{	umPagamento = pagamentoAppService
							.recuperaUmPagamento(resposta);
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' +	
					  "Numero = " + umPagamento.getId() + 
					  "  Valor = " + umPagamento.getValor());
																					
					String resp = Console.readLine('\n' + 
						"Confirma a remocao do pagamento?");

					if(resp.equals("s"))
					{	pagamentoAppService.exclui (umPagamento);
						
						System.out.println('\n' + "Pagamento removido com sucesso!");
					}
					else
					{	System.out.println('\n' + 
							"Pagamento nao removido.");
					}
					
					break;
				}
	
				case 4:
				{	
					int resposta = Console.readInt('\n' + 
						"Digite o numero do pagamento em cartao que voce deseja recuperar: ");
				
					Cartao pagamentoEmCartao;
					
					try
					{	pagamentoEmCartao = pagamentoAppService
							.recuperaUmPagamentoEmCartao(resposta);
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
				
					System.out.println(	'\n' + 
						"Pagamento numero = "  + pagamentoEmCartao.getId() + 
						"  Valor = " + pagamentoEmCartao.getValor() + 
						"  Numero do Cartao = " + pagamentoEmCartao.getNumero());
										
					break;
				}

				case 5:
				{	
					int idPagamento = Console.readInt('\n' + 
						"Digite o numero do pagamento (que nao se sabe se foi " +
						"efetuado por cartao ou conta) a recuperar: ");
				
					// Utiliza no comando SQL "UNION ALL" que n�o elimina as 
					// linhas duplicadas. 

					Pagamento pagamento;
					
					try
					{	pagamento = pagamentoAppService
							.recuperaUmPagamento(idPagamento);
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
				
					if (pagamento instanceof Conta)
					{	System.out.println(	'\n' + 
							"Pagamento numero = "  + pagamento.getId() + 
							"  Valor = " + pagamento.getValor() + 
							"  Banco = " + ((Conta)pagamento).getBanco());
					}
					else
					{	System.out.println(	'\n' + 
							"Pagamento numero = "  + pagamento.getId() + 
							"  Valor = " + pagamento.getValor() + 
							"  Numero do Cartao = " + 
								((Cartao)pagamento).getNumero());
					}
										
					break;
				}

				case 6:
				{	List pagamentos = pagamentoAppService
						.recuperaPagamentosEmCartao();
									
					if (pagamentos.size() == 0)
					{	System.out.println('\n' + 
							"Nao ha pagamentos cadastrados.");
						break;
					}
										
					System.out.println("");
					for (Iterator iter = pagamentos.iterator(); iter.hasNext(); )
					{	Pagamento pagamento = (Pagamento) iter.next();
	
						System.out.println(	'\n' + 
							"Pagamento numero = "  + pagamento.getId() + 
							"  Valor = " + pagamento.getValor() + 
							"  Numero do Cartao = " + 
								((Cartao)pagamento).getNumero());
					}
										
					break;
				}
	
				case 7:
				{	List pagamentos = pagamentoAppService
						.recuperaPagamentos();
									
					if (pagamentos.size() == 0)
					{	System.out.println('\n' + 
							"Nao ha pagamentos cadastrados.");
						break;
					}
										
					System.out.println("");
					for (Iterator iter = pagamentos.iterator(); iter.hasNext(); )
					{	Pagamento pagamento = (Pagamento) iter.next();
	
						if (pagamento instanceof Conta)
						{	System.out.println(	'\n' + 
								"Pagamento numero = "  + pagamento.getId() + 
								"  Valor = " + pagamento.getValor() + 
								"  Banco = " + ((Conta)pagamento).getBanco());
						}
						else
						{	System.out.println(	'\n' + 
								"Pagamento numero = "  + pagamento.getId() + 
								"  Valor = " + pagamento.getValor() + 
								"  Numero do Cartao = " + 
									((Cartao)pagamento).getNumero());
						}
					}
										
					break;
				}
	
				case 8:
				{	continua = false;
					break;
				}
	
				default:
					System.out.println('\n' + "Op��o inv�lida!");						
			}
		}		
	}
}
