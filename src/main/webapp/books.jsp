<%@ page import="br.edu.ifpr.foz.biblioteca.models.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="br.edu.ifpr.foz.biblioteca.DateUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% List<Book> books = (List<Book>) request.getAttribute("books"); %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Administração de Livros</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="css/dashboard.css">
</head>
<body class="bg-light">

<!-- Menu superior -->
<nav class="navbar navbar-expand-lg shadow-sm navbar-dark">
  <div class="container-fluid">
    <a class="navbar-brand" href="<%= request.getContextPath() %>/books"> <img src="images/logo.png" alt="">Biblioteca IFPR</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav ms-auto">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="#">Início</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">Livros</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">Autores</a>
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
              <span data-feather="file"></span>
              Livros
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">
              <span data-feather="shopping-cart"></span>
              Autores
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
        <h1 class="h2">Livros Cadastrados</h1>
        <a class="btn btn-info" href="<%= request.getContextPath() %>/books/create">Cadastrar</a>
      </div>

      <!-- Tabela de livros -->
      <div class="table-responsive">
        <table class="table table-striped table-sm">
          <thead>
          <tr>
            <th>Id</th>
            <th>Título</th>
            <th>Autor</th>
            <th>Data de Publicação</th>
            <th>Status</th>
            <th>Categoria</th>
            <th>Ações</th>
          </tr>
          </thead>
          <tbody>

          <% for (Book book : books) {%>
          <tr>
            <td><%= book.getId() %></td>
            <td><%= book.getNome() %></td>
            <td><%= book.getAuthor().getName() %></td>
            <td><%= DateUtils.brazilianDateFormat(book.getDataPublicacao()) %></td>
            <td><%= book.getStatus().getStatus() %></td>
            <td><%= book.getCategoria() %></td>
            <td>
              <a href="<%= request.getContextPath() %>/books/delete?id=<%= book.getId() %>" class="btn btn-sm btn-danger">Excluir</a>
              <a href="<%= request.getContextPath() %>/books/update?id=<%= book.getId() %>" class="btn btn-sm btn-primary">Editar</a>
            </td>
          </tr>
          <% } %>

          <!-- Adicione mais livros conforme necessário -->
          </tbody>
        </table>
      </div>

    </main>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
