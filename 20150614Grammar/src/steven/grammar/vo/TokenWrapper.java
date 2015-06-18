/**
 *
 */
package steven.grammar.vo;

/**
 * @author steven.lam.t.f
 *
 */
public class TokenWrapper implements Token{
	private final String name;
	private Token wrapped;

	public TokenWrapper(final String name){
		this.name = name;
	}
	public TokenWrapper(final String name, final Token wrapped){
		this.name = name;
		this.wrapped = wrapped;
	}
	@Override
	public MatchResult matches(final String input, final int offset){
		final MatchResult result = this.wrapped.matches(input, offset);
		if(result.isMatched()){
			return new MatchResult(input, result.getStart(), result.getEnd(), this, result.getChildren());
		}else{
			return new MatchFailure(input, result.getStart(), result.getEnd(), this, ((MatchFailure)result).getLongestMatchingSequence(), ((MatchFailure)result).getChild());
		}
	}
	@Override
	public String toString(){
		return this.name;
	}
	public final Token getWrapped(){
		return this.wrapped;
	}
	public final void setWrapped(final Token wrapped){
		this.wrapped = wrapped;
	}
	public final String getName(){
		return this.name;
	}
}
