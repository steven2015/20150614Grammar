/**
 *
 */
package steven.oracle.grammar;

import steven.grammar.Token;

/**
 * @author Steven
 *
 */
public class Hints extends Token{
	public Hints(final String data, final int startIndex, final int endIndex){
		super(data, startIndex, endIndex);
	}
	public Hints(final int offset, final String value){
		super(offset, value);
	}
}
