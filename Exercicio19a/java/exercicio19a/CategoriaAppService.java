package exercicio19a;

import org.hibernate.HibernateException;
import java.util.List;
import java.util.Set;
import java.util.Iterator;

public class CategoriaAppService
{
	private static CategoriaDAO categoriaDAO = new CategoriaDAO();

	public long inclui(Categoria umaCategoria)
	{
		try
		{
			HibernateUtil.beginTransaction();

			long numero = categoriaDAO.inclui(umaCategoria);

			HibernateUtil.commitTransaction();

			return numero;
		}
		catch (InfraestruturaException e)
		{
			try
			{
				HibernateUtil.rollbackTransaction();
			}
			catch (InfraestruturaException ie)
			{
			}

			throw e;
		}
		finally
		{
			try
			{
				HibernateUtil.closeSession();
			}
			catch (InfraestruturaException e)
			{
				throw e;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public long incluiCategoriaComoSubCategoria(Categoria umaCategoria,
			long idCategoriaPai) throws AplicacaoException
	{
		try
		{
			HibernateUtil.beginTransaction();

			Categoria categoriaPai = categoriaDAO
					.recuperaUmaCategoria(idCategoriaPai);

			umaCategoria.setCategoriaPai(categoriaPai);
			categoriaPai.getSubCategorias().add(umaCategoria);

			/*
			 * Como a instância categoriaPai é persistente (está vinculada à sessão),
			 * e a associação subCategorias possui salvamento em cascata habilitado,
			 * este código resulta na criação de uma nova Categoria que se tornará
			 * persistente quando a transação for comitada já que o Hibernate executa
			 * em cascata a operação de verificação de sujeira para os filhos de
			 * categoriaPai. Neste caso, o Hibernate irá executar um comando INSERT.
			 */

			HibernateUtil.commitTransaction();

			return umaCategoria.getId();
		}
		catch (ObjetoNaoEncontradoException e)
		{
			try
			{
				HibernateUtil.rollbackTransaction();
			}
			catch (InfraestruturaException ie)
			{
			}

			throw new AplicacaoException("Categoria pai nao encontrada");
		}
		catch (InfraestruturaException e)
		{
			try
			{
				HibernateUtil.rollbackTransaction();
			}
			catch (InfraestruturaException ie)
			{
			}

			throw e;
		}
		finally
		{
			try
			{
				HibernateUtil.closeSession();
			}
			catch (InfraestruturaException e)
			{
				throw e;
			}
		}
	}

	public void altera(Categoria umaCategoria)
	{
		try
		{
			HibernateUtil.beginTransaction();

			categoriaDAO.altera(umaCategoria);

			HibernateUtil.commitTransaction();
		}
		catch (InfraestruturaException e)
		{
			try
			{
				HibernateUtil.rollbackTransaction();
			}
			catch (InfraestruturaException ie)
			{
			}

			throw e;
		}
		finally
		{
			try
			{
				HibernateUtil.closeSession();
			}
			catch (InfraestruturaException e)
			{
				throw e;
			}
		}
	}

	public void exclui(long numero) throws AplicacaoException
	{
		try
		{
			HibernateUtil.beginTransaction();

			Categoria umaCategoria = categoriaDAO
					.recuperaUmaCategoriaESubCategorias(numero);

			if (umaCategoria.getSubCategorias().size() > 0)
			{
				throw new AplicacaoException(1, "Esta categoria possui "
						+ "subcategorias e nao pode " + "ser removida");
			}

			categoriaDAO.exclui(umaCategoria);

			HibernateUtil.commitTransaction();
		}
		catch (ObjetoNaoEncontradoException e)
		{
			try
			{
				HibernateUtil.rollbackTransaction();
			}
			catch (InfraestruturaException ie)
			{
			}

			throw new AplicacaoException("Categoria nao encontrado");
		}
		catch (AplicacaoException e)
		{
			try
			{
				HibernateUtil.rollbackTransaction();
			}
			catch (InfraestruturaException ie)
			{
			}

			throw e;
		}
		catch (InfraestruturaException e)
		{
			try
			{
				HibernateUtil.rollbackTransaction();
			}
			catch (InfraestruturaException ie)
			{
			}

			throw e;
		}
		finally
		{
			try
			{
				HibernateUtil.closeSession();
			}
			catch (HibernateException e)
			{
				throw e;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public boolean adicionaSubCategoriaAUmaCategoria(long idSubCategoria,
			long idCategoria) throws AplicacaoException
	{
		try
		{
			HibernateUtil.beginTransaction();

			Categoria subCategoria;
			Categoria categoria;

			try
			{
				subCategoria = categoriaDAO.recuperaUmaCategoria(idSubCategoria);
			}
			catch (ObjetoNaoEncontradoException e)
			{
				throw new AplicacaoException("Subcategoria nao encontrada");
			}

			try
			{
				categoria = categoriaDAO.recuperaUmaCategoria(idCategoria);
			}
			catch (ObjetoNaoEncontradoException e)
			{
				throw new AplicacaoException("Categoria nao encontrada");
			}

			/*
			 * Este método impede que a categoria A1 que tem a categoria B1 como
			 * subcategoria, seja seja adicionado como subcategoria de B1. E impede
			 * também que A1 seja adicionado como subcategoria dele próprio.
			 */

			if (pesquisa(subCategoria, idCategoria))
			{
				throw new AplicacaoException("Subcategoria invalida.");
			}

			subCategoria.setCategoriaPai(categoria);
			boolean retorno = categoria.getSubCategorias().add(subCategoria);

			if (retorno)
			{
				HibernateUtil.commitTransaction();
			}
			else
			{
				HibernateUtil.rollbackTransaction();
			}

			return retorno;
		}
		catch (AplicacaoException e)
		{
			try
			{
				HibernateUtil.rollbackTransaction();
			}
			catch (InfraestruturaException ie)
			{
			}

			throw e;
		}
		catch (InfraestruturaException e)
		{
			try
			{
				HibernateUtil.rollbackTransaction();
			}
			catch (InfraestruturaException ie)
			{
			}

			throw e;
		}
		finally
		{
			try
			{
				HibernateUtil.closeSession();
			}
			catch (HibernateException e)
			{
				throw e;
			}
		}
	}

	private boolean pesquisa(Categoria cat, long idCategoria)
			throws AplicacaoException
	{ // Retorna true se cat tiver como subacategoria a categoria
		// representada por idCategoria ou se cat.id = idCategoria.

		if (cat.getId() == idCategoria)
		{
			return true;
		}
		Set subCategorias = categoriaDAO.recuperaSubCategorias(cat.getId());

		boolean retorno = false;

		for (Iterator it = subCategorias.iterator(); it.hasNext();)
		{
			Categoria subCategoria = (Categoria) it.next();
			retorno = pesquisa(subCategoria, idCategoria);

			if (retorno)
				break;
		}
		return retorno;
	}

	public boolean removeSubCategoriaDeCategoria(long idCategoria,
			long idSubCategoria) throws AplicacaoException
	{
		try
		{
			HibernateUtil.beginTransaction();

			Categoria subCategoria;
			Categoria categoria;

			try
			{
				categoria = categoriaDAO.recuperaUmaCategoria(idCategoria);
			}
			catch (ObjetoNaoEncontradoException e)
			{
				throw new AplicacaoException("Categoria nao encontrada");
			}

			try
			{
				subCategoria = categoriaDAO.recuperaUmaCategoria(idSubCategoria);
			}
			catch (ObjetoNaoEncontradoException e)
			{
				throw new AplicacaoException("Subcategoria nao encontrada");
			}

			if (categoria.getSubCategorias().remove(subCategoria))
			{
				subCategoria.setCategoriaPai(null);
				HibernateUtil.commitTransaction();
				return true;
			}
			HibernateUtil.rollbackTransaction();
			return false;
		}
		catch (AplicacaoException e)
		{
			try
			{
				HibernateUtil.rollbackTransaction();
			}
			catch (InfraestruturaException ie)
			{
			}

			throw e;
		}
		catch (InfraestruturaException e)
		{
			try
			{
				HibernateUtil.rollbackTransaction();
			}
			catch (InfraestruturaException ie)
			{
			}

			throw e;
		}
		finally
		{
			try
			{
				HibernateUtil.closeSession();
			}
			catch (HibernateException e)
			{
				throw e;
			}
		}
	}

	public Categoria recuperaUmaCategoria(long numero) throws AplicacaoException
	{
		try
		{
			HibernateUtil.beginTransaction();

			Categoria umaCategoria = categoriaDAO.recuperaUmaCategoria(numero);

			HibernateUtil.commitTransaction();

			return umaCategoria;
		}
		catch (ObjetoNaoEncontradoException e)
		{
			try
			{
				HibernateUtil.rollbackTransaction();
			}
			catch (InfraestruturaException ie)
			{
			}

			throw new AplicacaoException("Categoria nao encontrada.");
		}
		catch (InfraestruturaException e)
		{
			try
			{
				HibernateUtil.rollbackTransaction();
			}
			catch (InfraestruturaException ie)
			{
			}

			throw e;
		}
		finally
		{
			try
			{
				HibernateUtil.closeSession();
			}
			catch (InfraestruturaException e)
			{
				throw e;
			}
		}
	}

	public Categoria recuperaUmaCategoriaESubCategorias(long numero)
			throws AplicacaoException
	{
		try
		{
			HibernateUtil.beginTransaction();

			Categoria umaCategoria = categoriaDAO.recuperaUmaCategoriaESubCategorias(numero);

			HibernateUtil.commitTransaction();

			return umaCategoria;
		}
		catch (ObjetoNaoEncontradoException e)
		{
			try
			{
				HibernateUtil.rollbackTransaction();
			}
			catch (InfraestruturaException ie)
			{
			}

			throw new AplicacaoException("Categoria nao encontrada.");
		}
		catch (InfraestruturaException e)
		{
			try
			{
				HibernateUtil.rollbackTransaction();
			}
			catch (InfraestruturaException ie)
			{
			}

			throw e;
		}
		finally
		{
			try
			{
				HibernateUtil.closeSession();
			}
			catch (InfraestruturaException e)
			{
				throw e;
			}
		}
	}

	public List recuperaCategorias()
	{
		try
		{
			HibernateUtil.beginTransaction();

			List categorias = categoriaDAO.recuperaCategorias();

			HibernateUtil.commitTransaction();

			return categorias;
		}
		catch (InfraestruturaException e)
		{
			try
			{
				HibernateUtil.rollbackTransaction();
			}
			catch (InfraestruturaException ie)
			{
			}

			throw e;
		}
		finally
		{
			try
			{
				HibernateUtil.closeSession();
			}
			catch (InfraestruturaException e)
			{
				throw e;
			}
		}
	}

	public Set recuperaCategoriasESubCategorias()
	{
		try
		{
			HibernateUtil.beginTransaction();

			Set categorias = categoriaDAO.recuperaCategoriasESubCategorias();

			HibernateUtil.commitTransaction();

			return categorias;
		}
		catch (InfraestruturaException e)
		{
			try
			{
				HibernateUtil.rollbackTransaction();
			}
			catch (InfraestruturaException ie)
			{
			}

			throw e;
		}
		finally
		{
			try
			{
				HibernateUtil.closeSession();
			}
			catch (InfraestruturaException e)
			{
				throw e;
			}
		}
	}
}