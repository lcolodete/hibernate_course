package exercicio31.lance;

import java.sql.Date;
import exercicio31.produto.Produto;
import exercicio31.util.*;

public class Lance
{
	private IdLance id;

	private double valor;

	private Date dataCriacao;

	private Produto produto;

	// ********* Construtores *********

	public Lance()
	{
	}

	// ********* Métodos do Tipo Get *********

	public IdLance getId()
	{
		return id;
	}

	public double getValor()
	{
		return valor;
	}

	public Date getDataCriacao()
	{
		return dataCriacao;
	}

	public String getDataCriacaoMasc()
	{
		return Util.dateToStr(dataCriacao);
	}

	// ********* Métodos do Tipo Set *********

	public void setId(IdLance id)
	{
		this.id = id;
	}

	public void setValor(String valor, double valorUltimoLance)
			throws AplicacaoException
	{
		if (valor == null || valor.length() == 0)
		{
			throw new AplicacaoException("Valor invalido.");
		}
		try
		{
			this.valor = Util.strToDouble(valor);
		}
		catch (NumberFormatException e)
		{
			throw new AplicacaoException("Valor inválido.");
		}

		if (this.valor <= valorUltimoLance)
		{
			throw new AplicacaoException("O valor do lance tem que ser superior a "
					+ valorUltimoLance);
		}
	}

	public void setDataCriacao(String dataCriacao, Date dataUltimoLance)
			throws AplicacaoException
	{
		if (!Util.dataValida(dataCriacao))
		{
			throw new AplicacaoException("Data de emissao invalida.");
		}
		this.dataCriacao = Util.strToDate(dataCriacao);

		if (this.dataCriacao.before(dataUltimoLance))
		{
			throw new AplicacaoException(
					"A data de emissao do lance nao pode ser anterior a "
							+ Util.dateToStr(dataUltimoLance));
		}

		Date hoje = new Date(System.currentTimeMillis());

		if (this.dataCriacao.after(hoje))
		{
			throw new AplicacaoException(
					"A data de emissao do lance nao pode ser posterior à data de hoje: "
							+ Util.dateToStr(hoje));
		}
	}

	// ********* Métodos para Associações *********

	public Produto getProduto()
	{
		return produto;
	}

	public void setProduto(Produto produto)
	{
		this.produto = produto;
	}
}
