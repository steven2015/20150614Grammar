/**
 *
 */
package steven.grammar;

import java.util.List;

/**
 * @author Steven
 *
 */
public class MatchSuccess implements MatchResult{
	private final Symbol symbol;
	private final MatchSuccess[] matchSuccesses;
	private final Token matchingToken;

	public MatchSuccess(final Symbol symbol){
		this.symbol = symbol;
		this.matchSuccesses = null;
		this.matchingToken = null;
	}
	public MatchSuccess(final Symbol symbol, final MatchSuccess... matchSuccesses){
		this.symbol = symbol;
		if(matchSuccesses == null || matchSuccesses.length == 0) {
			this.matchSuccesses = null;
		}else{
			this.matchSuccesses = matchSuccesses;
		}
		this.matchingToken = null;
	}
	public MatchSuccess(final Symbol symbol, final MatchSuccess matchSuccess){
		this.symbol = symbol;
		this.matchSuccesses = new MatchSuccess[]{matchSuccess};
		this.matchingToken = null;
	}
	public MatchSuccess(final Symbol symbol, final List<MatchSuccess> matchSuccesses){
		this.symbol = symbol;
		if(matchSuccesses == null || matchSuccesses.size() == 0) {
			this.matchSuccesses = null;
		}else{
			this.matchSuccesses = matchSuccesses.toArray(new MatchSuccess[matchSuccesses.size()]);
		}
		this.matchingToken = null;
	}
	public MatchSuccess(final Symbol symbol, final Token matchingToken){
		this.symbol = symbol;
		this.matchSuccesses = null;
		this.matchingToken = matchingToken;
	}
	@Override
	public boolean isMatched(){
		return true;
	}
	public final Symbol getSymbol(){
		return this.symbol;
	}
	public final MatchSuccess[] getMatchSuccesses(){
		return this.matchSuccesses;
	}
	public final Token getMatchingToken(){
		return this.matchingToken;
	}
}
