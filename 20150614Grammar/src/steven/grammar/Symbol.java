/**
 *
 */
package steven.grammar;

import steven.grammar.exception.GrammarException;

/**
 * @author Steven
 *
 */
public interface Symbol{
	public MatchResult matches(Tokenizer tokenizer) throws GrammarException;
}
