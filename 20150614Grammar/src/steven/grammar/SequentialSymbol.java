/**
 *
 */
package steven.grammar;

import java.util.ArrayList;
import java.util.List;

import steven.grammar.exception.GrammarException;

/**
 * @author Steven
 *
 */
public class SequentialSymbol implements Symbol{
	private final Symbol[] symbols;

	public SequentialSymbol(final Symbol... symbols){
		this.symbols = symbols;
	}
	@Override
	public MatchResult matches(final Tokenizer tokenizer) throws GrammarException{
		final int state = tokenizer.saveState();
		final List<MatchSuccess> list = new ArrayList<>();
		for(final Symbol symbol : this.symbols){
			final MatchResult r = symbol.matches(tokenizer);
			if(r.isMatched()) {
				list.add((MatchSuccess)r);
			}else{
				tokenizer.restoreState(state);
				return new MatchFailure(this, (MatchFailure)r, list);
			}
		}
		return new MatchSuccess(this, list);
	}
}
