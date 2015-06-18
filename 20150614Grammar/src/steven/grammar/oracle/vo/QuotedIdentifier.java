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
public enum QuotedIdentifier implements Token{
	INSTANCE;
	private QuotedIdentifier(){
	}
	@Override
	public MatchResult matches(final String input, final int offset){
		final int length = input.length();
		if(offset < length && input.charAt(offset) == '\"'){
			for(int i = offset + 1; i < length; i++){
				if(input.charAt(i) == '\"'){
					return new MatchResult(input, offset, i + 1, this);
				}
			}
			return new MatchFailure(input, offset, length, this, null, null);
		}else{
			return new MatchFailure(input, offset, offset, this, null, null);
		}
	}
	@Override
	public String toString(){
		return this.getClass().getName();
	}
}
