/**
 *
 */
package steven.grammar.vo;

/**
 * @author Steven
 *
 */
public class OptionToken implements Token{
	public static final Token OPTIONAL = (final String[] input, final int offset) -> offset;
	private final Token[] options;

	public OptionToken(final Token... options){
		this.options = options;
	}
	@Override
	public int matches(final String[] input, final int offset){
		boolean hasOptional = false;
		for(final Token option : this.options){
			if(option == OptionToken.OPTIONAL){
				hasOptional = true;
			}else{
				final int newOffset = option.matches(input, offset);
				if(newOffset >= 0){
					return newOffset;
				}
			}
		}
		if(hasOptional){
			return offset;
		}else{
			return -1;
		}
	}
	public final Token[] getOptions(){
		return this.options;
	}
}
