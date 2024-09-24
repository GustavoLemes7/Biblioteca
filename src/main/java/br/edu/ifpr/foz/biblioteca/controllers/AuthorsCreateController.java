package br.edu.ifpr.foz.biblioteca.controllers;

import br.edu.ifpr.foz.biblioteca.models.Author;
import br.edu.ifpr.foz.biblioteca.repositories.AuthorsRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/author/create")
public class AuthorsCreateController extends HttpServlet {

    AuthorsRepository repository = new AuthorsRepository();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<String> erros = new ArrayList<>();
        req.setAttribute("erros", erros);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/author-create.jsp");
        dispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String title = req.getParameter("field_name");

        Author author = new Author();
        author.setName(title);

        List<String> erros = new ArrayList<>();

        if((author.getName().trim()).isEmpty()){
            erros.add("Informe o nome do livro");
        }

        if(!erros.isEmpty()) {
            req.setAttribute("erros", erros);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/author-create.jsp");
            dispatcher.forward(req, resp);
        }else {
            repository.insert(author);
            resp.sendRedirect(req.getContextPath() + "/books/create");
        }


    }
}