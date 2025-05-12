package gerenciarusuarios.model;

public class Usuario {

    private int id;
    private String nome;
    private String cpf;
    private int idade;

    public Usuario(int id, String nome, String cpf, int idade) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
    }

    public Usuario() {

    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nome: " + nome + ", CPF: " + cpf + ", Idade: " + idade;
    }

}