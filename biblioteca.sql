create database biblioteca;
use biblioteca;
CREATE TABLE author (
	id INT PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(255) NOT NULL
);


CREATE TABLE bookStatus (
	id INT PRIMARY KEY AUTO_INCREMENT,
	statusBook VARCHAR(50) NOT NULL
);

 
CREATE TABLE book (
	id INT PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(255) NOT NULL,
	categoria VARCHAR(255),
	dataPublicacao DATE,
	author_id INT,
	status_id INT,
	FOREIGN KEY (author_id) REFERENCES author(id),
	FOREIGN KEY (status_id) REFERENCES bookStatus(id)
);

INSERT INTO author (nome) VALUES
('George Orwell'),
('J.K. Rowling'),
('J.R.R. Tolkien');


INSERT INTO bookStatus (statusBook) VALUES
('Disponível'),
('Emprestado'),
('Reservado');


INSERT INTO book (nome, categoria, dataPublicacao, author_id, status_id) VALUES
('1984', 'Ficção', '1949-06-08', 1, 1),
('Harry Potter e a Pedra Filosofal', 'Fantasia', '1997-06-26', 2, 1),
('O Senhor dos Anéis', 'Fantasia', '1954-07-29', 3, 2);
select * from book;


