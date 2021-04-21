package es.urjc.code.daw.library.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import es.urjc.code.daw.library.book.Book;
import es.urjc.code.daw.library.book.BookDTO;
import es.urjc.code.daw.library.book.BookService;

@Controller
public class BookWebController {
	
	private static final String BOOKS = "books";

	@Autowired
	private BookService service;
	
	@GetMapping("/")
	public String showBooks(Model model) {

		model.addAttribute(BOOKS, service.findAll());
		
		return BOOKS;
	}
	
	@GetMapping("/books/{id}")
	public String showBook(Model model, @PathVariable long id) {
		
		Optional<Book> op = service.findOne(id);
		if(op.isPresent()) {
			Book book = op.get();
			model.addAttribute("book", book);
			return "book";
		}else {
			return BOOKS;
		}
		
	}
	
	@GetMapping("/removebook/{id}")
	public String removeBook(Model model, @PathVariable long id) {
		
		Optional<Book> op = service.findOne(id);
		if(op.isPresent()) {
			Book book = op.get();
			service.delete(id);
			model.addAttribute("book", book);
			return "redirect:/";
		}else {
			return "redirect:/";
		}
	}
	
	@GetMapping("/newbook")
	public String newBook(Model model) {
		
		return "newBookPage";
	}
	
	@PostMapping("/createbook")
	public String newBookProcess(BookDTO book) {
		//He modificado esta clase para que reciba un objeto bookDTO (clase intermedia)
		Book persistentBook = new Book();
		persistentBook.setTitle(book.getTitle());
		persistentBook.setDescription(book.getDescription());
		
		
		
		
		Book newBook = service.save(persistentBook);
		
		return "redirect:/books/" + newBook.getId();
	}
	
	@GetMapping("/editbook/{id}")
	public String editBook(Model model, @PathVariable long id) {
		
		Optional<Book> op = service.findOne(id);
		if(op.isPresent()) {
			Book book = op.get();
			model.addAttribute("book", book);
			return "editBookPage";
		}else {
			return BOOKS;
		}
		
	}
	
	@PostMapping("/editbook")
	public String editBookProcess(BookDTO book) { //He modificado la clase para que reciba bookDTO
		//Se inicializa el libro dentro de la clase para proteger sus atributos privados.
		//Para ello utilizo la clase bookDTO (clase intermedia)
		Book persistentBook = new Book();
		persistentBook.setTitle(book.getTitle());
		persistentBook.setDescription(book.getDescription());
		
		
		service.save(persistentBook);
		return "bookEdited";
	}
}
