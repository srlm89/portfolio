package xxl.java.container.table;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.Test;

public class TableTest {

	@Test
	public void basicTableFuncionality() {
		Table<Integer, String, Boolean> table = Table.newTable(false);
		assertTrue(table.isEmpty());
		assertEquals(0, table.numberOfRows());
		assertEquals(0, table.numberOfColumns());
		table.put(1, "a", true);
		table.put(1, "b", false);
		table.put(2, "a", false);
		assertFalse(table.isEmpty());
		assertEquals(2, table.numberOfRows());
		assertEquals(2, table.numberOfColumns());
		assertEquals(4, table.numberOfCells());
		assertTrue(table.hasRow(1));
		assertTrue(table.hasRow(2));
		assertTrue(table.hasColumn("a"));
		assertTrue(table.hasColumn("b"));
		assertTrue(table.cell(1, "a"));
		assertFalse(table.cell(1, "b"));
		assertFalse(table.cell(2, "a"));
		assertFalse(table.cell(2, "b"));
	}

	@Test
	public void tableOfNumbers() {
		Table<Integer, Integer, Integer> table = Table.newTable(null);
		table.put(1, 0, 1);
		table.put(1, 1, 2);
		table.put(2, 1, 3);
		table.put(4, 1, 5);
		assertEquals(3, table.numberOfRows());
		assertEquals(2, table.numberOfColumns());
		ensureContainsAll(table.rows(), 1, 2, 4);
		ensureContainsAll(table.columns(), 0, 1);
		assertEquals(1, table.cell(1, 0).intValue());
		assertEquals(2, table.cell(1, 1).intValue());
		assertEquals(null, table.cell(2, 0));
		assertEquals(3, table.cell(2, 1).intValue());
		assertEquals(null, table.cell(4, 0));
		assertEquals(5, table.cell(4, 1).intValue());
		assertFalse(table.addRow(1));
		assertTrue(table.addRow(5));
		ensureContainsAll(table.rows(), 1, 2, 4, 5);
		ensureContainsAll(table.columns(), 0, 1);
		assertEquals(2, table.row(5).size());
		assertTrue(asList(null, null).containsAll(table.row(5).values()));
	}

	@Test
	public void removingColumns() {
		Table<Integer, Integer, Integer> table = Table.newTable(0);
		table.put(1, 1, 1);
		table.put(2, 2, 2);
		table.put(3, 3, 3);
		assertFalse(table.isEmpty());
		assertFalse(table.removeColumn(4));
		assertEquals(3, table.numberOfRows());
		assertEquals(3, table.numberOfColumns());
		assertTrue(table.removeColumn(2));
		assertEquals(3, table.numberOfRows());
		assertEquals(2, table.numberOfColumns());
		ensureContainsAll(table.rows(), 1, 2, 3);
		ensureContainsAll(table.columns(), 1, 3);
		assertTrue(table.removeColumn(3));
		assertEquals(3, table.numberOfRows());
		assertEquals(1, table.numberOfColumns());
		ensureContainsAll(table.rows(), 1, 2, 3);
		ensureContainsAll(table.columns(), 1);
		assertTrue(table.removeColumn(1));
		assertTrue(table.isEmpty());
		assertEquals(0, table.numberOfRows());
		assertEquals(0, table.numberOfColumns());
	}

	@Test
	public void removingRows() {
		Table<Integer, Integer, Integer> table = Table.newTable(0);
		table.put(1, 1, 1);
		table.put(2, 2, 2);
		table.put(3, 3, 3);
		assertFalse(table.isEmpty());
		assertFalse(table.removeRow(4));
		assertEquals(3, table.numberOfRows());
		assertEquals(3, table.numberOfColumns());
		assertTrue(table.removeRow(2));
		assertEquals(2, table.numberOfRows());
		assertEquals(3, table.numberOfColumns());
		ensureContainsAll(table.rows(), 1, 3);
		ensureContainsAll(table.columns(), 1, 2, 3);
		assertTrue(table.removeRow(3));
		assertEquals(1, table.numberOfRows());
		assertEquals(3, table.numberOfColumns());
		ensureContainsAll(table.rows(), 1);
		ensureContainsAll(table.columns(), 1, 2, 3);
		assertTrue(table.removeRow(1));
		assertTrue(table.isEmpty());
		assertEquals(0, table.numberOfRows());
		assertEquals(0, table.numberOfColumns());
	}

	@Test
	public void addingMultipleRows() {
		Table<Integer, Integer, Integer> table = Table.newTable(0);
		table.addRows(asList(1, 2, 3, 4, 2, 5));
		assertFalse(table.isEmpty());
		assertEquals(5, table.numberOfRows());
		assertEquals(0, table.numberOfColumns());
		assertEquals(0, table.numberOfCells());
		table.put(2, 2, 2);
		assertTrue(table.hasColumn(2));
		assertEquals(1, table.numberOfColumns());
		assertEquals(0, table.cell(1, 2).intValue());
		assertEquals(5, table.numberOfCells());
	}

	@Test
	public void addingMultipleColumns() {
		Table<Integer, Integer, Integer> table = Table.newTable(0);
		table.addColumns(asList(1, 2, 3, 4, 2, 5));
		assertFalse(table.isEmpty());
		assertEquals(0, table.numberOfRows());
		assertEquals(5, table.numberOfColumns());
		assertEquals(0, table.numberOfCells());
		table.put(2, 2, 2);
		assertTrue(table.hasRow(2));
		assertEquals(1, table.numberOfRows());
		assertEquals(0, table.cell(2, 1).intValue());
		assertEquals(5, table.numberOfCells());
	}

	@Test
	public void tableCreationWithRowsAndColumns() {
		Table<String, String, Integer> table = Table.newTable(asList("a", "b", "c"), asList("W", "X", "Y", "Z"), 0);
		assertFalse(table.isEmpty());
		assertEquals(3, table.numberOfRows());
		ensureContainsAll(table.rows(), "a", "b", "c");
		assertEquals(4, table.numberOfColumns());
		ensureContainsAll(table.columns(), "W", "X", "Y", "Z");
		assertEquals(0, table.cell("a", "X").intValue());
	}

	@Test
	public void defineAColumnOfATable() {
		Table<String, String, String> table = Table.newTable(".");
		Map<String, String> column = new HashMap<String, String>();
		column.put("a", "0");
		column.put("b", "1");
		column.put("c", "2");
		table.putColumn("Index", column);
		assertEquals(1, table.numberOfColumns());
		assertEquals(3, table.numberOfRows());
		assertEquals("0", table.cell("a", "Index"));
		assertEquals("1", table.cell("b", "Index"));
		assertEquals("2", table.cell("c", "Index"));
	}

	@Test
	public void defineARowOfATable() {
		Table<String, String, String> table = Table.newTable(".");
		Map<String, String> row = new HashMap<String, String>();
		row.put("Index", "0");
		row.put("Upper", "A");
		row.put("Next", "b");
		table.putRow("a", row);
		assertEquals(3, table.numberOfColumns());
		assertEquals(1, table.numberOfRows());
		assertEquals("0", table.cell("a", "Index"));
		assertEquals("A", table.cell("a", "Upper"));
		assertEquals("b", table.cell("a", "Next"));
	}

	@SuppressWarnings("unchecked")
	private <X> void ensureContainsAll(Iterable<X> iterable, X... elements) {
		Collection<X> fromIterable = new HashSet<X>();
		for (X item : iterable) {
			fromIterable.add(item);
		}
		Collection<X> fromArguments = asList(elements);
		assertTrue(fromIterable.containsAll(fromArguments));
	}
}
