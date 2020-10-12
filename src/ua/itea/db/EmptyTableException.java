package ua.itea.db;

public class EmptyTableException extends Exception {
	
	public EmptyTableException(String tableName) {
		super("Table `" + tableName + "` is empty");
	}
}
