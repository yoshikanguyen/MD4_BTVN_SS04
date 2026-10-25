package com.example.author_management.controller;

import com.example.author_management.model.Author;
import com.example.author_management.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // Lấy danh sách tất cả tác giả
    @GetMapping
    public ResponseEntity<?> getAllAuthors() {
        return new ResponseEntity<>(authorService.getAllAuthors(), HttpStatus.OK);
    }

    // Thêm
    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        Author newAuthor = authorService.createAuthor(author);
        return new ResponseEntity<>(newAuthor, HttpStatus.CREATED);
    }

    // Xem theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable("id") Long id) {
        Author author = authorService.getAuthorById(id);
        if (author == null) {
            return new ResponseEntity<>("Không tìm thấy tác giả với ID này", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    // Cập nhật theo ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable("id") Long id, @RequestBody Author request) {
        Author updatedAuthor = authorService.updateAuthor(id, request);
        if (updatedAuthor == null) {
            return new ResponseEntity<>("Không tìm thấy tác giả để cập nhật", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
    }

    // Xóa theo ID (có chặn xóa Admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable("id") Long id) {
        String status = authorService.deleteAuthor(id);
        if (status.equals("NOT_FOUND")) {
            return new ResponseEntity<>("Không tìm thấy tác giả với ID này", HttpStatus.NOT_FOUND);
        }
        if (status.equals("ADMIN_RESTRICTED")) {
            return new ResponseEntity<>("Lỗi: Không được phép xóa tài khoản quản trị (Admin)", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Xóa thành công", HttpStatus.OK);
    }

    // Tìm theo tên
    @GetMapping("/search")
    public ResponseEntity<List<Author>> searchAuthors(@RequestParam("name") String keyword) {
        List<Author> result = authorService.searchAuthors(keyword);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
