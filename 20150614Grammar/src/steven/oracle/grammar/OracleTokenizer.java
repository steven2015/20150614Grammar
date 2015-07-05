/**
 *
 */
package steven.oracle.grammar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import steven.grammar.Token;
import steven.grammar.Tokenizer;
import steven.grammar.exception.GrammarException;

/**
 * @author Steven
 *
 */
public class OracleTokenizer implements Tokenizer{
	private final String statement;
	private final int length;
	private final int[] startIndexOfLines;
	private final Token[] tokens;
	private final State state;

	public OracleTokenizer(final String statement) throws GrammarException{
		this.statement = statement;
		this.length = statement.length();
		{
			final List<Integer> list = new ArrayList<>();
			list.add(0);
			int index = 0;
			while(index < this.length){
				final int r = this.statement.indexOf('\r', index);
				final int n = this.statement.indexOf('\n', index);
				if(r < 0) {
					if(n < 0) {
						break;
					}else{
						index = n + 1;
					}
				}else{
					if(n < 0) {
						index = r + 1;
					}else if(r + 1 == n) {
						index = n + 1;
					}else if(r < n) {
						index = r + 1;
					}else{
						index = n + 1;
					}
				}
				list.add(index);
			}
			this.startIndexOfLines = new int[list.size()];
			for(int i = 0; i < this.startIndexOfLines.length; i++){
				this.startIndexOfLines[i] = list.get(i);
			}
		}
		{
			int index = 0;
			final List<Token> tokens = new ArrayList<>();
			while(index < this.length){
				final Token token = this.next(index);
				if(token == null) {
					break;
				}else{
					tokens.add(token);
					index = token.getEndIndex();
				}
			}
			this.tokens = tokens.toArray(new Token[tokens.size()]);
		}
		this.state = new State(0);
	}
	private OracleTokenizer(){
		this.statement = null;
		this.length = 0;
		this.startIndexOfLines = null;
		this.tokens = null;
		this.state = null;
	}
	@Override
	public Token next(){
		return this.state.next();
	}
	private Token next(final int currentIndex) throws GrammarException{
		int index = currentIndex;
		// space
		while(index < this.length){
			final char c = this.statement.charAt(index);
			if(c == ' ' || c == '\t' || c == '\r' || c == '\n') {
				index++;
			}else if(c == '-') {
				if(index + 1 < this.length && this.statement.charAt(index + 1) == '-') {
					final int r = this.statement.indexOf('\r', index + 2);
					final int n = this.statement.indexOf('\n', index + 2);
					if(r < 0) {
						if(n < 0) {
							index = this.length;
						}else{
							index = n + 1;
						}
					}else{
						if(n < 0) {
							index = r + 1;
						}else if(r + 1 == n) {
							index = n + 1;
						}else if(r < n) {
							index = r + 1;
						}else{
							index = n + 1;
						}
					}
				}else{
					return new Token(this.statement, index, index + 1);
				}
			}else if(c == '/') {
				if(index + 1 < this.length && this.statement.charAt(index + 1) == '*') {
					final int end = this.statement.indexOf("*/", index + 2);
					if(end < 0) {
						this.throwException(index, "No end symbol (*/) for comment.");
					}else{
						index = end + 2;
					}
				}else{
					return new Token(this.statement, index, index + 1);
				}
			}else{
				break;
			}
		}
		// content
		if(index < this.length) {
			final char c = this.statement.charAt(index);
			if(c == '\'') {
				int q = this.statement.indexOf('\'', index + 1);
				while(true){
					if(q < 0) {
						this.throwException(index, "No end symbol (') for string literal.");
					}else if(q + 1 < this.length && this.statement.charAt(q + 1) == '\'') {
						q = this.statement.indexOf('\'', q + 2);
					}else{
						return new StringLiteral(this.statement, index, q + 1);
					}
				}
			}else if(c == '\"') {
				final int q = this.statement.indexOf('\"', index + 1);
				if(q < 0) {
					this.throwException(index, "No end symbol (\") for string literal.");
				}else{
					return new QuotedIdentifier(this.statement, index, q + 1);
				}
			}else if(c >= '0' && c <= '9') {
				int i = this.getEndIndexOfNumberDigits(index + 1);
				if(i >= this.length) {
					return new NumberLiteral(this.statement, index, i);
				}else{
					if(this.statement.charAt(i) == '.') {
						i = this.getEndIndexOfNumberDigits(i + 1);
					}
					i = this.getEndIndexOfNumberPostfix(i);
					return new NumberLiteral(this.statement, index, i);
				}
			}else if(c == '.') {
				if(index + 1 < this.length) {
					final char c2 = this.statement.charAt(index + 1);
					if(c2 >= '0' && c2 <= '9') {
						int i = this.getEndIndexOfNumberDigits(index + 2);
						i = this.getEndIndexOfNumberPostfix(i);
						return new NumberLiteral(this.statement, index, i);
					}
				}
				return new Token(this.statement, index, index + 1);
			}else if(c == '+' || c == '-') {
				if(index + 1 < this.length) {
					final char c2 = this.statement.charAt(index + 1);
					if(c2 >= '0' && c2 <= '9') {
						int i = this.getEndIndexOfNumberDigits(index + 2);
						if(i >= this.length) {
							return new NumberLiteral(this.statement, index, i);
						}else{
							if(this.statement.charAt(i) == '.') {
								i = this.getEndIndexOfNumberDigits(i + 1);
							}
							i = this.getEndIndexOfNumberPostfix(i);
							return new NumberLiteral(this.statement, index, i);
						}
					}
				}
				return new Token(this.statement, index, index + 1);
			}else if(c == '|') {
				if(index + 1 < this.length && this.statement.charAt(index + 1) == '|') {
					return new Token(this.statement, index, index + 2);
				}else{
					this.throwException(index, "Unknown symbol |.");
				}
			}else if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
				int i = index + 1;
				while(i < this.length){
					final char c2 = this.statement.charAt(i);
					if((c2 >= 'a' && c2 <= 'z') || (c2 >= 'A' && c2 <= 'Z')) {
						i++;
					}else{
						break;
					}
				}
				return new Token(this.statement, index, i);
			}else if(c == '*' || c == '(' || c == ')') {
				return new Token(this.statement, index, index + 1);
			}else{
				this.throwException(index, "Unknown symbol " + c + ".");
			}
		}
		return null;
	}
	@Override
	public Tokenizer cloneCurrentState(){
		return this.state.cloneCurrentState();
	}
	public Token nextHints(final int currentIndex) throws GrammarException{
		int index = currentIndex;
		// space
		while(index < this.length){
			final char c = this.statement.charAt(index);
			if(c == ' ' || c == '\t' || c == '\r' || c == '\n') {
				index++;
			}else if(c == '-') {
				if(index + 1 < this.length && this.statement.charAt(index + 1) == '-') {
					final int r = this.statement.indexOf('\r', index + 2);
					final int n = this.statement.indexOf('\n', index + 2);
					if(r < 0) {
						if(n < 0) {
							index = this.length;
						}else{
							index = n + 1;
						}
					}else{
						if(n < 0) {
							index = r + 1;
						}else if(r + 1 == n) {
							index = n + 1;
						}else if(r < n) {
							index = r + 1;
						}else{
							index = n + 1;
						}
					}
				}else{
					return null;
				}
			}else if(c == '/') {
				if(index + 1 < this.length && this.statement.charAt(index + 1) == '*') {
					if(index + 2 < this.length && this.statement.charAt(index + 2) == '+') {
						final int end = this.statement.indexOf("*/", index + 3);
						if(end < 0) {
							this.throwException(index, "No end symbol (*/) for hints.");
						}else{
							return new Hints(this.statement, index, end + 2);
						}
					}else{
						final int end = this.statement.indexOf("*/", index + 2);
						if(end < 0) {
							this.throwException(index, "No end symbol (*/) for comment.");
						}else{
							index = end + 2;
						}
					}
				}else{
					return null;
				}
			}else{
				return null;
			}
		}
		return null;
	}
	private int getEndIndexOfNumberDigits(final int index){
		int i = index;
		while(i < this.length){
			final char c = this.statement.charAt(i);
			if(c >= '0' && c <= '9') {
				i++;
			}else{
				break;
			}
		}
		return i;
	}
	private int getEndIndexOfNumberPostfix(final int index) throws GrammarException{
		int i = index;
		if(i >= this.length) {
			return i;
		}
		if(Character.toUpperCase(this.statement.charAt(i)) == 'E') {
			i++;
			if(i >= this.length) {
				this.throwException(index, "Invalid number literal.");
			}
			{
				final char c = this.statement.charAt(i);
				if(c == '+' || c == '-') {
					i++;
				}
			}
			boolean hasDigit = false;
			while(i < this.length){
				final char c = this.statement.charAt(i);
				if(c >= '0' && c <= '9') {
					i++;
					hasDigit = true;
				}else{
					break;
				}
			}
			if(hasDigit == false) {
				this.throwException(index, "Invalid number literal.");
			}
		}
		if(i >= this.length) {
			return i;
		}
		final char c = this.statement.charAt(i);
		if(c == 'f' || c == 'F' || c == 'd' || c == 'D') {
			i++;
		}
		return i;
	}
	private void throwException(final int index, final String message) throws GrammarException{
		int lineIndex = Arrays.binarySearch(this.startIndexOfLines, index);
		if(lineIndex < 0) {
			lineIndex = -lineIndex - 2;
		}
		final int position = index - this.startIndexOfLines[lineIndex] + 1;
		throw new GrammarException(lineIndex + 1, position, message);
	}

	private final class State extends OracleTokenizer{
		private int nextTokenIndex;

		private State(final int nextTokenIndex){
			this.nextTokenIndex = nextTokenIndex;
		}
		@Override
		public Token next(){
			if(this.nextTokenIndex >= OracleTokenizer.this.tokens.length) {
				return null;
			}else{
				return OracleTokenizer.this.tokens[this.nextTokenIndex++];
			}
		}
		@Override
		public Tokenizer cloneCurrentState(){
			return new State(this.nextTokenIndex);
		}
		@Override
		public Token nextHints(final int currentIndex) throws GrammarException{
			return OracleTokenizer.this.nextHints(currentIndex);
		}
	}
}
