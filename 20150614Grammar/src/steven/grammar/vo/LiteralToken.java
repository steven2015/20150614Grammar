/**
 *
 */
package steven.grammar.vo;

/**
 * @author Steven
 *
 */
public class LiteralToken implements Token{
	private final String value;
	private final boolean caseSensitive;
	private final Token test;
	private final int length;

	public LiteralToken(final String value, final boolean caseSensitive){
		this.value = value;
		this.caseSensitive = caseSensitive;
		this.length = value.length();
		if(caseSensitive){
			this.test = (final String input, final int offset) -> {
				if(value.regionMatches(0, input, offset, this.length)){
					return new MatchResult(input, offset, offset + this.length, this);
				}else{
					return new MatchFailure(input, offset, offset, this, null, null);
				}
			};
		}else{
			this.test = (final String input, final int offset) -> {
				if(value.regionMatches(true, 0, input, offset, this.length)){
					return new MatchResult(input, offset, offset + this.length, this);
				}else{
					return new MatchFailure(input, offset, offset, this, null, null);
				}
			};
		}
	}
	@Override
	public MatchResult matches(final String input, final int offset){
		return this.test.matches(input, offset);
	}
	@Override
	public String toString(){
		return this.getClass().getName() + "(" + this.value + (this.caseSensitive ? ",case sensitive" : "") + ")";
	}
	public final String getValue(){
		return this.value;
	}
	public final boolean isCaseSensitive(){
		return this.caseSensitive;
	}
}
