package com.example.controller;

import com.example.common.Result;
import com.example.entity.Book;
import com.example.entity.Params;
import com.example.exception.CustomException;
import com.example.service.BookService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;




@CrossOrigin
@RestController
@RequestMapping("/book")
public class BookController {

    @Resource
    private BookService bookService;

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        PageInfo<Book> info = bookService.findBySearch(params);
        return Result.success(info);
    }

    @PostMapping
    public Result save(@RequestBody Book book) {
//        if (book.getId() == null) {
//            bookService.add(book);
//        } else {
//            bookService.update(book);
//        }
//        return Result.success();

        try {
            if (book.getId() == null) {
                bookService.add(book);
            } else {
                bookService.update(book);
            }
            return Result.success();
        } catch (CustomException e) {
            return Result.error(e.getMsg());
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        bookService.delete(id);
        return Result.success();
    }

}
