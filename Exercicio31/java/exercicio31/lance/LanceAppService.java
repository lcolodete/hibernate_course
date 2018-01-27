package exercicio31.lance;

import org.hibernate.HibernateException;
import java.util.List;
import java.sql.Date;
import java.util.ArrayList;

import exercicio31.lance.Lance;
import exercicio31.util.AplicacaoException;
import exercicio31.util.HibernateUtil;
import exercicio31.util.ObjetoNaoEncontradoException;
import exercicio31.util.*;
import exercicio31.produto.*;

public class LanceAppService
{	
	private static LanceDAO lanceDAO = new LanceDAO();
	private static ProdutoDAO produtoDAO = new ProdutoDAO();

	@SuppressWarnings("unchecked")
	public IdLance inclui(long idProduto, 
	                      long numeroLance, 
	                      String valor, 
	                      String dataCriacao, 
	                      Produto umProduto) 
		throws AplicacaoException
	{	
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
			
			ArrayList mensagens = new ArrayList();
			
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
			
			// Verifica se o lance já foi cadastrado.
			try
			{	// Para recuperar o ultimoLance de um Produto, é preciso 
				// recuperar todos os lances emitidos para esse produto. Por 
				// esse motivo, ao se tentar recuperar o lance abaixo, se ele
				// já existir ele não poderá ser cadastrado novamente.  
			
				lanceDAO.recuperaUmLance(idProduto, numeroLance);
				mensagens.add("Lance numero " + numeroLance + " ja cadastrado.");
				deuErro = true;
			}
			catch(ObjetoNaoEncontradoException e)
			{	// O lance cujo numero é "numeroLance" ainda não foi cadastrado.
			}

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

			IdLance idLance;
			
			if (!deuErro)
/* ==> */	{	umLance.setId(new IdLance(idProduto, numeroLance));
			
//				umLance.setProduto(umProduto); ==> Isso não é necessário.
																					// porque está com insert="false"
																					// e update="false" no hbm
																					// se tirar o comentário também
																					// funciona, mas nao precisa

/* ==> */		idLance = lanceDAO.inclui(umLance);
			}
			else
			{	throw new AplicacaoException(mensagens);
			}

			HibernateUtil.commitTransaction();
			
			return idLance;
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

	public void altera(Lance umLance) 
	{	try
		{	HibernateUtil.beginTransaction();

			lanceDAO.altera(umLance);

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
		     {	HibernateUtil.closeSession();
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
		     {	HibernateUtil.closeSession();
		     }
		     catch(HibernateException he)
		     {	throw he;
		     }
		}
	}

	public Lance recuperaUmLance(long idProduto, long numeroLance) 
		throws AplicacaoException
	{	try
		{	HibernateUtil.beginTransaction();

/* ==> */	Lance umLance = lanceDAO.recuperaUmLance(idProduto, numeroLance);

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
		     {	HibernateUtil.closeSession();
		     }
		     catch(InfraestruturaException he)
		     {	throw he;
		     }
		}
	}

	public Lance recuperaUmLanceComProduto(long idProduto, long numeroLance) 
		throws AplicacaoException
	{	try
		{	HibernateUtil.beginTransaction();

			Lance umLance = lanceDAO.recuperaUmLanceComProduto(idProduto, numeroLance);

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
		     {	HibernateUtil.closeSession();
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
		     {	HibernateUtil.closeSession();
		     }
		     catch(InfraestruturaException he)
		     {	throw he;
		     }
		}
	}
}