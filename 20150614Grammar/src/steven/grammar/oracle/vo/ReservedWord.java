/**
 *
 */
package steven.grammar.oracle.vo;

import steven.grammar.vo.LiteralToken;
import steven.grammar.vo.Token;

/**
 * @author steven.lam.t.f
 *
 */
public interface ReservedWord{
	public static final Token ACCESS = new LiteralToken("ACCESS", false);
	public static final Token ADD = new LiteralToken("ADD", false);
	public static final Token ALL = new LiteralToken("ALL", false);
	public static final Token ALTER = new LiteralToken("ALTER", false);
	public static final Token AND = new LiteralToken("AND", false);
	public static final Token ANY = new LiteralToken("ANY", false);
	public static final Token AS = new LiteralToken("AS", false);
	public static final Token ASC = new LiteralToken("ASC", false);
	public static final Token AUDIT = new LiteralToken("AUDIT", false);
	public static final Token BETWEEN = new LiteralToken("BETWEEN", false);
	public static final Token BY = new LiteralToken("BY", false);
	public static final Token CHAR = new LiteralToken("CHAR", false);
	public static final Token CHECK = new LiteralToken("CHECK", false);
	public static final Token CLUSTER = new LiteralToken("CLUSTER", false);
	public static final Token COLUMN = new LiteralToken("COLUMN", false);
	public static final Token COLUMN_VALUE = new LiteralToken("COLUMN_VALUE", false);
	public static final Token COMMENT = new LiteralToken("COMMENT", false);
	public static final Token COMPRESS = new LiteralToken("COMPRESS", false);
	public static final Token CONNECT = new LiteralToken("CONNECT", false);
	public static final Token CREATE = new LiteralToken("CREATE", false);
	public static final Token CURRENT = new LiteralToken("CURRENT", false);
	public static final Token DATE = new LiteralToken("DATE", false);
	public static final Token DECIMAL = new LiteralToken("DECIMAL", false);
	public static final Token DEFAULT = new LiteralToken("DEFAULT", false);
	public static final Token DELETE = new LiteralToken("DELETE", false);
	public static final Token DESC = new LiteralToken("DESC", false);
	public static final Token DISTINCT = new LiteralToken("DISTINCT", false);
	public static final Token DROP = new LiteralToken("DROP", false);
	public static final Token ELSE = new LiteralToken("ELSE", false);
	public static final Token EXCLUSIVE = new LiteralToken("EXCLUSIVE", false);
	public static final Token EXISTS = new LiteralToken("EXISTS", false);
	public static final Token FILE = new LiteralToken("FILE", false);
	public static final Token FLOAT = new LiteralToken("FLOAT", false);
	public static final Token FOR = new LiteralToken("FOR", false);
	public static final Token FROM = new LiteralToken("FROM", false);
	public static final Token GRANT = new LiteralToken("GRANT", false);
	public static final Token GROUP = new LiteralToken("GROUP", false);
	public static final Token HAVING = new LiteralToken("HAVING", false);
	public static final Token IDENTIFIED = new LiteralToken("IDENTIFIED", false);
	public static final Token IMMEDIATE = new LiteralToken("IMMEDIATE", false);
	public static final Token IN = new LiteralToken("IN", false);
	public static final Token INCREMENT = new LiteralToken("INCREMENT", false);
	public static final Token INDEX = new LiteralToken("INDEX", false);
	public static final Token INITIAL = new LiteralToken("INITIAL", false);
	public static final Token INSERT = new LiteralToken("INSERT", false);
	public static final Token INTEGER = new LiteralToken("INTEGER", false);
	public static final Token INTERSECT = new LiteralToken("INTERSECT", false);
	public static final Token INTO = new LiteralToken("INTO", false);
	public static final Token IS = new LiteralToken("IS", false);
	public static final Token LEVEL = new LiteralToken("LEVEL", false);
	public static final Token LIKE = new LiteralToken("LIKE", false);
	public static final Token LOCK = new LiteralToken("LOCK", false);
	public static final Token LONG = new LiteralToken("LONG", false);
	public static final Token MAXEXTENTS = new LiteralToken("MAXEXTENTS", false);
	public static final Token MINUS = new LiteralToken("MINUS", false);
	public static final Token MLSLABEL = new LiteralToken("MLSLABEL", false);
	public static final Token MODE = new LiteralToken("MODE", false);
	public static final Token MODIFY = new LiteralToken("MODIFY", false);
	public static final Token NESTED_TABLE_ID = new LiteralToken("NESTED_TABLE_ID", false);
	public static final Token NOAUDIT = new LiteralToken("NOAUDIT", false);
	public static final Token NOCOMPRESS = new LiteralToken("NOCOMPRESS", false);
	public static final Token NOT = new LiteralToken("NOT", false);
	public static final Token NOWAIT = new LiteralToken("NOWAIT", false);
	public static final Token NULL = new LiteralToken("NULL", false);
	public static final Token NUMBER = new LiteralToken("NUMBER", false);
	public static final Token OF = new LiteralToken("OF", false);
	public static final Token OFFLINE = new LiteralToken("OFFLINE", false);
	public static final Token ON = new LiteralToken("ON", false);
	public static final Token ONLINE = new LiteralToken("ONLINE", false);
	public static final Token OPTION = new LiteralToken("OPTION", false);
	public static final Token OR = new LiteralToken("OR", false);
	public static final Token ORDER = new LiteralToken("ORDER", false);
	public static final Token PCTFREE = new LiteralToken("PCTFREE", false);
	public static final Token PRIOR = new LiteralToken("PRIOR", false);
	public static final Token PUBLIC = new LiteralToken("PUBLIC", false);
	public static final Token RAW = new LiteralToken("RAW", false);
	public static final Token RENAME = new LiteralToken("RENAME", false);
	public static final Token RESOURCE = new LiteralToken("RESOURCE", false);
	public static final Token REVOKE = new LiteralToken("REVOKE", false);
	public static final Token ROW = new LiteralToken("ROW", false);
	public static final Token ROWID = new LiteralToken("ROWID", false);
	public static final Token ROWNUM = new LiteralToken("ROWNUM", false);
	public static final Token ROWS = new LiteralToken("ROWS", false);
	public static final Token SELECT = new LiteralToken("SELECT", false);
	public static final Token SESSION = new LiteralToken("SESSION", false);
	public static final Token SET = new LiteralToken("SET", false);
	public static final Token SHARE = new LiteralToken("SHARE", false);
	public static final Token SIZE = new LiteralToken("SIZE", false);
	public static final Token SMALLINT = new LiteralToken("SMALLINT", false);
	public static final Token START = new LiteralToken("START", false);
	public static final Token SUCCESSFUL = new LiteralToken("SUCCESSFUL", false);
	public static final Token SYNONYM = new LiteralToken("SYNONYM", false);
	public static final Token SYSDATE = new LiteralToken("SYSDATE", false);
	public static final Token TABLE = new LiteralToken("TABLE", false);
	public static final Token THEN = new LiteralToken("THEN", false);
	public static final Token TO = new LiteralToken("TO", false);
	public static final Token TRIGGER = new LiteralToken("TRIGGER", false);
	public static final Token UID = new LiteralToken("UID", false);
	public static final Token UNION = new LiteralToken("UNION", false);
	public static final Token UNIQUE = new LiteralToken("UNIQUE", false);
	public static final Token UPDATE = new LiteralToken("UPDATE", false);
	public static final Token USER = new LiteralToken("USER", false);
	public static final Token VALIDATE = new LiteralToken("VALIDATE", false);
	public static final Token VALUES = new LiteralToken("VALUES", false);
	public static final Token VARCHAR = new LiteralToken("VARCHAR", false);
	public static final Token VARCHAR2 = new LiteralToken("VARCHAR2", false);
	public static final Token VIEW = new LiteralToken("VIEW", false);
	public static final Token WHENEVER = new LiteralToken("WHENEVER", false);
	public static final Token WHERE = new LiteralToken("WHERE", false);
	public static final Token WITH = new LiteralToken("WITH", false);
}
