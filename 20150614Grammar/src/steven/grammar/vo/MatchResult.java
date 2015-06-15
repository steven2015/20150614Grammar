/**
 *
 */
package steven.grammar.vo;

/**
 * @author steven.lam.t.f
 *
 */
public class MatchResult{
	private final String input;
	private final int start;
	private final int end;
	private final Token token;
	private final MatchResult[] children;

	public MatchResult(final String input, final int start, final int end, final Token token){
		this(input, start, end, token, null);
	}
	public MatchResult(final String input, final int start, final int end, final Token token, final MatchResult[] children){
		this.input = input;
		this.start = start;
		this.end = end;
		this.token = token;
		this.children = children;
	}
	public boolean isMatched(){
		return true;
	}
	public final String getInput(){
		return this.input;
	}
	public final int getStart(){
		return this.start;
	}
	public final int getEnd(){
		return this.end;
	}
	public final Token getToken(){
		return this.token;
	}
	public final MatchResult[] getChildren(){
		return this.children;
	}
}
