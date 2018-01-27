package exercicio22.prodcat;

import java.io.Serializable;

public class IdProdutoCategoria implements Serializable
{
	private final static long serialVersionUID = 1;

	private long idProduto;

	private long idCategoria;

	public IdProdutoCategoria()
	{
	}

	/* ==> */
	public IdProdutoCategoria(long idProduto, long idCategoria)
	{
		this.idProduto = idProduto;
		this.idCategoria = idCategoria;
	}

	public long getIdProduto()
	{
		return idProduto;
	}

	public void setIdProduto(long idProduto)
	{
		this.idProduto = idProduto;
	}

	public long getIdCategoria()
	{
		return idCategoria;
	}

	public void setIdCategoria(long idCategoria)
	{
		this.idCategoria = idCategoria;
	}
}
