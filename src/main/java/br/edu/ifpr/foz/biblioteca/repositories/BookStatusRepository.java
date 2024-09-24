package br.edu.ifpr.foz.biblioteca.repositories;

import br.edu.ifpr.foz.biblioteca.connection.ConnectionFactory;
import br.edu.ifpr.foz.biblioteca.exceptions.DatabaseException;
import br.edu.ifpr.foz.biblioteca.models.BookStatus;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookStatusRepository {

    private Connection connection;

    public BookStatusRepository() {

        connection = ConnectionFactory.getConnection();

    }

    public List<BookStatus> getAll() {

        List<BookStatus> booksStatus = new ArrayList<>();

        String sql = "SELECT * FROM bookStatus";

        try {

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {

                BookStatus bookStatus = new BookStatus();
                bookStatus.setId(result.getInt("id"));
                bookStatus.setStatus(result.getString("statusBook"));
                booksStatus.add(bookStatus);
            }


        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return booksStatus;

    }
}
