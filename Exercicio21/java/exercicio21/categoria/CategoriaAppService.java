package exercicio21.categoria;

import org.hibernate.HibernateException;

import exercicio21.util.AplicacaoException;
import exercicio21.util.HibernateUtil;
import exercicio21.util.InfraestruturaException;
import exercicio21.util.ObjetoNaoEncontradoException;

import java.util.List;
import java.util.Set;
import java.util.Iterator;

public class CategoriaAppService
{	
	private static CategoriaDAO categoriaDAO = new CategoriaDAO();

	public long inclui(Categoria umaCategoria) 
	{	try
		{	HibernateUtil.beginTransaction();

			long numero = categoriaDAO.inclui(umaCategoria);

			HibernateUtil.commitTransaction();
			
			return numero;
		} 
		catch(InfraestruturaException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

		    throw e;
		}
		finally
		{   try
		    {	HibernateUtil.closeSession();
		    }
		    catch(InfraestruturaException e)
		    {	throw e;
		    }
		}
	}

	public long incluiCategoriaComoSubCategoria(Categoria umaCategoria, 
	                                            long idCategoriaPai) 
		throws AplicacaoException
	{	try
		{	HibernateUtil.beginTransaction();

			Categoria categoriaPai = categoriaDAO.
				recuperaUmaCategoria(idCategoriaPai);

			// atribui a categoria pai de umaCategoria			
			umaCategoria.setCategoriaPai(categoriaPai);

			categoriaDAO.inclui(umaCategoria);
			
			HibernateUtil.commitTransaction();
			
			return umaCategoria.getId();
		} 
		catch(ObjetoNaoEncontradoException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

			throw new AplicacaoException("Categoria pai nao encontrada");
		}
		catch(InfraestruturaException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

		    throw e;
		}
		finally
		{   try
		    {  HibernateUtil.closeSession();
		    }
		    catch(InfraestruturaException e)
		    {	throw e;
		    }
		}
	}

	public void altera(Categoria umaCategoria) 
	{	try
		{	HibernateUtil.beginTransaction();

			categoriaDAO.altera(umaCategoria);

			HibernateUtil.commitTransaction();
		} 
		catch(InfraestruturaException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

		    throw e;
		}
		finally
		{   try
		    {	HibernateUtil.closeSession();
		    }
		    catch(InfraestruturaException e)
		    {	throw e;
		    }
		}
	}

	public void exclui(long numero) throws AplicacaoException
	{	try
		{	HibernateUtil.beginTransaction();

			Categoria umaCategoria = categoriaDAO.
				recuperaUmaCategoriaESubCategorias(numero);

			if(umaCategoria.getSubCategorias().size() > 0)
			{	throw new AplicacaoException(1, "Esta categoria possui " +
				                                "subcategorias e nao pode " +
				                                "ser removida");
			}

			categoriaDAO.exclui(umaCategoria);

			HibernateUtil.commitTransaction();
		} 
		catch(ObjetoNaoEncontradoException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

		    throw new AplicacaoException("Categoria nao encontrado");
		}
		catch(AplicacaoException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

			throw e;
		}
		catch(InfraestruturaException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

		    throw e;
		}
		finally
		{   try
		    {  HibernateUtil.closeSession();
		    }
		    catch(HibernateException e)
		    {	throw e;
		    }
		}
	}

	public boolean adicionaSubCategoriaAUmaCategoria(long idSubCategoria, 
							                      long idCategoria)
		throws AplicacaoException
	{	try
		{	HibernateUtil.beginTransaction();

			Categoria subCategoria;
			Categoria categoria;

			try
			{	subCategoria = categoriaDAO.
					recuperaUmaCategoria(idSubCategoria);
			}
			catch(ObjetoNaoEncontradoException e)
			{	throw new AplicacaoException("Subcategoria nao encontrada");
			}

			try
			{	categoria = categoriaDAO.
					recuperaUmaCategoria(idCategoria);
			}
			catch(ObjetoNaoEncontradoException e)
			{	throw new AplicacaoException("Categoria nao encontrada");
			}

			/* Este método impede que a categoria A1 que tem a categoria B1 
			 * como subcategoria, seja seja adicionado como subcategoria de B1. 
			 * E impede também que A1 seja adicionado como subcategoria dele
			 * próprio.
			 */

			if (pesquisa(subCategoria, idCategoria))
			{	throw new AplicacaoException("Subcategoria invalida.");
			}
			
			// Com esta solução corre-se o risco de uma categoria ser designada
			// mais de uma vez como categoria pai de um subCategoria.
			if(subCategoria.getCategoriaPai() != null && 
			   subCategoria.getCategoriaPai().getId() == categoria.getId())
			{	HibernateUtil.rollbackTransaction();
				return false;
			}
			else
			{	subCategoria.setCategoriaPai(categoria); 
				HibernateUtil.commitTransaction();
				return true;
			}
		} 
		catch(AplicacaoException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

			throw e;
		}
		catch(InfraestruturaException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

		    throw e;
		}
		finally
		{   try
		    {	HibernateUtil.closeSession();
		    }
		    catch(HibernateException e)
		    {	throw e;
		    }
		}
	}
	
