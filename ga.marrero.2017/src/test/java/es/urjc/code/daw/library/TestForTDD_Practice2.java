package es.urjc.code.daw.library;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import es.urjc.code.daw.library.book.LineBreaker;

public class TestForTDD_Practice2 {
	
	@Test
	void testBreaker_blank_and_size_2() {
		testLineBreaker("", 2, "");	
	}
	
	@Test
	void testBreaker_test_and_size4() {
		testLineBreaker("test", 4, "test");
	}
	
	@Test
	void testBreaker_test_and_size5() {
		testLineBreaker("test", 5, "test");
	}
	
	@Test
	void testBreaker_test_x2_and_size4() {
		testLineBreaker("test test", 4, "test\ntest");
	}
	
	@Test
	void testBreaker_test_x2_and_size5() {
		testLineBreaker("test test", 5, "test\ntest");
	}
	
	@Test
	void testBreaker_test_x2_and_size6() {
		testLineBreaker("test test", 6, "test\ntest");
	}
	
	@Test
	void testBreaker_test_x4_and_size9() {
		testLineBreaker("test test test test", 9, "test test\ntest test");
	}
	
	@Test
	void testBreaker_test_x2_twoSpaces_and_size4() {
		testLineBreaker("test  test", 4, "test\ntest");
	}
	
	@Test
	void testBreaker_test_x2_threeSpaces_and_size6() {
		testLineBreaker("test   test", 6, "test\ntest");
	}
	
	@Test
	void testBreaker_test_x2_noSpaces_and_size5() {
		testLineBreaker("testtest", 5, "test-\ntest");
	}
	
	@Test
	void testBreaker_test_x3_noSpaces_and_size5() {
		testLineBreaker("testtesttest", 5, "test-\ntest-\ntest");
	}
	
	@Test
	void testBreaker_test_x2_and_size3() {
		testLineBreaker("test test", 3, "te-\nst\nte-\nst");
	}
	
	@Test
	void testBreaker_test_numbers_test_and_size6() {
		testLineBreaker("test 1234567 test", 6, "test\n12345-\n67\ntest");
	}
	
	@Test
	void testBreaker_test_nineNumbers_test_and_size3() {
		testLineBreaker("123456789", 3, "12-\n34-\n56-\n789");
	}

	private void testLineBreaker (String text, int textLenght, String expectedBrokenText) {	
		new LineBreaker();
		assertEquals(expectedBrokenText, LineBreaker.breakText(text, textLenght));
	}
}
