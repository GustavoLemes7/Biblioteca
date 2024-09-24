package br.edu.ifpr.foz.biblioteca.repositories;

import br.edu.ifpr.foz.biblioteca.connection.ConnectionFactory;
import br.edu.ifpr.foz.biblioteca.exceptions.DatabaseException;
import br.edu.ifpr.foz.biblioteca.models.Book;
import br.edu.ifpr.foz.biblioteca.models.Author;
import br.edu.ifpr.foz.biblioteca.models.BookStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BooksRepository {

    private Connection connection;

    public BooksRepository() {

        connection = ConnectionFactory.getConnection();

    }

    public List<Book> getBooks() {

        List<Book> books = new ArrayList<>();

        try {

            Statement statement = connection.createStatement();
            String query = "SELECT book.*, author.nome AS author_nome, bookStatus.statusBook AS book_status " +
                    "FROM book " +
                    "JOIN author ON book.author_id = author.id " +
                    "JOIN bookStatus ON book.status_id = bookStatus.id " +
                    "ORDER BY book.id DESC";


            ResultSet result = statement.executeQuery(query);

            while (result.next()) {

                Author author = instantiateAuthor(result);
                BookStatus status = instantiateBookStatus(result);
                Book book = instantiateBook(result, status, author);

                books.add(book);

            }

            result.close();


        } catch (SQLException e) {

            throw new RuntimeException(e);

        } finally {
            ConnectionFactory.closeConnection();
        }


        return books;
    }

    public Book insert(Book book) {

        String sql = "INSERT INTO book (nome, categoria, dataPublicacao, author_id, status_id) " +
                "VALUES(?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, book.getNome());
            statement.setString(2, book.getCategoria());
            statement.setDate(3, Date.valueOf(book.getDataPublicacao()));
            statement.setInt(4, book.getAuthor().getId());
            statement.setInt(5, book.getStatus().getId());

            Integer rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {

                ResultSet id = statement.getGeneratedKeys();

                id.next();

                Integer bookId = id.getInt(1);

                System.out.println("Rows inserted: " + rowsInserted);
                System.out.println("Id: " + bookId);

                book.setId(bookId);

            }


        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }

        return book;
    }



    public void update(Book book) {

        String sql = "UPDATE book SET " +
                "nome = ?, " +
                "categoria = ?, " +
                "dataPublicacao = ?, " +
                "author_id = ?, " +
                "status_id = ? " +
                "WHERE (book.id = ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, book.getNome());
            statement.setString(2, book.getCategoria());
            statement.setDate(3, Date.valueOf(book.getDataPublicacao()));
            statement.setDouble(4,book.getAuthor().getId());
            statement.setInt(5, book.getStatus().getId());
            statement.setInt(6, book.getId());

            int rowsAffected = statement.executeUpdate();

            System.out.println("Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

    }

    public void delete(Integer id) {

        String sql = "DELETE FROM book WHERE Id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, id);
            Integer rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Rows deleted: " + rowsDeleted);
            }

        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
    }

    public Book getById(Integer id) {

        Book book;
        BookStatus status;
        Author author;

        String sql = "SELECT book.*, author.nome AS author_nome, bookStatus.statusBook AS book_status " +
                "FROM book " +
                "JOIN author ON book.author_id = author.id " +
                "JOIN bookStatus ON book.status_id = bookStatus.id " +
                "WHERE book.id = ? ORDER BY book.id DESC";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                author = instantiateAuthor(resultSet);
                status = instantiateBookStatus(resultSet);
                book = instantiateBook(resultSet, status, author);



            } else {
                throw new DatabaseException("Livro não encontrado");
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return book;
    }

    public List<Book> findByAuthor(Integer id) {

        List<Book> booksList = new ArrayList<>();

        Book book;
        Author author;
        BookStatus status;

        String sql = "SELECT book.*, author.nome AS author_nome, bookStatus.statusBook AS book_status " +
                "FROM book " +
                "JOIN author ON book.author_id = author.id " +
                "JOIN bookStatus ON book.status_id = bookStatus.id " +
                "WHERE author.id = ? ORDER BY book.id DESC";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            Map<Integer, Author> map = new HashMap<>();

            while (resultSet.next()) {

                author = map.get(resultSet.getInt("author_id"));

                if (author == null) {
                    author = instantiateAuthor(resultSet);
                    map.put(resultSet.getInt("author_id"), author);
                }

                status = instantiateBookStatus(resultSet);
                book = this.instantiateBook(resultSet, status, author);

                booksList.add(book);
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }

        return booksList;

    }

    public Book instantiateBook(ResultSet resultSet, BookStatus status, Author author) throws SQLException {

        Book book = new Book();

        book.setId(resultSet.getInt("id"));
        book.setNome(resultSet.getString("nome"));
        book.setCategoria(resultSet.getString("categoria"));
        book.setDataPublicacao(resultSet.getDate("dataPublicacao").toLocalDate());
        book.setAuthor(author);
        book.setStatus(status);

        return book;
    }

    public BookStatus instantiateBookStatus(ResultSet resultSet) throws SQLException {

        BookStatus bookStatus = new BookStatus();

        bookStatus.setId(resultSet.getInt("status_id"));
        bookStatus.setStatus(resultSet.getString("book_status"));

        return bookStatus;
    }

    public Author instantiateAuthor(ResultSet resultSet) throws SQLException {

        Author author = new Author();

        author.setId(resultSet.getInt("author_id"));
        author.setName(resultSet.getString("author_nome"));

        return author;
    }

}