	private boolean pesquisa(Categoria cat, long idCategoria) throws AplicacaoException
	{	// Retorna true se cat tiver como subacategoria a categoria representada 
		// por idCategoria ou se cat.id = idCategoria.
	
		if (cat.getId() == idCategoria)
		{	return true; 
		}
		else
		{	// Recupera as subcategorias de cat
			Set subCategorias = categoriaDAO.recuperaSubCategorias(cat.getId());
			
			boolean retorno = false;
					
			for (Iterator it = subCategorias.iterator(); it.hasNext();)
			{	Categoria subCategoria = (Categoria) it.next();
				retorno = pesquisa (subCategoria, idCategoria);
				
				if(retorno) break;
			}
			return retorno;
		}
	}
			
	public boolean removeSubCategoriaDeCategoria(long idCategoria, 
				                                 long idSubCategoria)
		throws AplicacaoException
	{	try
		{	HibernateUtil.beginTransaction();

			Categoria subCategoria;
			
			try
			{	categoriaDAO.recuperaUmaCategoria(idCategoria);
			}
			catch(ObjetoNaoEncontradoException e)
			{	throw new AplicacaoException("Categoria nao encontrada");
			}

			try
			{	subCategoria = categoriaDAO.
					recuperaUmaCategoria(idSubCategoria);
			}
			catch(ObjetoNaoEncontradoException e)
			{	throw new AplicacaoException("Subcategoria nao encontrada");
			}

			if((subCategoria.getCategoriaPai()!= null) && 
			   (subCategoria.getCategoriaPai().getId() == idCategoria))
			{	subCategoria.setCategoriaPai(null);
				HibernateUtil.commitTransaction();
				return true;
			}
			else
			{	HibernateUtil.rollbackTransaction();
				return false;
			}
		} 
		catch(AplicacaoException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

			throw e;
		}
		catch(InfraestruturaException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

		    throw e;
		}
		finally
		{   try
		    {	HibernateUtil.closeSession();
		    }
		    catch(HibernateException e)
		    {	throw e;
		    }
		}
	}

	public Categoria recuperaUmaCategoria(long numero) 
		throws AplicacaoException
	{	try
		{	HibernateUtil.beginTransaction();

			Categoria umaCategoria = categoriaDAO.recuperaUmaCategoria(numero);

			HibernateUtil.commitTransaction();
			
			return umaCategoria;
		} 
		catch(ObjetoNaoEncontradoException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }
			
			throw new AplicacaoException("Categoria nao encontrada.");
		}
		catch(InfraestruturaException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

		    throw e;
		}
		finally
		{   try
		    {  HibernateUtil.closeSession();
		    }
		    catch(InfraestruturaException e)
		    {	throw e;
		    }
		}
	}

	public Categoria recuperaUmaCategoriaESubCategorias(long numero) 
		throws AplicacaoException
	{	try
		{	HibernateUtil.beginTransaction();

			Categoria umaCategoria = categoriaDAO.
				recuperaUmaCategoriaESubCategorias(numero);

			HibernateUtil.commitTransaction();
			
			return umaCategoria;
		} 
		catch(ObjetoNaoEncontradoException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }
			
			throw new AplicacaoException("Categoria nao encontrada.");
		}
		catch(InfraestruturaException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

		    throw e;
		}
		finally
		{   try
		    {  HibernateUtil.closeSession();
		    }
		    catch(InfraestruturaException e)
		    {	throw e;
		    }
		}
	}

	public List recuperaCategorias()
	{	try
		{	HibernateUtil.beginTransaction();

			List categorias = categoriaDAO.recuperaCategorias();

			HibernateUtil.commitTransaction();
			
			return categorias;
		} 
		catch(InfraestruturaException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

		    throw e;
		}
		finally
		{   try
		    {	HibernateUtil.closeSession();
		    }
		    catch(InfraestruturaException e)
		    {	throw e;
		    }
		}
	}

	public Set recuperaCategoriasESubCategorias()
	{	try
		{	HibernateUtil.beginTransaction();

			Set categorias = categoriaDAO.recuperaCategoriasESubCategorias();

			HibernateUtil.commitTransaction();
			
			return categorias;
		} 
		catch(InfraestruturaException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

		    throw e;
		}
		finally
		{   try
		    {	HibernateUtil.closeSession();
		    }
		    catch(InfraestruturaException e)
		    {	throw e;
		    }
		}
	}
}