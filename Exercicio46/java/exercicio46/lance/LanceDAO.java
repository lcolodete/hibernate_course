package exercicio46.lance;

import java.util.List;

import exercicio46.dao.DaoGenerico;

public interface LanceDAO 
	extends DaoGenerico<Lance, Long>
{
    Lance buscaUmLanceComProduto(long numero);

    List<Lance> recuperaLances();
}