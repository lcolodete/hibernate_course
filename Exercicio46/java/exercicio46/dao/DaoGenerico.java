package exercicio46.dao;

import java.io.Serializable;

import exercicio46.util.ObjetoNaoEncontradoException;

/**
 * A interface GenericDao básica com os métodos CRUD. Os métodos
 * de busca são adicionados por herança de interface.
 *
 * Interfaces estendidas podem declarar métodos que começam  por 
 * encontrar... listar... iterate... or scroll...  Estes métodos
 * irão  executar buscas pré-configuradas que são localizadas em 
 * função do restante do nome dos métodos.
 * 
 */
public interface DaoGenerico<T, PK extends Serializable>
{
    PK inclui(T obj);

    T getPorId(PK id) throws ObjetoNaoEncontradoException;

    void altera(T obj);

    void exclui(T obj);
}
