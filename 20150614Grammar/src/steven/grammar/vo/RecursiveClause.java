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
public class RecursiveClause implements Token{
	private final Token body;
	private final Token appendWhenLoop;

	public RecursiveClause(final Token body, final Token appendWhenLoop){
		this.body = body;
		this.appendWhenLoop = appendWhenLoop;
	}
	@Override
	public MatchResult matches(final String input, final int offset){
		int newOffset = offset;
		final List<MatchResult> results = new ArrayList<>();
		int resultsSize = 0;
		int resultsEnd = 0;
		while(true){
			final int markOffset = newOffset;
			if(this.body != null && this.body != OptionToken.OPTIONAL){
				final MatchResult bodyResult = this.body.matches(input, newOffset);
				if(bodyResult.isMatched()){
					results.add(bodyResult);
					newOffset = bodyResult.getEnd();
					resultsSize = results.size();
					resultsEnd = newOffset;
				}else{
					if(newOffset == offset){
						return new MatchFailure(input, offset, newOffset, this, null, (MatchFailure)bodyResult);
					}else{
						return new MatchResult(input, offset, resultsEnd, this, results.subList(0, resultsSize).toArray(new MatchResult[resultsSize]));
					}
				}
			}
			if(this.appendWhenLoop != null && this.appendWhenLoop != OptionToken.OPTIONAL){
				final MatchResult appendWhenLoopResult = this.appendWhenLoop.matches(input, newOffset);
				if(appendWhenLoopResult.isMatched()){
					results.add(appendWhenLoopResult);
					newOffset = appendWhenLoopResult.getEnd();
				}else{
					return new MatchResult(input, offset, newOffset, this, results.toArray(new MatchResult[results.size()]));
				}
			}
			if(markOffset == newOffset){
				return new MatchFailure(input, offset, newOffset, this, results.toArray(new MatchResult[results.size()]), null);
			}
		}
	}
	public final Token getBody(){
		return this.body;
	}
	public final Token getAppendWhenLoop(){
		return this.appendWhenLoop;
	}
}
