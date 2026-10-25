package api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class Users {

    // APONTA PARA A PASTA "db" QUE ESTÁ NO MESMO NÍVEL DA PASTA PAI DO JAVA
    private static final Path CAMINHO_ARQUIVO = Paths.get("db", "users.txt");

    public static void main(String[] args) {
        try {
            // Garante que a pasta "db" exista antes de criar o arquivo
            if (CAMINHO_ARQUIVO.getParent() != null && !Files.exists(CAMINHO_ARQUIVO.getParent())) {
                Files.createDirectories(CAMINHO_ARQUIVO.getParent());
            }

            // Inicializa o arquivo se ele não existir dentro da pasta db
            if (!Files.exists(CAMINHO_ARQUIVO)) {
                Files.createFile(CAMINHO_ARQUIVO);
            }

            System.out.println("Clientes salvos em db/users.txt: " + getAll());

        } catch (IOException e) {
            System.err.println("Erro na operação: " + e.getMessage());
        }
    }

    // CREATE
    public static void createUser(String email, String senha, String role) throws IOException {
        String linha = email + ";" + senha + ";" + role + System.lineSeparator();
        Files.writeString(CAMINHO_ARQUIVO, linha, StandardOpenOption.APPEND);
    }

    // READ (Todos)
    public static List<String> getAll() throws IOException {
        return Files.readAllLines(CAMINHO_ARQUIVO);
    }

    // READ (Por ID/Email)
    public static String getById(String email) throws IOException {
        for (String linha : Files.readAllLines(CAMINHO_ARQUIVO)) {
            if (linha.startsWith(email + ";")) {
                return linha;
            }
        }
        return null;
    }

    // UPDATE
    public static void updateById(String email, String novaSenha, String novoRole) throws IOException {
        List<String> linhas = Files.readAllLines(CAMINHO_ARQUIVO);
        List<String> atualizadas = new ArrayList<>();

        for (String linha : linhas) {
            if (linha.startsWith(email + ";")) {
                atualizadas.add(email + ";" + novaSenha + ";" + novoRole);
            } else {
                atualizadas.add(linha);
            }
        }
        Files.write(CAMINHO_ARQUIVO, atualizadas);
    }

    // DELETE
    public static void deleteById(String email) throws IOException {
        List<String> linhas = Files.readAllLines(CAMINHO_ARQUIVO);
        List<String> filtradas = new ArrayList<>();

        for (String linha : linhas) {
            if (!linha.startsWith(email + ";")) {
                filtradas.add(linha);
            }
        }
        Files.write(CAMINHO_ARQUIVO, filtradas);
    }
}
