/**
 *
 */
package steven.grammar.oracle.vo;

import steven.grammar.vo.MatchFailure;
import steven.grammar.vo.MatchResult;
import steven.grammar.vo.Token;

/**
 * @author steven.lam.t.f
 *
 */
public enum Space implements Token{
	INSTANCE;
	private Space(){
	}
	@Override
	public MatchResult matches(final String input, final int offset){
		int i = offset;
		final int length = input.length();
		while(i < length){
			final char c = input.charAt(i);
			if(c != ' ' && c != '\t' && c != '\r' && c != '\n'){
				break;
			}else{
				i++;
			}
		}
		if(i > offset){
			return new MatchResult(input, offset, i, this);
		}else{
			return new MatchFailure(input, offset, offset, this, null, null);
		}
	}
	@Override
	public String toString(){
		return this.getClass().getName();
	}
}
