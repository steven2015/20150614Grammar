/**
 *
 */
package steven.sth;

import steven.grammar.oracle.util.OracleGrammarUtils;
import steven.grammar.oracle.vo.ReservedWord;
import steven.grammar.vo.MatchResult;
import steven.grammar.vo.Token;

/**
 * @author steven.lam.t.f
 *
 */
public class S20150615Oracle{
	public static final void main(final String[] args){
		final Token select = OracleGrammarUtils.buildSequential(ReservedWord.SELECT, OracleGrammarUtils.DATABASE_OBJECT_OR_PART, ReservedWord.FROM, OracleGrammarUtils.DATABASE_OBJECT_OR_PART);
		final MatchResult result = select.matches("select abc from dual", 0);
		if(result.isMatched()){
			System.out.println("MATCHED");
		}else{
			System.out.println("NOT_MATCHED");
		}
		OracleGrammarUtils.dumpResult(result);
	}
}
