
package br.edu.ifpr.foz.biblioteca.controllers;


import br.edu.ifpr.foz.biblioteca.models.Book;
import br.edu.ifpr.foz.biblioteca.models.BookStatus;
import br.edu.ifpr.foz.biblioteca.models.Author;
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

@WebServlet("/books/update")
public class BooksUpdateController extends HttpServlet {

    BooksRepository repository = new BooksRepository();
    BookStatusRepository bookStatusRepository = new BookStatusRepository();
    AuthorsRepository authorsRepository = new AuthorsRepository();
    BookService bookService = new BookService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer id =  Integer.valueOf(req.getParameter("id"));

        List<String> erros = new ArrayList<>();
        req.setAttribute("erros", erros);

        List<Author> authors = authorsRepository.getAll();
        req.setAttribute("authors", authors);

        List<BookStatus> statusBook = bookStatusRepository.getAll();
        req.setAttribute("status", statusBook);

        Book book = repository.getById(id);
        req.setAttribute("book", book);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/books-update.jsp");
        dispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer id = Integer.valueOf(req.getParameter("field_id"));
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
        book.setId(id);
        book.setNome(title);
        book.setCategoria(category);
        book.setDataPublicacao(publicationDate);
        book.setAuthor(author);
        book.setStatus(statusBook);
        erros = bookService.validarDados(book);

        if(!erros.isEmpty()) {


            List<Author> authors = authorsRepository.getAll();
            req.setAttribute("authors", authors);


            List<BookStatus> status = bookStatusRepository.getAll();
            req.setAttribute("status", status);
            req.setAttribute("erros", erros);

            req.setAttribute("book", book);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/books-update.jsp");
            dispatcher.forward(req, resp);
        }else {
            repository.update(book);
            resp.sendRedirect(req.getContextPath() + "/books");
        }

    }
}
