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
public enum NonQuotedIdentifier implements Token{
	INSTANCE;
	private NonQuotedIdentifier(){
	}
	@Override
	public MatchResult matches(final String input, final int offset){
		final int length = input.length();
		if(offset < length){
			int start = offset;
			final char c = input.charAt(start);
			if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')){
				start++;
			}else{
				return new MatchFailure(input, offset, offset, this, null, null);
			}
			for(int i = start; i < length; i++){
				final char c2 = input.charAt(i);
				if(((c2 >= 'a' && c2 <= 'z') || (c2 >= 'A' && c2 <= 'Z') || (c2 >= '0' && c2 <= '9') || c2 == '_' || c2 == '$' || c2 == '#') == false){
					return new MatchResult(input, offset, i, this);
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
