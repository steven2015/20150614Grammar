/**
 *
 */
package steven.grammar;

import steven.grammar.exception.GrammarException;

/**
 * @author Steven
 *
 */
public interface Tokenizer{
	public Token next() throws GrammarException;
	public Token peek() throws GrammarException;
	public Tokenizer cloneCurrentState();
}
