package model;

// Aluno: Erick Ribeiro Graciano
public class Empresa {
    String cnpj;
    String nome;
    int numEmpregados;

    //Construtor
    public Empresa(String cnpj, String nome, int numEmpregados) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.numEmpregados = numEmpregados;
    }
    //Construtor vazio para o TDE2
    public Empresa() {
    }

    //Getterts e Setters
    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getNumEmpregados() {
        return numEmpregados;
    }
    public void setNumEmpregados(int numEmpregados) {
        this.numEmpregados = numEmpregados;
    }

    //Métodos de validação
    public boolean validarCnpj() {
        return cnpj != null && !cnpj.isBlank();
    }

    public boolean validarNome() {
        return nome != null && !nome.isBlank();
    }

    public boolean validarNumEmpregados() {
        return numEmpregados >= 0;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "cnpj='" + cnpj + '\'' +
                ", nome='" + nome + '\'' +
                ", numEmpregados=" + numEmpregados +
                '}';
    }
}
