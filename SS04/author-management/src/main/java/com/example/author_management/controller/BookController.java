package com.example.author_management.controller;

import com.example.author_management.dto.BookRequest;
import com.example.author_management.model.Book;
import com.example.author_management.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Lưu sách mới
    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody BookRequest request) {
        try {
            Book newBook = bookService.createBook(request);
            return new ResponseEntity<>(newBook, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Lấy danh sách tất cả các sách
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    // Xem chi tiết một cuốn sách
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable("id") Long id) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            return new ResponseEntity<>("Không tìm thấy sách", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
}