package api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

// Formato da linha: email;idSessao;assento
// Exemplo: joao@x.com;1;A3
// Um assento está ocupado se já existe uma linha com aquela sessão e aquele assento.
public class Ingressos {

    private static final Path CAMINHO_ARQUIVO = Paths.get("db", "ingressos.txt");

    // CREATE
    public static void createIngresso(String email, int idSessao, String assento) throws IOException {
        String linha = email + ";" + idSessao + ";" + assento + System.lineSeparator();
        Files.writeString(CAMINHO_ARQUIVO, linha, StandardOpenOption.APPEND);
    }

    // READ (Todos)
    public static List<String> getAll() throws IOException {
        return Files.readAllLines(CAMINHO_ARQUIVO);
    }

    // READ (Ingressos de um cliente)
    public static List<String> getByEmail(String email) throws IOException {
        List<String> resultado = new ArrayList<>();

        for (String linha : Files.readAllLines(CAMINHO_ARQUIVO)) {
            if (linha.startsWith(email + ";")) {
                resultado.add(linha);
            }
        }
        return resultado;
    }

    // READ (Assentos já ocupados de uma sessão, ex: [A1, B5])
    public static List<String> getAssentosOcupados(int idSessao) throws IOException {
        List<String> ocupados = new ArrayList<>();

        for (String linha : Files.readAllLines(CAMINHO_ARQUIVO)) {
            String[] dados = linha.split(";");
            if (dados[1].equals(String.valueOf(idSessao))) {
                ocupados.add(dados[2]);
            }
        }
        return ocupados;
    }

    // O assento está ocupado nessa sessão?
    public static boolean assentoOcupado(int idSessao, String assento) throws IOException {
        return getAssentosOcupados(idSessao).contains(assento);
    }
}