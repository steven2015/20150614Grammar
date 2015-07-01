/**
 *
 */
package steven.grammar;

/**
 * @author Steven
 *
 */
public interface Symbol{
	public MatchResult matches(Tokenizer tokenizer);
}
