package exercicio22.prodcat;

import exercicio22.produto.Produto;
import exercicio22.categoria.Categoria;

public class ProdutoCategoria
{	
	private IdProdutoCategoria id;

	private Produto produto;

	private Categoria categoria;

	// ********* Construtores *********

	public ProdutoCategoria()
	{	
	}
/* ==> */
	public ProdutoCategoria(long idProduto, long idCategoria)
	{	this.id = new IdProdutoCategoria(idProduto, idCategoria);
	}

	// ********* Métodos do Tipo Get *********

/* ==> */
	public IdProdutoCategoria getId()
	{	return id;
	}
	
	// ********* Métodos do Tipo Set *********

/* ==> */	
	@SuppressWarnings("unused")
	private void setId(IdProdutoCategoria id)
	{	this.id = id;
	}

	// ********* Métodos para Associações *********

	public Produto getProduto()
	{	return produto;
	}
	
	public Categoria getCategoria()
	{	return categoria;
	}
	
	public void setProduto(Produto produto)
	{	this.produto = produto;
	}

	public void setCategoria(Categoria categoria)
	{	this.categoria = categoria;
	}
}

