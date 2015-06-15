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
public enum CommentToken implements Token{
	INSTANCE;
	private CommentToken(){
	}
	@Override
	public MatchResult matches(final String input, final int offset){
		if("/*".regionMatches(0, input, offset, 2)){
			final int index = input.indexOf("*/", offset + 2);
			if(index >= 0){
				return new MatchResult(input, offset, index + 2, this);
			}else{
				return new MatchFailure(input, offset, input.length(), this, null, null);
			}
		}else if("--".regionMatches(0, input, offset, 2)){
			final int cr = input.indexOf('\r', offset + 2);
			final int lf = input.indexOf('\n', offset + 2);
			if(cr >= 0){
				if(lf == cr + 1){
					return new MatchResult(input, offset, lf + 1, this);
				}else if(lf < 0 || cr < lf){
					return new MatchResult(input, offset, cr + 1, this);
				}else{
					return new MatchResult(input, offset, lf + 1, this);
				}
			}else if(lf >= 0){
				return new MatchResult(input, offset, lf + 1, this);
			}else{
				return new MatchResult(input, offset, input.length(), this);
			}
		}else{
			return new MatchFailure(input, offset, offset, this, null, null);
		}
	}
	@Override
	public String toString(){
		return this.getClass().getName();
	}
}
