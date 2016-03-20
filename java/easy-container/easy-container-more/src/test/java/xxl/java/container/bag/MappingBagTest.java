package xxl.java.container.bag;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import xxl.java.container.bag.Bag;
import xxl.java.container.bag.MappingBag;
import xxl.java.container.bag.MappingBag.Function;

public class MappingBagTest {

	@Test
	public void mappingBagTest() {
		MappingBag<Integer, String> mappingBag = MappingBag.newMappingBag(stringMapper());
		mappingBag.addMapping(1);
		mappingBag.addMapping(10, 2);
		assertEquals(3, mappingBag.size());
		assertEquals(1, mappingBag.repetitionsOf("1"));
		assertEquals(2, mappingBag.repetitionsOf("10"));
		mappingBag.removeMapping(1);
		assertEquals(2, mappingBag.size());
		assertEquals(0, mappingBag.repetitionsOf("1"));
	}

	@Test
	public void mappingBagConstructWithOtherBag() {
		Bag<Integer> bag = Bag.newHashBag(1, 2, 3, 4, 5, 3, 2);
		MappingBag<Integer, String> mappingBag = MappingBag.newMappingBag(stringMapper(), bag);
		assertEquals(7, mappingBag.size());
		assertEquals(1, mappingBag.repetitionsOf("1"));
		assertEquals(2, mappingBag.repetitionsOf("2"));
		assertEquals(2, mappingBag.repetitionsOf("3"));
		assertEquals(1, mappingBag.repetitionsOf("4"));
		assertEquals(1, mappingBag.repetitionsOf("5"));
	}

	private Function<Integer, String> stringMapper() {
		return new Function<Integer, String>() {
			@Override
			public String outputFor(Integer value) {
				return value.toString();
			}
		};
	}
}
