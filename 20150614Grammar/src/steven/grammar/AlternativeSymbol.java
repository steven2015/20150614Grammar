/**
 *
 */
package steven.grammar;

import steven.grammar.exception.GrammarException;

/**
 * @author Steven
 *
 */
public class AlternativeSymbol implements Symbol{
	private final boolean optional;
	private final Symbol[] options;

	public AlternativeSymbol(final boolean optional, final Symbol... options){
		this.optional = optional;
		this.options = options;
	}
	@Override
	public MatchResult matches(final Tokenizer tokenizer) throws GrammarException{
		final int state = tokenizer.saveState();
		for(final Symbol option : this.options){
			tokenizer.restoreState(state);
			final MatchResult r = option.matches(tokenizer);
			if(r.isMatched()) {
				return new MatchSuccess(this, (MatchSuccess)r);
			}
		}
		tokenizer.restoreState(state);
		if(this.optional) {
			return new MatchSuccess(this);
		}else{
			return new MatchFailure(this, tokenizer.next());
		}
	}
	public final boolean isOptional(){
		return this.optional;
	}
	public final Symbol[] getOptions(){
		return this.options;
	}
}
