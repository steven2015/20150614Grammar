/**
 *
 */
package steven.grammar;

/**
 * @author Steven
 *
 */
public class MatchFailure implements MatchResult{
	public MatchFailure(){
	}
	@Override
	public boolean isMatched(){
		return false;
	}
}
