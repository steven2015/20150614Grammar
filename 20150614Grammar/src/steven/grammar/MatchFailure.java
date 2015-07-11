/**
 *
 */
package steven.grammar;

import java.util.List;

/**
 * @author Steven
 *
 */
public class MatchFailure implements MatchResult{
	private final Symbol symbol;
	private final MatchFailure matchFailure;
	private final MatchSuccess[] matchSuccesses;
	private final Token failingToken;

	public MatchFailure(final Symbol symbol, final MatchFailure matchFailure){
		this.symbol = symbol;
		this.matchFailure = matchFailure;
		this.matchSuccesses = null;
		this.failingToken = null;
	}
	public MatchFailure(final Symbol symbol, final MatchFailure matchFailure, final MatchSuccess... matchSuccesses){
		this.symbol = symbol;
		this.matchFailure = matchFailure;
		if(matchSuccesses == null || matchSuccesses.length == 0) {
			this.matchSuccesses = null;
		}else{
			this.matchSuccesses = matchSuccesses;
		}
		this.failingToken = null;
	}
	public MatchFailure(final Symbol symbol, final MatchFailure matchFailure, final MatchSuccess matchSuccess){
		this.symbol = symbol;
		this.matchFailure = matchFailure;
		this.matchSuccesses = new MatchSuccess[]{matchSuccess};
		this.failingToken = null;
	}
	public MatchFailure(final Symbol symbol, final MatchFailure matchFailure, final List<MatchSuccess> matchSuccesses){
		this.symbol = symbol;
		this.matchFailure = matchFailure;
		if(matchSuccesses == null || matchSuccesses.size() == 0) {
			this.matchSuccesses = null;
		}else{
			this.matchSuccesses = matchSuccesses.toArray(new MatchSuccess[matchSuccesses.size()]);
		}
		this.failingToken = null;
	}
	public MatchFailure(final Symbol symbol, final Token failingToken){
		this.symbol = symbol;
		this.matchFailure = null;
		this.matchSuccesses = null;
		this.failingToken = failingToken;
	}
	@Override
	public boolean isMatched(){
		return false;
	}
	public final Symbol getSymbol(){
		return this.symbol;
	}
	public final MatchFailure getMatchFailure(){
		return this.matchFailure;
	}
	public final MatchSuccess[] getMatchSuccesses(){
		return this.matchSuccesses;
	}
	public final Token getFailingToken(){
		return this.failingToken;
	}
}
