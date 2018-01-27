package exercicio21.categoria;

import corejava.*;
import java.util.Set;
import java.util.Iterator;

import exercicio21.util.AplicacaoException;

public class PrincipalCategoria
{	public static void main (String[] args) 
	{	
		String nome;

		Categoria umaCategoria;

		CategoriaAppService categoriaAppService = new CategoriaAppService();

		boolean continua = true;
		while (continua)
		{	System.out.println('\n' + "O que você deseja fazer?");
			System.out.println('\n' + "1. Cadastrar uma categoria");
			System.out.println("");
			System.out.println("2. Cadastrar uma categoria como subcategoria");
			System.out.println("3. Adicionar subcategoria existente a uma categoria");
			System.out.println("4. Remover uma subcategoria de uma categoria");
			System.out.println("");
			System.out.println("5. Remover uma categoria");
			System.out.println("6. Listar uma categoria e suas subcategorias");
			System.out.println("7. Listar todas as categorias");
			System.out.println("");
			System.out.println("8. Sair");
						
			int opcao = Console.readInt('\n' + "Digite um numero entre 1 e 8:");
					
			switch (opcao)
			{	case 1:
				{
					nome = Console.readLine('\n' + 
						"Informe o nome da categoria: ");
						
					umaCategoria = new Categoria(nome);
			
					long numero = categoriaAppService.inclui(umaCategoria);

					System.out.println('\n' + "Categoria numero " + 
						    numero + " incluida com sucesso!");	

					break;
				}

				case 2:
				{
					nome = Console.readLine('\n' + "Informe o nome " +
						"da categoria que voce deseja cadastrar: ");

					long idCategoriaPai = Console.
						readInt("Informe o numero da categoria pai: ");
						
					umaCategoria = new Categoria(nome);

					try
					{	long numero = categoriaAppService.
							incluiCategoriaComoSubCategoria(umaCategoria, 
							                                idCategoriaPai);
						System.out.println('\n' + "Categoria numero " + 
						    numero + " incluida com sucesso!");	
					}
					catch(AplicacaoException e)
					{	System.out.println (e.getMessage());
					}
					
					break;
				}

				case 3:
				{	long idSubCategoria = Console.readInt('\n' + 
									"Informe o numero da subcategoria: ");
					long idCategoria = Console.readInt('\n' + 
									"Informe o numero da categoria: ");

					try
					{	if(categoriaAppService.
							adicionaSubCategoriaAUmaCategoria(idSubCategoria, 
							                                  idCategoria))
						{	System.out.println('\n' + "Subcategoria adicionada " + 
						    						  " com sucesso `a categoria.");
						}
						else
						{	System.out.println('\n' + "Subcategoria já cadastrada");
						}
					}
					catch(AplicacaoException e)
					{	System.out.println (e.getMessage());
					}
					
					break;
				}

				case 4:
				{	int idCategoria = Console.readInt('\n' + 
						"Digite o numero da categoria: ");
									
					int idSubCategoria = Console.readInt('\n' + 
						"Digite o numero da subcategoria que voce deseja " +
						"remover: ");
						
					try
					{	if(categoriaAppService.removeSubCategoriaDeCategoria
							(idCategoria, idSubCategoria))
						{	System.out.println
								("Subcategoria removida com sucesso.");
						}
						else
						{	System.out.println
								("Subcategoria nao encontrada na categoria.");
						}
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + e.getMessage());
					}
					
					break;
				}

				case 5:
				{	int resposta = Console.readInt('\n' + 
						"Digite o numero da categoria que voce deseja remover: ");
									
					try
					{	umaCategoria = categoriaAppService.
											recuperaUmaCategoria(resposta);
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
						
					System.out.println('\n' + 
						"Número = " + umaCategoria.getId() + 
						"    Nome = " + umaCategoria.getNome());
								
					String resp = Console.readLine('\n' + 
						"Confirma a remocao da categoria?");

					if(resp.equals("s"))
					{	try
						{	categoriaAppService.exclui (resposta);
							System.out.println('\n' + 
									"Categoria removida com sucesso!");
						}
						catch(AplicacaoException e)
						{	System.out.println('\n' + e.getMessage());
						}
					}
					else
					{	System.out.println('\n' + "Categoria nao removida.");
					}
					
					break;
				}

				case 6:
				{	
					long numero = Console.readInt(
						"Informe o numero da categoria: ");
				
					try
					{	umaCategoria = categoriaAppService
							.recuperaUmaCategoriaESubCategorias(numero);
					}
					catch(AplicacaoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
					
					System.out.println(	'\n' + 
						"Categoria numero = "  + umaCategoria.getId() + 
						"  Nome = "  + umaCategoria.getNome());

					Set categorias = umaCategoria.getSubCategorias();
					
					System.out.println("");
					for (Iterator it = categorias.iterator(); it.hasNext(); )
					{	Categoria subCategoria = (Categoria) it.next();
						System.out.println(
							"     Subcategoria numero = "  + 
											subCategoria.getId() + 
							"  Nome = "  +  subCategoria.getNome());
					}										

					break;
				}

				case 7:
				{
					Set categorias = categoriaAppService.
							recuperaCategoriasESubCategorias();
						
					if (categorias.size() != 0)
					{	System.out.println("");

						for (Iterator it = categorias.iterator(); 
						              it.hasNext(); )
						{	Categoria categoria = (Categoria) it.next();
							System.out.println('\n' + 
								"Categoria numero = "  + categoria.getId() + 
								"  Nome = "  + categoria.getNome());

							Set subCategorias = categoria.getSubCategorias();
							
							for (Iterator it2 = subCategorias.iterator(); 
							              it2.hasNext();)
							{	Categoria subCategoria = (Categoria) it2.next();
								System.out.println(	'\n' + 
									"      Subcategoria numero = "  + 
										subCategoria.getId() + 
									"  nome = " + subCategoria.getNome());
							}	
                    	} 
					}
					else
					{	System.out.println('\n' + 
							"Nao ha produtos cadastrados com esta descricao.");
					}

					break;
				}

				case 8:
				{	continua = false;
					break;
				}

				default:
					System.out.println('\n' + "Opção inválida!");
			}
		}		
	}
}
