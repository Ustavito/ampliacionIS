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
	void testBreaker_testtest_and_size4() {
		testLineBreaker("test test", 4, "test\ntest");
	}
	
	@Test
	void testBreaker_testtest_and_size5() {
		testLineBreaker("test test", 5, "test\ntest");
	}
	
	private void testLineBreaker (String text, int textLenght, String expectedBrokenText) {	
		assertEquals(expectedBrokenText, new LineBreaker().breakText(text, textLenght));
	}
}
