package es.urjc.code.daw.library.book;

import javax.persistence.Column;

public class BookDTO {
	private String title;
	
	@Column(length = 50000)
	private String description;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
