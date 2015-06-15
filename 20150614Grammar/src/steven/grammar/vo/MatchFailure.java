/**
 *
 */
package steven.grammar.vo;

/**
 * @author steven.lam.t.f
 *
 */
public class MatchFailure extends MatchResult{
	private final MatchResult[] longestMatchingSequence;
	private final MatchFailure child;

	/**
	 *
	 * @param input
	 * @param start
	 * @param end
	 *            end index of the <code>longestMatchingSequence</code>
	 * @param token
	 *            <code>this</code> token, not child token
	 * @param longestMatchingSequence
	 *            longest match sequence of child tokens
	 * @param child
	 *            <code>MatchFailure</code> received from the failing child token
	 */
	public MatchFailure(final String input, final int start, final int end, final Token token, final MatchResult[] longestMatchingSequence, final MatchFailure child){
		super(input, start, end, token, null);
		this.longestMatchingSequence = longestMatchingSequence;
		this.child = child;
	}
	@Override
	public boolean isMatched(){
		return false;
	}
	public final MatchResult[] getLongestMatchingSequence(){
		return this.longestMatchingSequence;
	}
	public final MatchFailure getChild(){
		return this.child;
	}
}
