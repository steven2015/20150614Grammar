/**
 *
 */
package steven.grammar.vo;

/**
 * @author Steven
 *
 */
public interface Token{
	public MatchResult matches(String input, int offset);
}
