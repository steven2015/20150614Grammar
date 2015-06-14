/**
 *
 */
package steven.grammar.vo;

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
	public int matches(final String[] input, final int offset){
		int newOffset = offset;
		for(final Token token : this.tokens){
			newOffset = token.matches(input, newOffset);
			if(newOffset < 0){
				return -1;
			}
		}
		return newOffset;
	}
	public final Token[] getTokens(){
		return this.tokens;
	}
}
