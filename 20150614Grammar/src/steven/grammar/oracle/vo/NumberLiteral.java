/**
 *
 */
package steven.grammar.oracle.vo;

import steven.grammar.vo.MatchFailure;
import steven.grammar.vo.MatchResult;
import steven.grammar.vo.Token;

/**
 *
 * @author steven.lam.t.f
 *
 */
public enum NumberLiteral implements Token{
	INSTANCE;
	private NumberLiteral(){
	}
	@Override
	public MatchResult matches(final String input, final int offset){
		final int length = input.length();
		if(offset < length){
			int start = offset;
			final char c = input.charAt(start);
			if(c == '+' || c == '-'){
				start++;
			}
			if(input.charAt(start) == '.'){
				start++;
				for(int i = start; i < length; i++){
					final char c2 = input.charAt(i);
					if(c2 < '0' || c2 > '9'){
						if(i > start){
							start = i;
							break;
						}else{
							return new MatchFailure(input, offset, i, this, null, null);
						}
					}
				}
				start = length;
			}else{
				boolean hasDot = false;
				for(int i = start; i < length; i++){
					final char c2 = input.charAt(i);
					if(c2 < '0' || c2 > '9'){
						if(i > start){
							if(c2 == '.'){
								if(hasDot){
									start = i;
									break;
								}else{
									hasDot = true;
								}
							}else{
								start = i;
								break;
							}
						}else{
							return new MatchFailure(input, offset, i, this, null, null);
						}
					}
				}
				start = length;
			}
			if(start < length){
				boolean scientificNotation = false;
				final char c2 = input.charAt(start);
				if(c2 == 'e' || c2 == 'E'){
					start++;
					scientificNotation = true;
				}
				if(start < length){
					final char c3 = input.charAt(start);
					if(c3 == '+' || c3 == '-'){
						start++;
						scientificNotation = true;
					}
				}
				if(scientificNotation){
					for(int i = start; i < length; i++){
						final char c3 = input.charAt(i);
						if(c3 < '0' || c3 > '9'){
							if(i > start){
								start = i;
								break;
							}else{
								return new MatchFailure(input, offset, i, this, null, null);
							}
						}
					}
					start = length;
				}
				if(start < length){
					final char c3 = input.charAt(start);
					if(c3 == 'f' || c3 == 'F' || c3 == 'd' || c3 == 'D'){
						start++;
					}
				}
			}
			return new MatchResult(input, offset, start, this);
		}else{
			return new MatchFailure(input, offset, offset, this, null, null);
		}
	}
	@Override
	public String toString(){
		return this.getClass().getName();
	}
}
