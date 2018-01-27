package exercicio20.lance;

import org.hibernate.HibernateException;
import java.util.List;
import java.sql.Date;
import java.util.ArrayList;

import exercicio20.lance.Lance;
import exercicio20.util.AplicacaoException;
import exercicio20.util.ObjetoNaoEncontradoException;
import exercicio20.util.*;
import exercicio20.produto.*;

public class LanceAppService
{	
	private static LanceDAO lanceDAO = new LanceDAO();
	private static ProdutoDAO produtoDAO = new ProdutoDAO();

	@SuppressWarnings("unchecked")
	public long inclui(double valor, Date dataCriacao, Produto umProduto) 
		throws AplicacaoException
	{	
		ArrayList mensagens = new ArrayList();

		try
		{	HibernateUtil.beginTransaction();

			Lance umLance = new Lance();
	
			// A execução do método  recuperaUmProdutoComLock(id)  abaixo
			// impede  que  dois lances  sejam  cadastrados em  paralelo.
			// Como este  método põe  um lock em  Produto, a inserção  de 
			// dois  lances  acontece  sempre em  série, i. é, obedecendo
			// a uma fila. Isto impede que o valor do  segundo lance seja
			// inferior ao valor do primeiro ou que se tente cadastrar um
			// lance para um produto que tenha sido removido.
			
			try
			{	produtoDAO.recuperaUmProdutoComLock(umProduto.getId());
			}
			catch(ObjetoNaoEncontradoException e)
			{	mensagens.add("Produto não encontrado");
				throw new AplicacaoException(mensagens);
			}

			Lance ultimoLance = produtoDAO.recuperaUltimoLance(umProduto);

			double valorUltimoLance;
			Date   dataUltimoLance;
			
			if (ultimoLance == null)
			{	valorUltimoLance = umProduto.getLanceMinimo() - 1;
				dataUltimoLance  = umProduto.getDataCadastro();
			}
			else
			{	valorUltimoLance = ultimoLance.getValor();
				dataUltimoLance  = ultimoLance.getDataCriacao();
			}
			
			boolean deuErro = false;
			
			try
			{	umLance.setValor(valor, valorUltimoLance);
			}
			catch(AplicacaoException e)
			{	mensagens.add(e.getMessage());
				deuErro = true;
			}
			
			try
			{	umLance.setDataCriacao(dataCriacao, dataUltimoLance);
			}
			catch(AplicacaoException e)
			{	mensagens.add(e.getMessage());
				deuErro = true;
			}

			long numero;
			
			if (!deuErro)
			{	umLance.setProduto(umProduto); // Evita a recuperacao 
											   // de todos os lances 
											   // de um produto.
				numero = lanceDAO.inclui(umLance);
			}
			else
			{	throw new AplicacaoException(mensagens);
			}

			HibernateUtil.commitTransaction();
			
			return numero;
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
		{    try
		     {    HibernateUtil.closeSession();
		     }
		     catch(InfraestruturaException he)
		     {	throw he;
		     }
		}
	}

	public void exclui(Lance umLance) 
	{	try
		{	HibernateUtil.beginTransaction();

			lanceDAO.exclui(umLance);

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
		{    try
		     {  HibernateUtil.closeSession();
		     }
		     catch(HibernateException he)
		     {	throw he;
		     }
		}
	}

	public Lance recuperaUmLance(long numero) throws AplicacaoException
	{	try
		{	HibernateUtil.beginTransaction();

			Lance umLance = lanceDAO.recuperaUmLance(numero);

			HibernateUtil.commitTransaction();
			
			return umLance;
		} 
		catch(ObjetoNaoEncontradoException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

		    throw new AplicacaoException("Lance nao encontrado");
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
		{    try
		     {  HibernateUtil.closeSession();
		     }
		     catch(InfraestruturaException he)
		     {	throw he;
		     }
		}
	}

	public Lance recuperaUmLanceComProduto(long numero) 
		throws AplicacaoException
	{	try
		{	HibernateUtil.beginTransaction();

			Lance umLance = lanceDAO.recuperaUmLanceComProduto(numero);

			HibernateUtil.commitTransaction();
			
			return umLance;
		} 
		catch(ObjetoNaoEncontradoException e)
		{	try
			{	HibernateUtil.rollbackTransaction();
			}
			catch(InfraestruturaException ie)
			{ }

		    throw new AplicacaoException("Lance nao encontrado");
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
		{    try
		     {  HibernateUtil.closeSession();
		     }
		     catch(InfraestruturaException he)
		     {	throw he;
		     }
		}
	}

	public List recuperaLances()
	{	try
		{	HibernateUtil.beginTransaction();

			List lances = lanceDAO.recuperaLances();

			HibernateUtil.commitTransaction();
			
			return lances;
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
		{    try
		     {  HibernateUtil.closeSession();
		     }
		     catch(InfraestruturaException he)
		     {	throw he;
		     }
		}
	}
}