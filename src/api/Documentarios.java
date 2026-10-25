package api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class Documentarios {

    private static final Path CAMINHO_ARQUIVO = Paths.get("db", "documentarios.txt");

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

            System.out.println("Clientes salvos em db/filmes.txt: " + getAll());

        } catch (IOException e) {
            System.err.println("Erro na operação: " + e.getMessage());
        }
    }

    // CREATE
    public static void createDoc(int idMidia, String nome, int duracao, String diretor, int idCat, int idClassInd, ArrayList<String> fontes, String assunto) throws IOException {
        String linha = idMidia + ";" + nome + ";" + duracao + ";" + diretor + ";" + idCat + ";" + idClassInd + ";" + assunto + ";" + fontes + System.lineSeparator();
        Files.writeString(CAMINHO_ARQUIVO, linha, StandardOpenOption.APPEND);
    }

    // READ (Todos)
    public static List<String> getAll() throws IOException {
        return Files.readAllLines(CAMINHO_ARQUIVO);
    }

    // READ (Por id)
    public static String getById(int idMidia) throws IOException {
        for (String linha : Files.readAllLines(CAMINHO_ARQUIVO)) {
            if (linha.startsWith(idMidia + ";")) {
                return linha;
            }
        }
        return null;
    }

    // UPDATE
    public static void updateById(int idMidia, String nome, int duracao, String diretor, int idCat, int idClassInd, ArrayList<String> fontes, String assunto) throws IOException {
        List<String> linhas = Files.readAllLines(CAMINHO_ARQUIVO);
        List<String> atualizadas = new ArrayList<>();

        for (String linha : linhas) {
            if (linha.startsWith(idMidia + ";")) {
                atualizadas.add(idMidia + ";" + nome + ";" + duracao + ";" + diretor + ";" + idCat + ";" + idClassInd + ";" + assunto + ";" + fontes);
            } else {
                atualizadas.add(linha);
            }
        }
        Files.write(CAMINHO_ARQUIVO, atualizadas);
    }

    // DELETE
    public static void deleteById(int idMidia) throws IOException {
        List<String> linhas = Files.readAllLines(CAMINHO_ARQUIVO);
        List<String> filtradas = new ArrayList<>();

        for (String linha : linhas) {
            if (!linha.startsWith(idMidia + ";")) {
                filtradas.add(linha);
            }
        }
        Files.write(CAMINHO_ARQUIVO, filtradas);
    }
}
