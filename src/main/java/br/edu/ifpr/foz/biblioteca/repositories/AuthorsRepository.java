package br.edu.ifpr.foz.biblioteca.repositories;

import br.edu.ifpr.foz.biblioteca.connection.ConnectionFactory;
import br.edu.ifpr.foz.biblioteca.models.Author;
import br.edu.ifpr.foz.biblioteca.exceptions.DatabaseException;
import br.edu.ifpr.foz.biblioteca.models.Book;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorsRepository {

    private Connection connection;

    public AuthorsRepository() {

        connection = ConnectionFactory.getConnection();

    }

    public List<Author> getAll() {

        List<Author> authors = new ArrayList<>();

        String sql = "SELECT * FROM author";

        try {

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {

                Author author = new Author();
                author.setId(result.getInt("id"));
                author.setName(result.getString("nome"));
                authors.add(author);
            }


        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return authors;

    }

    public Author insert(Author author) {

        String sql = "INSERT INTO author (nome) " +
                "VALUES(?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, author.getName());

            Integer rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {

                ResultSet id = statement.getGeneratedKeys();

                id.next();

                Integer authorId = id.getInt(1);

                System.out.println("Rows inserted: " + rowsInserted);
                System.out.println("Id: " + authorId);

                author.setId(authorId);

            }


        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }

        return author;
    }
}
