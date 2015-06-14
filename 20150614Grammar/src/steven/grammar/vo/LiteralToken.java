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

	public LiteralToken(final String value, final boolean caseSensitive){
		this.value = value;
		this.caseSensitive = caseSensitive;
		if(caseSensitive){
			this.test = (final String[] input, final int offset) -> {
				if(offset < input.length && value.equals(input[offset])){
					return offset + 1;
				}else{
					return -1;
				}
			};
		}else{
			this.test = (final String[] input, final int offset) -> {
				if(offset < input.length && value.equalsIgnoreCase(input[offset])){
					return offset + 1;
				}else{
					return -1;
				}
			};
		}
	}
	@Override
	public int matches(final String[] input, final int offset){
		return this.test.matches(input, offset);
	}
	public final String getValue(){
		return this.value;
	}
	public final boolean isCaseSensitive(){
		return this.caseSensitive;
	}
}
