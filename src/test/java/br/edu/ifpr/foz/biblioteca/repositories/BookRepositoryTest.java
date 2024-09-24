package br.edu.ifpr.foz.biblioteca.repositories;

import br.edu.ifpr.foz.biblioteca.models.Author;
import br.edu.ifpr.foz.biblioteca.models.Book;
import br.edu.ifpr.foz.biblioteca.models.BookStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class BookRepositoryTest {


    @Test
    public void deveExibirUmaListaDeLivros(){

        BooksRepository repository = new BooksRepository();

        List<Book> books = repository.getBooks();

        for (Book b: books) {
            System.out.println(b);
        }
    }

    @Test
    public void deveInserirUmRegistroNaTabelaLivro(){

        BooksRepository repository = new BooksRepository();
        Author author = new Author();
        author.setId(1);
        BookStatus status = new BookStatus();
        status.setId(1);
        Book bookFake = new Book();
        bookFake.setNome("Harry Potter");
        bookFake.setCategoria("Adventure");
        bookFake.setDataPublicacao(LocalDate.of(2024, 8, 5));
        bookFake.setAuthor(author);
        bookFake.setStatus(status);

        Book book = repository.insert(bookFake);

    }

    @Test
    public void deveDeletarUmLivro(){

        BooksRepository repository = new BooksRepository();
        repository.delete(4);

    }

    @Test
    public void deveRetornarUmLivroPeloId(){

        BooksRepository repository = new BooksRepository();
        Book book = repository.getById(1);

        System.out.println(book);


    }

    @Test
    public void deveRetornarUmaListaDeSellersPeloIdDoAutor() {
        BooksRepository repository = new BooksRepository();
        List<Book> booksList = repository.findByAuthor(1);

        for (Book book: booksList) {
            System.out.println(book);

        }

    }

    @Test
    public void DeveAtualizarDadosLivro(){
        BooksRepository repository = new BooksRepository();
        Author author = new Author();
        author.setId(1);
        BookStatus status = new BookStatus();
        status.setId(1);
        Book bookFake = new Book();
        bookFake.setId(2);
        bookFake.setNome("Robin Hood");
        bookFake.setCategoria("Adventure");
        bookFake.setDataPublicacao(LocalDate.of(2024, 8, 5));
        bookFake.setAuthor(author);
        bookFake.setStatus(status);

        repository.update(bookFake);



    }
}
