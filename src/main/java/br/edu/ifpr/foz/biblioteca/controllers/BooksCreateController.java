package br.edu.ifpr.foz.biblioteca.controllers;


import br.edu.ifpr.foz.biblioteca.models.Author;
import br.edu.ifpr.foz.biblioteca.models.Book;
import br.edu.ifpr.foz.biblioteca.models.BookStatus;
import br.edu.ifpr.foz.biblioteca.repositories.AuthorsRepository;
import br.edu.ifpr.foz.biblioteca.repositories.BookStatusRepository;
import br.edu.ifpr.foz.biblioteca.repositories.BooksRepository;
import br.edu.ifpr.foz.biblioteca.service.BookService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/books/create")
public class BooksCreateController extends HttpServlet {

    BooksRepository repository = new BooksRepository();
    AuthorsRepository authorsRepository = new AuthorsRepository();
    BookStatusRepository statusRepository = new BookStatusRepository();
    BookService bookService = new BookService();




    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<String> erros = new ArrayList<>();

        List<Author> authors = authorsRepository.getAll();
        req.setAttribute("authors", authors);

        List<BookStatus> status = statusRepository.getAll();
        req.setAttribute("status", status);
        req.setAttribute("erros", erros);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/books-create.jsp");
        dispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String title = req.getParameter("field_title");
        String category = req.getParameter("field_category");
        LocalDate publicationDate = LocalDate.parse(req.getParameter("field_publicationDate"));
        Integer authorId = Integer.valueOf(req.getParameter("field_author"));
        Integer statusId = Integer.valueOf(req.getParameter("field_status"));

        List<String> erros = new ArrayList<>();

        Author author = new Author();
        author.setId(authorId);

        BookStatus statusBook = new BookStatus();
        statusBook.setId(statusId);


        Book book = new Book();
        book.setNome(title);
        book.setCategoria(category);
        book.setDataPublicacao(publicationDate);
        book.setAuthor(author);
        book.setStatus(statusBook);

        erros = bookService.validarDados(book);

        if(!erros.isEmpty()) {
            List<Author> authors = authorsRepository.getAll();
            req.setAttribute("authors", authors);


            List<BookStatus> status = statusRepository.getAll();
            req.setAttribute("status", status);
            req.setAttribute("erros", erros);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/books-create.jsp");
            dispatcher.forward(req, resp);
        }else {
            repository.insert(book);
            resp.sendRedirect(req.getContextPath() + "/books");
        }


    }
}