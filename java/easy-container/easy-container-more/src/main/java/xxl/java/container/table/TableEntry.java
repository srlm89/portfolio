package xxl.java.container.table;

import static java.lang.String.format;


public class TableEntry<R, C> {

	public static <R, C> TableEntry<R, C> from(R row, C column) {
		return new TableEntry<R, C>(row, column);
	}
	
	private TableEntry(R row, C column) {
		this.row = row;
		this.column = column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((column == null) ? 0 : column.hashCode());
		result = prime * result + ((row == null) ? 0 : row.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TableEntry<?, ?> other = (TableEntry<?, ?>) obj;
		if (column == null) {
			if (other.column != null)
				return false;
		}
		else if (!column.equals(other.column))
			return false;
		if (row == null) {
			if (other.row != null)
				return false;
		}
		else if (!row.equals(other.row))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return format("%s [row=%s, column=%s]", TableEntry.class.getSimpleName(), row, column);
	}

	private R row;
	private C column;
}
