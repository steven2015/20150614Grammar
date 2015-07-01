/**
 *
 */
package steven.grammar.exception;

/**
 * @author Steven
 *
 */
public class GrammarException extends Exception{
	private static final long serialVersionUID = -3707166164646402338L;

	public GrammarException(final int lineNumber, final int position, final String message){
		super("Error at line " + lineNumber + " position " + position + ": " + message);
	}
}
