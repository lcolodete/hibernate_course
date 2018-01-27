package exercicio35;

import java.util.List;

public class AplicacaoException extends Exception
{	
	private final static long serialVersionUID = 1;
	
	private int codigo;
	private List mensagens;
	
	public AplicacaoException()
	{
	}

	public AplicacaoException(String msg)
	{	super(msg);
	}

	public AplicacaoException(List mensagens)
	{	this.mensagens = mensagens;
	}

	public AplicacaoException(int codigo, String msg)
	{	super(msg);
		this.codigo = codigo;
	}
	
	public AplicacaoException(Exception e)
	{	super(e);
	}
	
	public int getCodigoDeErro()
	{	return codigo;
	}

	public List getMensagens()
	{	return mensagens;
	}
}	