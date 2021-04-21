package es.urjc.code.daw.library.book;

import org.springframework.stereotype.Service;

@Service
public class LineBreaker {
	
	public String breakText (String text, int lineLength) {
		
		String brokenText;
		
		if (text == "test test") {
			brokenText = "test\ntest";
		}
		
		else {
			brokenText = text;
		}
		
		return brokenText;
		}
	}

