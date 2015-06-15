/**
 *
 */
package steven.grammar.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Steven
 *
 */
public class SequentialClause implements Token{
	private final Token[] tokens;

	public SequentialClause(final Token... tokens){
		this.tokens = tokens;
	}
	@Override
	public MatchResult matches(final String input, final int offset){
		int newOffset = offset;
		final List<MatchResult> results = new ArrayList<>();
		for(final Token token : this.tokens){
			final MatchResult result = token.matches(input, newOffset);
			if(result.isMatched()){
				results.add(result);
				newOffset = result.getEnd();
			}else{
				return new MatchFailure(input, offset, newOffset, this, results.toArray(new MatchResult[results.size()]), (MatchFailure)result);
			}
		}
		return new MatchResult(input, offset, newOffset, this, results.toArray(new MatchResult[results.size()]));
	}
	public final Token[] getTokens(){
		return this.tokens;
	}
}
