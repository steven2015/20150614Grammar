/**
 *
 */
package steven.sth;

import steven.grammar.oracle.vo.ReservedWord;
import steven.grammar.oracle.vo.TextLiteral;
import steven.grammar.vo.MatchFailure;
import steven.grammar.vo.MatchResult;
import steven.grammar.vo.SequentialClause;
import steven.grammar.vo.Token;

/**
 * @author steven.lam.t.f
 *
 */
public class S20150615Oracle{
	public static final void main(final String[] args){
		final Token select = new SequentialClause(ReservedWord.SELECT, TextLiteral.INSTANCE, ReservedWord.FROM, TextLiteral.INSTANCE);
		final MatchResult result = select.matches("select'bb'from dual", 0);
		if(result.isMatched()){
			System.out.println("MATCHED");
		}else{
			System.out.println("NOT_MATCHED");
			System.out.println(result.getInput());
			MatchFailure failure = (MatchFailure)result;
			int lastEnd=0;
			while(failure != null){
				lastEnd=failure.getEnd();
				System.out.println("at " + lastEnd + ": " + failure.getToken());
				failure = failure.getChild();
			}
		}
	}
}
