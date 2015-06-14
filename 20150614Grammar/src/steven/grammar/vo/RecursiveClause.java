/**
 *
 */
package steven.grammar.vo;

/**
 * @author Steven
 *
 */
public class RecursiveClause implements Token{
	private final Token body;
	private final Token appendWhenLoop;

	public RecursiveClause(Token body, Token appendWhenLoop){
		this.body = body;
		this.appendWhenLoop = appendWhenLoop;
	}
	@Override
	public int matches(String[] input, int offset){
		int newOffset = offset;
		while(true){
			int tmp = body.matches(input, newOffset);
			if(tmp < 0){
				if(newOffset == offset){
					return -1;
				}else{
					return newOffset;
				}
			}else{
				newOffset = tmp;
			}
			int tmp2 = appendWhenLoop.matches(input, newOffset);
			if(tmp2 < 0){
				return newOffset;
			}else{
				newOffset = tmp2;
			}
		}
	}
	public final Token getBody(){
		return body;
	}
	public final Token getAppendWhenLoop(){
		return appendWhenLoop;
	}
}
