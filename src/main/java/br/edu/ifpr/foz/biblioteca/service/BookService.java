package br.edu.ifpr.foz.biblioteca.service;

import java.util.List;
import java.util.ArrayList;
import br.edu.ifpr.foz.biblioteca.models.Book;

public class BookService {

    public List<String> validarDados(Book book) {

        List<String> erros = new ArrayList<>();

        if((book.getNome().trim()).isEmpty()){
            erros.add("Informe o nome do livro");
        }

        if((book.getCategoria().trim()).isEmpty()){
            erros.add("Informe o categoria do livro");
        }

        if(book.getDataPublicacao() == null){
            erros.add("Informe a data do livro");
        }

        if(book.getAuthor().getId() == 0){
            erros.add("Informe o author do livro");
        }

        if(book.getStatus().getId() == 0){
            erros.add("Informe o status do livro");
        }

        return erros;
    }
}
