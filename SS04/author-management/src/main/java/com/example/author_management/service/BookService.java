package com.example.author_management.service;

import com.example.author_management.dto.BookRequest;
import com.example.author_management.model.Author;
import com.example.author_management.model.Book;
import com.example.author_management.repository.AuthorRepository;
import com.example.author_management.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    // Thêm mới sách + kiểm tra tác giả
    public Book createBook(BookRequest request) {
        Optional<Author> authorOpt = authorRepository.findById(request.getAuthorId());
        if (authorOpt.isEmpty()) {
            throw new RuntimeException("Tác giả không tồn tại");
        }
        Book book = new Book(request.getTitle(), request.getPrice(), authorOpt.get());
        return bookRepository.save(book);
    }

    // Lấy danh sách tất cả các sách
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Xem chi tiết một cuốn sách theo ID
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }
}
