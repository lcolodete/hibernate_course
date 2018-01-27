package exercicio45.lance;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import exercicio45.controleTransacao.Transacional;
import exercicio45.produto.Produto;
import exercicio45.produto.ProdutoDAO;
import exercicio45.util.AplicacaoException;

public class LanceAppService
{	
	private static LanceDAO lanceDAO = new LanceDAO();
	private static ProdutoDAO produtoDAO = new ProdutoDAO();

	@Transacional
	@SuppressWarnings("unchecked")
	public long inclui(String valor, String dataCriacao, Produto umProduto) 
		throws AplicacaoException
	{
		Lance umLance = new Lance();

		// A implementação do método recuperaUltimoLance(umProduto) 
		// impede que dois lances sejam cadastrados em paralelo.
		// Como este método põe um lock em Produto, a inserção de 
		// dois produtos acontece sempre em série, i. é, obedecendo
		// a uma fila. Isto impede que o valor do segundo lance seja
		// inferior ao valor do primeiro.

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
		ArrayList mensagens = new ArrayList();
		
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
		
		return numero;
	}	
	
	@Transacional
	public void exclui(Lance umLance) 
	{	lanceDAO.exclui(umLance);		
	}
	
	public Lance recuperaUmLance(long numero)
		throws AplicacaoException	
	{	Lance umLance = lanceDAO.recuperaUmLance(numero);
		
		return umLance;
	}
	
	public List recuperaLances()
	{	List lances = lanceDAO.recuperaLances();
		
		return lances;
	}
}