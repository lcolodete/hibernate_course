package exercicio46.util;

public class ObjetoNaoEncontradoException extends AplicacaoException
{	
	private final static long serialVersionUID = 1L;
	
	public ObjetoNaoEncontradoException()
	{
	}
	
	public ObjetoNaoEncontradoException(String mensagem)
	{	super(mensagem);
	}
}	