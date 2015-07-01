/**
 *
 */
package steven.oracle.grammar;

import steven.grammar.Token;

/**
 * @author Steven
 *
 */
public class StringLiteral extends Token{
	private final String content;

	public StringLiteral(final String data, final int startIndex, final int endIndex){
		super(data, startIndex, endIndex);
		this.content = data.substring(startIndex + 1, endIndex - 1);
	}
	public StringLiteral(final int offset, final String value){
		super(offset, value);
		this.content = value.substring(1, value.length() - 1);
	}
	public final String getContent(){
		return this.content;
	}
}
