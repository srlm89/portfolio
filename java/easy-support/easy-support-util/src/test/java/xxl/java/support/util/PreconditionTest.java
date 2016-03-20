package xxl.java.support.util;

import static java.util.Arrays.asList;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;

public class PreconditionTest {

	@Test
	public void preconditionTrue() {
		try {
			Precondition.checkTrue(true, "");
		}
		catch (Throwable t) {
			fail();
		}
		try {
			Precondition.checkTrue(false, "");
			fail();
		}
		catch (Throwable t) {
			return;
		}
	}

	@Test
	public void preconditionFalse() {
		try {
			Precondition.checkFalse(false, "");
		}
		catch (Throwable t) {
			fail();
		}
		try {
			Precondition.checkFalse(true, "");
			fail();
		}
		catch (Throwable t) {
			return;
		}
	}

	@Test
	public void preconditionNotNull() {
		try {
			Precondition.checkNotNull(new Object());
		}
		catch (Throwable t) {
			fail();
		}
		try {
			Precondition.checkNotNull(null);
			fail();
		}
		catch (Throwable t) {
			return;
		}
	}

	@Test
	public void preconditionNull() {
		try {
			Precondition.checkNull(null);
		}
		catch (Throwable t) {
			fail();
		}
		try {
			Precondition.checkNull(new Object());
			fail();
		}
		catch (Throwable t) {
			return;
		}
	}

	@Test
	public void preconditionSameSize() {
		try {
			Precondition.checkSameSize(asList(), asList());
			Precondition.checkSameSize(asList(1, 2), asList(null, ""));
		}
		catch (Throwable t) {
			fail();
		}
		try {
			Precondition.checkSameSize(asList(), asList(new Object()));
			fail();
		}
		catch (Throwable t) {
			return;
		}
	}

	@Test
	public void preconditionContainsAll() {
		try {
			Precondition.checkContainsAll(asList(), asList());
			Precondition.checkContainsAll(asList(1, 2, 3, 4, null), asList(2, 4, null));
		}
		catch (Throwable t) {
			fail();
		}
		try {
			Precondition.checkContainsAll(asList(), asList(1));
			fail();
		}
		catch (Throwable t) {
			return;
		}
	}

	@Test
	public void preconditionEquallyRepeated() {
		try {
			Precondition.checkEquallyRepeated(0, asList(1), asList(2));
			Precondition.checkEquallyRepeated(1, asList(null, 1, 2, 1), asList(1, 1));
		}
		catch (Throwable t) {
			fail();
		}
		try {
			Precondition.checkEquallyRepeated(0, asList(0, 0, 0), asList(0, 0));
			fail();
		}
		catch (Throwable t) {
			return;
		}
	}

	@Test
	public void preconditionCollectionEquals() {
		Collection<Integer> set = new HashSet<Integer>();
		set.add(1);
		set.add(2);
		set.add(3);
		try {
			Precondition.checkEquals(asList(), asList());
			Precondition.checkEquals(asList(null, null), asList(null, null));
			Precondition.checkEquals(asList(2, 5, 7, 1, 4), asList(4, 1, 7, 5, 2));
			Precondition.checkEquals(set, asList(2, 3, 1));
		}
		catch (Throwable t) {
			fail();
		}
		try {
			Precondition.checkEquals(set, asList(1, 2, 1, 3));
			fail();
		}
		catch (Throwable t) {
			return;
		}
	}

	@Test
	public void preconditionSameArraySize() {
		try {
			Precondition.checkSameArraySize(new Byte[] { 1, 2 }, new Byte[] { 1, 2 });
		}
		catch (Throwable t) {
			fail();
		}
		try {
			Precondition.checkSameArraySize(new Byte[] { 1, 2 }, new Byte[] {});
			fail();
		}
		catch (Throwable t) {
			return;
		}
	}

	@Test
	public void preconditionArrayEquals() {
		try {
			Precondition.checkArrayEquals(new Byte[] {}, new Byte[] {});
			Precondition.checkArrayEquals(new Byte[] { 1, 2 }, new Byte[] { 1, 2 });
		}
		catch (Throwable t) {
			fail();
		}
		try {
			Precondition.checkArrayEquals(new Byte[] { 1, 2 }, new Byte[] { 1, 3 });
			fail();
		}
		catch (Throwable t) {
			return;
		}
	}
}
