package exercicio22.categoria;

import java.util.Set;
import java.util.HashSet;

public class Categoria
{
	private Long id;

	private String nome;

	private Categoria categoriaPai;

	private Set subCategorias = new HashSet();

	private Set produtoCategorias = new HashSet();

	// ********* Construtores *********

	public Categoria()
	{
	}

	public Categoria(String nome)
	{
		this.nome = nome;
	}

	// ********* Métodos do Tipo Get *********

	public Long getId()
	{
		return id;
	}

	public String getNome()
	{
		return nome;
	}

	// ********* Métodos do Tipo Set *********

	@SuppressWarnings("unused")
	private void setId(Long id)
	{
		this.id = id;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	// ********* Métodos para Associações *********

	public Set getSubCategorias()
	{
		return subCategorias;
	}

	public void setSubCategorias(Set subCategorias)
	{
		this.subCategorias = subCategorias;
	}

	public Categoria getCategoriaPai()
	{
		return categoriaPai;
	}

	public void setCategoriaPai(Categoria categoriaPai)
	{
		this.categoriaPai = categoriaPai;
	}

	/* ==> */
	public Set getProdutoCategorias()
	{
		return produtoCategorias;
	}

	/* ==> */
	public void setProdutoCategorias(Set produtoCategorias)
	{
		this.produtoCategorias = produtoCategorias;
	}
}
