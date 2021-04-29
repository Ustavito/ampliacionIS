package es.urjc.code.daw.library.book;

import org.springframework.stereotype.Service;

@Service
public class LineBreaker {
	
	public static String breakText (String text, int lineLength) {
		
		String brokenText ="";
		String aux;
		String aux2, aux3;
		int top;
		String[] words = text.split(" ");
		
		
		//Si la cadena es menor a la longitud maxima
		if (text.length() <= lineLength) {
			brokenText = text.trim();
		}
		
		//Si la cadena tiene un espacio en el centro
		else if (text.charAt(lineLength) == ' '){
				aux = text.substring(0, lineLength).trim();
				aux2 = text.substring(lineLength).trim();
				brokenText = aux + "\n" + aux2;
		}
	
		//Si tiene espacios pero cabe en una linea
		else if ((text.indexOf(' ') != -1) && text.indexOf(' ') <= lineLength && words.length < 3 ) {
			aux = text.substring(0, text.indexOf(' ')).trim();
			aux2 = text.substring(text.indexOf(' ')).trim();
			brokenText = aux + "\n" + aux2;
		}
		
		//Si tiene espacios pero no cabe en una linea
		else if ((text.indexOf(' ') > lineLength) || text.indexOf(' ') == -1 || words.length % 3 == 0) {
			top = 0;
			aux = "";
			aux2= "";
			while (top < text.length() -1){							
				if (top + lineLength >= text.length() &&  text.indexOf(' ') == -1){
					aux = text.substring(top).trim();
					aux2 = aux2 + aux;					
				}
								
				else if(top + lineLength >= text.length() &&  text.indexOf(' ') != -1) {
					aux = text.substring(top +1).trim();
					aux2 = aux2 + aux;				
				}
				
				else if (words.length % 3 == 0 && words.length != 0) {
					aux3="";
					for (int i = 0; i <= words.length -1; i++) {
						aux = breakText(words[i].trim(), lineLength).trim();
						if (aux3.length() == 0){
							aux3 = aux3 + aux;
						}
						else {
							aux3 = aux3 + "\n" + aux;
						}
						aux2 = aux3; 
					}
					break;
				}
				
				else {
						if (text.charAt(top + lineLength) == ' '|| text.charAt(top + lineLength -1) == ' ') {
							aux = text.substring(top, top+lineLength -1);
							aux2 = aux2 + aux.trim() + "\n";
							
						}
						else {
							aux = text.substring(top, top+lineLength -1);
							if (text.charAt(top) == ' ') {
								aux = text.substring(top +1, top+lineLength).trim();
							}			
							aux2 = aux2 + aux.trim() + "-" + "\n";
						}		
				}
				
				top = top + lineLength -1;
			}
			
			brokenText = aux2;
		}
		
		return brokenText;		
	}
}