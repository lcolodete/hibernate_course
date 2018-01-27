package exercicio46.produto;

import java.util.List;
import java.util.Set;

import exercicio46.dao.DaoGenerico;
import exercicio46.lance.Lance;

public interface ProdutoDAO 
	extends DaoGenerico<Produto, Long>
{
    Produto buscaUmProdutoELances(long numero);
    
    List<Produto> recuperaProdutos();
    
    Set<Produto> recuperaConjuntoDeProdutosELances();
    
    Lance buscaUltimoLance(Produto produto);
}