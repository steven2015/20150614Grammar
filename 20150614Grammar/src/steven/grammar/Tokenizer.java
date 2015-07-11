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
	public int saveState();
	public void restoreState(int state);
}
