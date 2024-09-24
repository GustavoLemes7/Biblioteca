package br.edu.ifpr.foz.biblioteca.controllers;


import br.edu.ifpr.foz.biblioteca.repositories.BooksRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/books/delete")
public class BooksDeleteController extends HttpServlet {

    BooksRepository repository = new BooksRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        Integer id = Integer.valueOf(req.getParameter("id"));

        repository.delete(id);

        resp.sendRedirect("http://localhost:8080/Biblioteca_war/books");

    }
}