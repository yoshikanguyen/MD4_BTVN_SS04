package com.example.author_management.service;

import com.example.author_management.model.Author;
import com.example.author_management.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    // Đổi kiểu id thành Long và dùng orElse(null) với findById
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    // Đổi kiểu id thành Long
    public Author updateAuthor(Long id, Author request) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isPresent()) {
            Author existingAuthor = optionalAuthor.get();
            existingAuthor.setName(request.getName());
            existingAuthor.setEmail(request.getEmail());
            return authorRepository.save(existingAuthor); // Lưu cập nhật vào DB
        }
        return null;
    }

    // Xóa + chặn tài khoản Admin
    public String deleteAuthor(Long id) {
        Author author = getAuthorById(id);
        if (author == null) {
            return "NOT_FOUND";
        }
        // Kiểm tra nếu tên là "Admin" (không phân biệt hoa thường)
        if (author.getName().equalsIgnoreCase("Admin")) {
            return "ADMIN_RESTRICTED";
        }
        authorRepository.deleteById(id); // Dùng deleteById thay vì delete
        return "SUCCESS";
    }

    // Tìm theo từ khóa (không phân biệt hoa thường, chứa kw)
    public List<Author> searchAuthors(String keyword) {
        List<Author> result = new ArrayList<>();
        List<Author> allAuthors = authorRepository.findAll();
        for (Author a : allAuthors) {
            if (keyword == null || a.getName().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(a);
            }
        }
        return result;
    }
}