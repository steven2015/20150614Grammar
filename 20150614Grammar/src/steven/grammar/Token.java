/**
 *
 */
package steven.grammar;

/**
 * @author Steven
 *
 */
public class Token{
	protected final int startIndex;
	protected final int endIndex;
	protected final int length;
	protected final String value;

	public Token(final String data, final int startIndex, final int endIndex){
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.length = endIndex - startIndex;
		this.value = data.substring(this.startIndex, this.endIndex);
	}
	public Token(final int offset, final String value){
		this.startIndex = offset;
		this.length = value.length();
		this.endIndex = offset + this.length;
		this.value = value;
	}
	@Override
	public String toString(){
		return this.value;
	}
	public boolean equals(final String anotherString){
		return this.value.equals(anotherString);
	}
	public boolean equalsIgnoreCase(final String anotherString){
		return this.value.equalsIgnoreCase(anotherString);
	}
	public final int getStartIndex(){
		return this.startIndex;
	}
	public final int getEndIndex(){
		return this.endIndex;
	}
	public final int getLength(){
		return this.length;
	}
	public final String getValue(){
		return this.value;
	}
}
