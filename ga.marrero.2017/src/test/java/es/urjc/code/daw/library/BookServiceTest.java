package es.urjc.code.daw.library;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.urjc.code.daw.library.book.Book;
import es.urjc.code.daw.library.book.BookRepository;
import es.urjc.code.daw.library.book.BookService;
import es.urjc.code.daw.library.notification.NotificationService;

class BookServiceTest {
	
	BookRepository repository;
	Book book;
	NotificationService notification;
	BookService service;
	
	@BeforeEach
	public void setUp() {
		this.repository = mock (BookRepository.class);
		this.book = mock (Book.class);
		this.notification = mock (NotificationService.class);
		this.service = new BookService(repository, notification);
	}
	
	
	@Test
	void whenSaveBook_LauchNotification() {
		
		//Given
		when(repository.save(book)).thenReturn(book);
		when(book.getTitle()).thenReturn("Eragon");
		when(book.getDescription()).thenReturn("Descripcion de libro");
		
		//When
		service.save(book);
		
		//Then 
		verify (notification, times(1)).notify("Book Event: book with title="+ book.getTitle() + " was created");	
	}
	
	@Test
	void whenSaveBook_SaveInRepository() {
		
		//Given
		when(book.getId()).thenReturn(10L);
		when(book.getTitle()).thenReturn("Eldest");
		when(book.getDescription()).thenReturn("Descripcion de libro");
		when(repository.existsById(10L)).thenReturn(true);
		when(repository.save(book)).thenReturn(book);
		
		//When
		service.save(book);
		service.exist(10L);
		
		
		//Then
		verify(repository, atLeastOnce()).existsById(10L);
		assertThat(service.exist(10L)).isTrue();	
	}
	
	@Test
	void whenErraseBook_LaunchNotification() {
		
		//Given
		when(book.getId()).thenReturn(20L);
		
		//When
		service.delete(book.getId());
		
		//Then
		verify(notification, times(1)).notify("Book Event: book with id=" + book.getId() + " was deleted");
	}
	
	@Test
	void whenErraseBook_ErraseInRepository() {
		
		//Given
		when(book.getId()).thenReturn(30L);
		when(repository.existsById(30L)).thenReturn(false);
		
		//When
		service.delete(book.getId());
		
		//Then
		verify(repository, never()).existsById(30L);
		assertThat(service.exist(30L)).isFalse();	
	}
}