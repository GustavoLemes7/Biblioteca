package br.edu.ifpr.foz.biblioteca.exceptions;

//não chechada
public class DatabaseIntegrityException extends RuntimeException {

    public DatabaseIntegrityException(String msg){
        super(msg);
    }

}
