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
public enum Comment implements Token{
	INSTANCE,
	HINT;
	private Comment(){
	}
	@Override
	public MatchResult matches(final String input, final int offset){
		final int length = input.length();
		if("/*".regionMatches(0, input, offset, 2)){
			final Token token;
			if(offset + 2 < length && input.charAt(offset + 2) == '+'){
				token = HINT;
			}else{
				token = INSTANCE;
			}
			final int index = input.indexOf("*/", offset + 2);
			if(index >= 0){
				return new MatchResult(input, offset, index + 2, token);
			}else{
				return new MatchFailure(input, offset, input.length(), token, null, null);
			}
		}else if("--".regionMatches(0, input, offset, 2)){
			final Token token;
			if(offset + 2 < length && input.charAt(offset + 2) == '+'){
				token = HINT;
			}else{
				token = INSTANCE;
			}
			final int cr = input.indexOf('\r', offset + 2);
			final int lf = input.indexOf('\n', offset + 2);
			if(cr >= 0){
				if(lf == cr + 1){
					return new MatchResult(input, offset, lf + 1, token);
				}else if(lf < 0 || cr < lf){
					return new MatchResult(input, offset, cr + 1, token);
				}else{
					return new MatchResult(input, offset, lf + 1, token);
				}
			}else if(lf >= 0){
				return new MatchResult(input, offset, lf + 1, token);
			}else{
				return new MatchResult(input, offset, input.length(), token);
			}
		}else{
			return new MatchFailure(input, offset, offset, this, null, null);
		}
	}
	@Override
	public String toString(){
		return this.getClass().getName() + "." + this.name();
	}
}
