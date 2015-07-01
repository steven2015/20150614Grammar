/**
 *
 */
package steven.oracle.grammar;

import java.math.BigDecimal;

import steven.grammar.Token;

/**
 * @author Steven
 *
 */
public class NumberLiteral extends Token{
	private final BigDecimal content;

	public NumberLiteral(final String data, final int startIndex, final int endIndex){
		super(data, startIndex, endIndex);
		final char c = data.charAt(endIndex - 1);
		if(c >= '0' && c <= '9') {
			this.content = new BigDecimal(data.substring(startIndex, endIndex));
		}else{
			this.content = new BigDecimal(data.substring(startIndex, endIndex - 1));
		}
	}
	public NumberLiteral(final int offset, final String value){
		super(offset, value);
		final char c = value.charAt(value.length() - 1);
		if(c >= '0' && c <= '9') {
			this.content = new BigDecimal(value);
		}else{
			this.content = new BigDecimal(value.substring(0, value.length() - 1));
		}
	}
	public final BigDecimal getContent(){
		return this.content;
	}
}
