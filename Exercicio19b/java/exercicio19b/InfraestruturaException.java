package exercicio19b;

public class InfraestruturaException extends RuntimeException
{	
	private final static long serialVersionUID = 1;
	
	public InfraestruturaException(Exception e)
	{	super(e);
	}
}	