<%@ page import="br.edu.ifpr.foz.biblioteca.models.Book" %>
<%@ page import="br.edu.ifpr.foz.biblioteca.models.Author" %>
<%@ page import="br.edu.ifpr.foz.biblioteca.models.BookStatus" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Author> authors = (List<Author>) request.getAttribute("authors");
    List<BookStatus> bookStatus = (List<BookStatus>) request.getAttribute("status");
    List<String> erros = (List<String>) request.getAttribute("erros");
    Book book = (Book) request.getAttribute("book");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Administração de Livros</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/dashboard.css">
</head>
<body class="bg-light">

<!-- Menu superior -->
<nav class="navbar navbar-expand-lg shadow-sm navbar-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="<%= request.getContextPath() %>/books"> <img src="<%= request.getContextPath() %>/images/logo.png" alt="Biblioteca IFPR">Biblioteca IFPR</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="<%= request.getContextPath() %>/books">Início</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<%= request.getContextPath() %>/books">Livros</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<%= request.getContextPath() %>/author/create">Autores</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Configurações</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <!-- Barra lateral -->
        <nav id="sidebar" class="col-md-3 col-lg-2 d-md-block bg-light sidebar">
            <div class="position-sticky pt-3">
                <ul class="nav flex-column">
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath() %>/books">
                            <span data-feather="book"></span>
                            Livros
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath() %>/author/create">
                            <span data-feather="users"></span>
                            Cadastrar Autores
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">
                            <span data-feather="bar-chart-2"></span>
                            Relatórios
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">
                            <span data-feather="layers"></span>
                            Integrações
                        </a>
                    </li>
                </ul>
            </div>
        </nav>

        <!-- Conteúdo principal -->
        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 main-content">
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">Edição de Livro</h1>
            </div>

            <form action="<%= request.getContextPath() %>/books/update" method="post">

                <div class="mb-3">
                    <label for="id" class="form-label">Id: </label>
                    <input type="text" class="form-control" id="id" name="field_id" value="<%= book.getId() %>" readonly>
                </div>

                <div class="mb-3">
                    <label for="title" class="form-label">Título: </label>
                    <input type="text" class="form-control" id="title" name="field_title" value="<%= book.getNome() %>">
                </div>

                <div class="mb-3">
                    <label for="categoria" class="form-label">Categoria: </label>
                    <input type="text" class="form-control" id="categoria" name="field_category" value="<%= book.getCategoria()%>">
                </div>

                <div class="mb-3">
                    <label for="author" class="form-label">Autor: </label>
                    <select class="form-select" name="field_author" id="author">
                        <option value=0 selected> Selecione um autor...</option>
                        <% for (Author author : authors) {
                            String selected = (book.getAuthor().getId() == author.getId()) ? "selected" : ""; %>
                        <option <%= selected %> value="<%= author.getId() %>"><%= author.getName() %></option>
                        <% } %>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="publicationDate" class="form-label">Data de Publicação: </label>
                    <input type="date" class="form-control" id="publicationDate" name="field_publicationDate" value="<%= book.getDataPublicacao() %>" required>
                </div>

                <div class="mb-3">
                    <label for="status" class="form-label">Status: </label>
                    <select class="form-select" name="field_status" id="status">
                        <option value=0 selected>Selecione um status...</option>
                        <% for (BookStatus status : bookStatus) {
                            String selected = (book.getStatus().getId() == status.getId()) ? "selected" : ""; %>
                        <option <%= selected %> value="<%= status.getId() %>"><%= status.getStatus() %></option>
                        <% } %>
                    </select>
                </div>

                <div class="col-12">
                    <button class="btn btn-primary btn-sm px-5" type="submit">Atualizar</button>
                </div>

                <%
                    if (erros != null && !erros.isEmpty()) {
                %>
                <br>
                <div class="alert alert-danger">
                    <ul>
                        <% for (String erro : erros) { %>
                        <li><%= erro %></li>
                        <% } %>
                    </ul>
                </div>
                <% } %>
            </form>
        </main>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
