/**
 *
 */
package steven.grammar.oracle.util;

import java.util.ArrayList;
import java.util.List;

import steven.grammar.oracle.vo.Comment;
import steven.grammar.oracle.vo.NonQuotedIdentifier;
import steven.grammar.oracle.vo.QuotedIdentifier;
import steven.grammar.oracle.vo.Space;
import steven.grammar.vo.LiteralToken;
import steven.grammar.vo.MatchFailure;
import steven.grammar.vo.MatchResult;
import steven.grammar.vo.OptionToken;
import steven.grammar.vo.RecursiveClause;
import steven.grammar.vo.SequentialClause;
import steven.grammar.vo.Token;
import steven.grammar.vo.TokenWrapper;

/**
 * @author steven.lam.t.f
 *
 */
public class OracleGrammarUtils{
	public static final Token OPTIONAL_SPACE_OR_COMMENT = new TokenWrapper("OPTIONAL_SPACE_OR_COMMENT", new OptionToken(new RecursiveClause(new OptionToken(Space.INSTANCE, Comment.INSTANCE), null), OptionToken.OPTIONAL));
	public static final Token IDENTIFIER = new TokenWrapper("IDENTIFIER", OracleGrammarUtils.buildOptions(QuotedIdentifier.INSTANCE, NonQuotedIdentifier.INSTANCE));
	public static final Token SCHEMA = new TokenWrapper("SCHEMA", OracleGrammarUtils.IDENTIFIER);
	public static final Token OBJECT = new TokenWrapper("OBJECT", OracleGrammarUtils.IDENTIFIER);
	public static final Token PART = new TokenWrapper("PART", OracleGrammarUtils.IDENTIFIER);
	public static final Token DBLINK = new TokenWrapper("DBLINK", OracleGrammarUtils.buildRecursive(OracleGrammarUtils.IDENTIFIER, LiteralToken.DOT));
	public static final Token DATABASE_OBJECT_OR_PART = new TokenWrapper("DATABASE_OBJECT_OR_PART", OracleGrammarUtils.buildSequential(OracleGrammarUtils.buildOptions(OracleGrammarUtils.buildSequential(OracleGrammarUtils.SCHEMA, LiteralToken.DOT), OptionToken.OPTIONAL), OracleGrammarUtils.OBJECT, OracleGrammarUtils.buildOptions(OracleGrammarUtils.buildSequential(LiteralToken.DOT, OracleGrammarUtils.PART), OptionToken.OPTIONAL), OracleGrammarUtils.buildOptions(OracleGrammarUtils.buildSequential(LiteralToken.AT, OracleGrammarUtils.DBLINK), OptionToken.OPTIONAL)));
	// select
	public static final Token SELECT = new TokenWrapper("SELECT");
	public static final Token SUBQUERY = new TokenWrapper("SUBQUERY");
	public static final Token FOR_UPDATE_CLAUSE = new TokenWrapper("FOR_UPDATE_CLAUSE");

	private OracleGrammarUtils(){
	}
	public static final Token buildSequential(final Token... tokens){
		final Token[] array = new Token[tokens.length * 2 + 1];
		array[0] = OracleGrammarUtils.OPTIONAL_SPACE_OR_COMMENT;
		for(int i = 0, j = 1; i < tokens.length; i++){
			array[j++] = tokens[i];
			array[j++] = OracleGrammarUtils.OPTIONAL_SPACE_OR_COMMENT;
		}
		return new SequentialClause(array);
	}
	public static final Token buildOptions(final Token... options){
		return new OptionToken(options);
	}
	public static final Token buildRecursive(final Token body, final Token appendWhenLoop){
		if(appendWhenLoop == null || appendWhenLoop == OptionToken.OPTIONAL){
			return new RecursiveClause(body, OracleGrammarUtils.OPTIONAL_SPACE_OR_COMMENT);
		}else{
			return new RecursiveClause(body, new SequentialClause(OracleGrammarUtils.OPTIONAL_SPACE_OR_COMMENT, appendWhenLoop, OracleGrammarUtils.OPTIONAL_SPACE_OR_COMMENT));
		}
	}
	public static final void dumpResult(final MatchResult result){
		if(result.isMatched()){
			System.out.println(result.getInput());
			OracleGrammarUtils.internalDumpResult("", result);
		}else{
			OracleGrammarUtils.dumpFailure((MatchFailure)result);
		}
	}
	public static final void dumpFailure(MatchFailure failure){
		System.out.println(failure.getInput());
		final List<String> lines = new ArrayList<>();
		int lastEnd = 0;
		String lastName = null;
		while(failure != null){
			lastEnd = failure.getEnd();
			lines.add("at " + lastEnd + ": " + failure.getToken());
			if(failure.getToken() instanceof TokenWrapper){
				lastName = ((TokenWrapper)failure.getToken()).getName();
			}
			failure = failure.getChild();
		}
		for(int i = 0; i < lastEnd; i++){
			System.out.print(" ");
		}
		System.out.println("^");
		if(lastName != null){
			System.out.println("Expecting " + lastName + ":");
		}
		while(lines.size() > 0){
			System.out.println(lines.remove(lines.size() - 1));
		}
	}
	private static final void internalDumpResult(final String indent, final MatchResult result){
		if(result.getStart() != result.getEnd()){
			System.out.println(indent + "at [" + result.getStart() + "," + result.getEnd() + "]: " + result.getToken());
			if(result.getChildren() != null && result.getChildren().length > 0){
				final String newIndent = indent + "\t";
				for(final MatchResult child : result.getChildren()){
					OracleGrammarUtils.internalDumpResult(newIndent, child);
				}
			}
		}
	}
}
