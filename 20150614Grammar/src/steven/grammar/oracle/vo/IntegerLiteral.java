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
public enum IntegerLiteral implements Token{
	INSTANCE;
	private IntegerLiteral(){
	}
	@Override
	public MatchResult matches(final String input, final int offset){
		final int length = input.length();
		if(offset < length){
			int start = offset;
			final char c = input.charAt(start);
			if(c == '+' || c == '-'){
				start++;
			}
			for(int i = start; i < length; i++){
				final char c2 = input.charAt(i);
				if(c2 < '0' || c2 > '9'){
					if(i > start){
						return new MatchResult(input, offset, i, this);
					}else{
						return new MatchFailure(input, offset, i, this, null, null);
					}
				}
			}
			return new MatchResult(input, offset, length, this);
		}else{
			return new MatchFailure(input, offset, offset, this, null, null);
		}
	}
	@Override
	public String toString(){
		return this.getClass().getName();
	}
}
