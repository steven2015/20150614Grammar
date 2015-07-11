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
public class RecursiveSymbol implements Symbol{
	private final Symbol body;
	private final Symbol includedWhenLoop;

	public RecursiveSymbol(final Symbol body, final Symbol includedWhenLoop){
		this.body = body;
		this.includedWhenLoop = includedWhenLoop;
	}
	@Override
	public MatchResult matches(final Tokenizer tokenizer) throws GrammarException{
		final int state = tokenizer.saveState();
		final List<MatchSuccess> list = new ArrayList<>();
		MatchSuccess lastLoopSuccess = null;
		MatchFailure matchFailure = null;
		while(true){
			if(this.body != null) {
				final MatchResult r = this.body.matches(tokenizer);
				if(r.isMatched()) {
					if(lastLoopSuccess != null) {
						list.add(lastLoopSuccess);
						lastLoopSuccess = null;
					}
					list.add((MatchSuccess)r);
				}else{
					matchFailure = (MatchFailure)r;
					break;
				}
			}
			if(this.includedWhenLoop != null) {
				final MatchResult r = this.includedWhenLoop.matches(tokenizer);
				if(r.isMatched()) {
					lastLoopSuccess = (MatchSuccess)r;
				}else{
					break;
				}
			}
		}
		if(list.size() == 0) {
			if(this.body == null) {
				return new MatchSuccess(this);
			}else{
				tokenizer.restoreState(state);
				return new MatchFailure(this, matchFailure, list);
			}
		}else{
			return new MatchSuccess(this, list);
		}
	}
	public final Symbol getBody(){
		return this.body;
	}
	public final Symbol getIncludedWhenLoop(){
		return this.includedWhenLoop;
	}
}
