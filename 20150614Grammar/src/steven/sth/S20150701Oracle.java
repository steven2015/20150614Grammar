/**
 *
 */
package steven.sth;

import steven.grammar.Token;
import steven.grammar.exception.GrammarException;
import steven.oracle.grammar.OracleTokenizer;

/**
 * @author Steven
 *
 */
public class S20150701Oracle{
	public static final void main(final String[] args){
		final String sql = "select trunc(sysdate)||'abc' as \"def\" \r\n123[ from dual";
		final OracleTokenizer tokenizer = new OracleTokenizer(sql);
		Token token = null;
		try{
			while((token = tokenizer.next()) != null){
				System.out.println(token.getClass() + ": " + token);
			}
		}catch(final GrammarException e){
			e.printStackTrace();
		}
	}
}
