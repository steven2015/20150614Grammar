/**
 *
 */
package steven.grammar.oracle.vo;

import steven.grammar.vo.MatchFailure;
import steven.grammar.vo.MatchResult;
import steven.grammar.vo.OptionToken;
import steven.grammar.vo.RecursiveClause;
import steven.grammar.vo.Token;

/**
 * @author steven.lam.t.f
 *
 */
public enum SpaceOrCommentToken implements Token{
	INSTANCE;
	private final Token test;

	private SpaceOrCommentToken(){
		this.test = new RecursiveClause(new OptionToken(SpaceToken.INSTANCE, CommentToken.INSTANCE), null);
	}
	@Override
	public MatchResult matches(final String input, final int offset){
		final MatchResult result = this.test.matches(input, offset);
		if(result.isMatched()){
			return new MatchResult(input, offset, result.getEnd(), this, result.getChildren());
		}else{
			return new MatchFailure(input, offset, result.getEnd(), this, result.getChildren(), ((MatchFailure)result).getChild());
		}
	}
	@Override
	public String toString(){
		return this.getClass().getName();
	}
}
