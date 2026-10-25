package api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

// Formato da linha: idSessao;tipo(F ou D);idMidia;data;hora
// Exemplo: 1;F;2;26/10/2026;19:30
public class Sessoes {

    private static final Path CAMINHO_ARQUIVO = Paths.get("db", "sessoes.txt");

    // CREATE
    public static void createSessao(int idSessao, String tipo, int idMidia, String data, String hora) throws IOException {
        String linha = idSessao + ";" + tipo + ";" + idMidia + ";" + data + ";" + hora + System.lineSeparator();
        Files.writeString(CAMINHO_ARQUIVO, linha, StandardOpenOption.APPEND);
    }

    // READ (Todos)
    public static List<String> getAll() throws IOException {
        return Files.readAllLines(CAMINHO_ARQUIVO);
    }

    // READ (Por id)
    public static String getById(int idSessao) throws IOException {
        for (String linha : Files.readAllLines(CAMINHO_ARQUIVO)) {
            if (linha.startsWith(idSessao + ";")) {
                return linha;
            }
        }
        return null;
    }

    // Próximo id livre (maior id existente + 1)
    public static int proximoId() throws IOException {
        int maior = 0;
        for (String linha : Files.readAllLines(CAMINHO_ARQUIVO)) {
            String[] dados = linha.split(";");
            int id = Integer.parseInt(dados[0]);
            if (id > maior) {
                maior = id;
            }
        }
        return maior + 1;
    }

    // DELETE
    public static void deleteById(int idSessao) throws IOException {
        List<String> filtradas = new ArrayList<>();

        for (String linha : Files.readAllLines(CAMINHO_ARQUIVO)) {
            if (!linha.startsWith(idSessao + ";")) {
                filtradas.add(linha);
            }
        }
        Files.write(CAMINHO_ARQUIVO, filtradas);
    }
}