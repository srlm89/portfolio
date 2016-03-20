package xxl.java.container.table;

import static java.lang.String.format;

import java.util.*;

public class Table<R, C, V> {

	public static <R, C, V> Table<R, C, V> newTable(V defaultValue) {
		return new Table<R, C, V>(defaultValue);
	}

	public static <R, C, V> Table<R, C, V> newTable(Collection<? extends R> rows, Collection<? extends C> columns,
			V defaultValue) {
		Table<R, C, V> newTable = newTable(defaultValue);
		newTable.addRows(rows);
		newTable.addColumns(columns);
		return newTable;
	}

	private Table(V defaultValue) {
		this.defaultValue = defaultValue;
		this.cells = newLinkedHashMap();
		this.rows = new LinkedHashSet<R>();
		this.columns = new LinkedHashSet<C>();
	}

	public boolean isEmpty() {
		return numberOfRows() == 0 && numberOfColumns() == 0;
	}

	public Iterable<R> rows() {
		return rows;
	}

	public Iterable<C> columns() {
		return columns;
	}

	public V defaultValue() {
		return defaultValue;
	}

	public int numberOfRows() {
		return rows.size();
	}

	public int numberOfColumns() {
		return columns.size();
	}

	public int numberOfCells() {
		return cells.size();
	}

	public boolean hasRow(R row) {
		return rows.contains(row);
	}

	public boolean hasColumn(C column) {
		return columns.contains(column);
	}

	public Map<C, V> row(R row) {
		Map<C, V> rowValues = newLinkedHashMap();
		if (hasRow(row)) {
			for (C column : columns()) {
				rowValues.put(column, cell(row, column));
			}
		}
		return rowValues;
	}

	public Map<C, V> rowAddIfAbsent(R row) {
		if (!hasRow(row)) {
			addRow(row);
		}
		return row(row);
	}

	public Map<R, V> column(C column) {
		Map<R, V> columnValues = newLinkedHashMap();
		if (hasColumn(column)) {
			for (R row : rows()) {
				columnValues.put(row, cell(row, column));
			}
		}
		return columnValues;
	}

	public Map<R, V> columnAddIfAbsent(C column) {
		if (!hasColumn(column)) {
			addColumn(column);
		}
		return column(column);
	}

	public V put(R row, C column, V value) {
		addRow(row);
		addColumn(column);
		return cells.put(entry(row, column), value);
	}

	public void putRow(R row, Map<C, V> columns) {
		for (C column : columns.keySet()) {
			put(row, column, columns.get(column));
		}
	}

	public void putColumn(C column, Map<R, V> rows) {
		for (R row : rows.keySet()) {
			put(row, column, rows.get(row));
		}
	}

	public V cell(R row, C column) {
		return cells.get(entry(row, column));
	}

	public void addRows(Collection<? extends R> rows) {
		for (R row : rows) {
			addRow(row);
		}
	}

	public boolean addRow(R row) {
		if (!hasRow(row)) {
			rows.add(row);
			for (C column : columns()) {
				addCell(row, column, defaultValue);
			}
			return true;
		}
		return false;
	}

	public boolean removeRow(R row) {
		if (hasRow(row)) {
			rows.remove(row);
			for (C column : columns()) {
				removeCell(row, column);
			}
			clearIfNoCells();
			return true;
		}
		return false;
	}

	public void addColumns(Collection<? extends C> columns) {
		for (C column : columns) {
			addColumn(column);
		}
	}

	public boolean addColumn(C column) {
		if (!hasColumn(column)) {
			columns.add(column);
			for (R row : rows()) {
				addCell(row, column, defaultValue);
			}
			return true;
		}
		return false;
	}

	public boolean removeColumn(C column) {
		if (hasColumn(column)) {
			columns.remove(column);
			for (R row : rows()) {
				removeCell(row, column);
			}
			clearIfNoCells();
			return true;
		}
		return false;
	}

	public void clear() {
		rows.clear();
		columns.clear();
		cells.clear();
	}

	@Override
	public String toString() {
		return format("Table[%d rows; %d columns]", numberOfRows(), numberOfColumns());
	}

	private <A, B> Map<A, B> newLinkedHashMap() {
		return new LinkedHashMap<A, B>();
	}

	private void addCell(R row, C column, V value) {
		cells.put(entry(row, column), value);
	}

	private void removeCell(R row, C column) {
		cells.remove(entry(row, column));
	}

	private TableEntry<R, C> entry(R row, C column) {
		return TableEntry.from(row, column);
	}

	private void clearIfNoCells() {
		if (numberOfCells() == 0) {
			clear();
		}
	}

	private V defaultValue;
	private Collection<R> rows;
	private Collection<C> columns;
	private Map<TableEntry<R, C>, V> cells;
}
