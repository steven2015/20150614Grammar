/**
 *
 */
package steven.oracle.grammar;

import steven.grammar.Token;

/**
 * @author Steven
 *
 */
public class QuotedIdentifier extends Token{
	public QuotedIdentifier(final String data, final int startIndex, final int endIndex){
		super(data, startIndex, endIndex);
	}
	public QuotedIdentifier(final int offset, final String value){
		super(offset, value);
	}
}
