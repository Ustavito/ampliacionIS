package es.urjc.code.daw.library.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import es.urjc.code.daw.library.book.Book;
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
	public String newBookProcess(Book book) {
		
		Book newBook = service.save(book);
		
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
	public String editBookProcess(Book book) {
		service.save(book);
		return "bookEdited";
	}
}
