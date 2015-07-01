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
	private int currentIndex;
	private Token peek;

	public OracleTokenizer(final String statement){
		this.statement = statement;
		this.length = statement.length();
		this.currentIndex = 0;
		this.peek = null;
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
	}
	private OracleTokenizer(final String statement, final int length, final int[] startIndexOfLines, final int currentIndex, final Token peek){
		this.statement = statement;
		this.length = length;
		this.startIndexOfLines = startIndexOfLines;
		this.currentIndex = currentIndex;
		this.peek = peek;
	}
	@Override
	public Token next() throws GrammarException{
		if(this.peek == null) {
			this.peek();
		}
		if(this.peek == null) {
			return null;
		}else{
			this.currentIndex = this.peek.getEndIndex();
			final Token tmp = this.peek;
			this.peek = null;
			return tmp;
		}
	}
	@Override
	public Token peek() throws GrammarException{
		if(this.peek == null) {
			int index = this.currentIndex;
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
						this.peek = new Token(this.statement, index, index + 1);
						break;
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
						this.peek = new Token(this.statement, index, index + 1);
						break;
					}
				}else{
					break;
				}
			}
			// content
			if(this.peek == null && index < this.length) {
				final char c = this.statement.charAt(index);
				if(c == '\'') {
					int q = this.statement.indexOf('\'', index + 1);
					while(true){
						if(q < 0) {
							this.throwException(index, "No end symbol (') for string literal.");
						}else if(q + 1 < this.length && this.statement.charAt(q + 1) == '\'') {
							q = this.statement.indexOf('\'', q + 2);
						}else{
							this.peek = new StringLiteral(this.statement, index, q + 1);
							break;
						}
					}
				}else if(c == '\"') {
					final int q = this.statement.indexOf('\"', index + 1);
					if(q < 0) {
						this.throwException(index, "No end symbol (\") for string literal.");
					}else{
						this.peek = new QuotedIdentifier(this.statement, index, q + 1);
					}
				}else if(c >= '0' && c <= '9') {
					int i = this.getEndIndexOfNumberDigits(index + 1);
					if(i >= this.length) {
						this.peek = new NumberLiteral(this.statement, index, i);
					}else{
						if(this.statement.charAt(i) == '.') {
							i = this.getEndIndexOfNumberDigits(i + 1);
						}
						i = this.getEndIndexOfNumberPostfix(i);
						this.peek = new NumberLiteral(this.statement, index, i);
					}
				}else if(c == '.') {
					if(index + 1 < this.length) {
						final char c2 = this.statement.charAt(index + 1);
						if(c2 >= '0' && c2 <= '9') {
							int i = this.getEndIndexOfNumberDigits(index + 2);
							i = this.getEndIndexOfNumberPostfix(i);
							this.peek = new NumberLiteral(this.statement, index, i);
						}
					}
					if(this.peek == null) {
						this.peek = new Token(this.statement, index, index + 1);
					}
				}else if(c == '+' || c == '-') {
					if(index + 1 < this.length) {
						final char c2 = this.statement.charAt(index + 1);
						if(c2 >= '0' && c2 <= '9') {
							int i = this.getEndIndexOfNumberDigits(index + 2);
							if(i >= this.length) {
								this.peek = new NumberLiteral(this.statement, index, i);
							}else{
								if(this.statement.charAt(i) == '.') {
									i = this.getEndIndexOfNumberDigits(i + 1);
								}
								i = this.getEndIndexOfNumberPostfix(i);
								this.peek = new NumberLiteral(this.statement, index, i);
							}
						}
					}
					if(this.peek == null) {
						this.peek = new Token(this.statement, index, index + 1);
					}
				}else if(c == '|') {
					if(index + 1 < this.length && this.statement.charAt(index + 1) == '|') {
						this.peek = new Token(this.statement, index, index + 2);
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
					this.peek = new Token(this.statement, index, i);
				}else if(c == '*' || c == '(' || c == ')') {
					this.peek = new Token(this.statement, index, index + 1);
				}else{
					this.throwException(index, "Unknown symbol " + c + ".");
				}
			}
		}
		return this.peek;
	}
	@Override
	public Tokenizer cloneCurrentState(){
		return new OracleTokenizer(this.statement, this.length, this.startIndexOfLines, this.currentIndex, this.peek);
	}
	public Token nextHints() throws GrammarException{
		final Token hints = this.peekHints();
		if(hints == null) {
			return null;
		}else{
			this.currentIndex = hints.getEndIndex();
			return hints;
		}
	}
	public Token peekHints() throws GrammarException{
		int index = this.currentIndex;
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
}
