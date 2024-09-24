package br.edu.ifpr.foz.biblioteca.models;

public class BookStatus {
    private Integer id;
    private String status;

    public int getId() {
            return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BookStatus{" +
                "id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}
