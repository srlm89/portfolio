package xxl.java.container.table;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class TableEntryTest {

	@Test
	@SuppressWarnings("rawtypes")
	public void tableEntryHashCodeAndEquals() {
		TableEntry entryA = TableEntry.from(2, ".");
		TableEntry entryB = TableEntry.from(2, ".");
		TableEntry entryC = TableEntry.from(3, ".");
		TableEntry entryD = TableEntry.from(2, ",");
		TableEntry entryE = TableEntry.from(null, ".");
		TableEntry entryF = TableEntry.from(2, null);
		TableEntry entryG = TableEntry.from(null, null);

		assertEquals(entryA, entryB);
		assertEquals(entryA.hashCode(), entryB.hashCode());

		assertFalse(entryA.equals(entryC));
		assertFalse(entryC.equals(entryA));

		assertFalse(entryA.equals(entryD));
		assertFalse(entryD.equals(entryA));

		assertFalse(entryA.equals(entryE));
		assertFalse(entryE.equals(entryA));

		assertFalse(entryA.equals(entryF));
		assertFalse(entryF.equals(entryA));

		assertFalse(entryA.equals(entryG));
		assertFalse(entryG.equals(entryA));
	}

	@Test
	@SuppressWarnings("rawtypes")
	public void tableEntryToString() {
		TableEntry entryA = TableEntry.from(2, true);
		TableEntry entryB = TableEntry.from(null, false);
		TableEntry entryC = TableEntry.from(2, null);
		TableEntry entryD = TableEntry.from(null, null);

		assertEquals("TableEntry [row=2, column=true]", entryA.toString());
		assertEquals("TableEntry [row=null, column=false]", entryB.toString());
		assertEquals("TableEntry [row=2, column=null]", entryC.toString());
		assertEquals("TableEntry [row=null, column=null]", entryD.toString());
	}
}
