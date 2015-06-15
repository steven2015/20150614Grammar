/**
 *
 */
package steven.grammar.vo;

/**
 * @author Steven
 *
 */
public class OptionToken implements Token{
	public static final Token OPTIONAL = (final String input, final int offset) -> null;
	private final Token[] options;

	public OptionToken(final Token... options){
		this.options = options;
	}
	@Override
	public MatchResult matches(final String input, final int offset){
		boolean hasOptional = false;
		MatchFailure failure = null;
		for(final Token option : this.options){
			if(option == OptionToken.OPTIONAL){
				hasOptional = true;
			}else{
				final MatchResult result = option.matches(input, offset);
				if(result.isMatched()){
					return new MatchResult(input, offset, result.getEnd(), this, new MatchResult[]{result});
				}else{
					if(failure == null || failure.getEnd() < result.getEnd()){
						failure = (MatchFailure)result;
					}
				}
			}
		}
		if(hasOptional){
			return new MatchResult(input, offset, offset, this);
		}else{
			return new MatchFailure(input, offset, offset, this, null, failure);
		}
	}
	public final Token[] getOptions(){
		return this.options;
	}
}
