package principal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import api.Users;
import api.Filmes;
import api.Documentarios;
import api.Sessoes;
import api.Ingressos;
import ingresso.Ingresso;
import props_ingresso.Cliente;
import props_ingresso.Sessao;
import props_sessao.Assento;
import props_sessao.Documentario;
import props_sessao.Filme;
import props_sessao.Midia;

public class Principal {

    private static final Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    public static void main(String[] args) {

        try {
            prepararArquivos();

            int opcao;

            do {
                System.out.println("\n===== CINEMA =====");
                System.out.println("1 - Cadastrar usuário");
                System.out.println("2 - Fazer login");
                System.out.println("0 - Sair");
                System.out.print("Escolha: ");

                opcao = lerInt();

                switch (opcao) {
                    case 1:
                        cadastrarUsuario();
                        break;

                    case 2:
                        Cliente logado = login();
                        if (logado != null) {
                            menuCinema(logado);
                        }
                        break;

                    case 0:
                        System.out.println("Sistema encerrado.");
                        break;

                    default:
                        System.out.println("Opção inválida!");
                }

            } while (opcao != 0);

        } catch (IOException e) {
            System.out.println("Erro ao acessar os arquivos: " + e.getMessage());
        }

        sc.close();
    }

    // Prepara a pasta e os arquivos antes de usar a API
    private static void prepararArquivos() throws IOException {
        Files.createDirectories(Paths.get("db"));

        System.out.println("Pasta do banco: " + Paths.get("db").toAbsolutePath());

        String[] arquivos = {
            "users.txt",
            "filmes.txt",
            "documentarios.txt",
            "sessoes.txt",
            "ingressos.txt"
        };

        for (String nome : arquivos) {
            Path caminho = Paths.get("db", nome);

            if (!Files.exists(caminho)) {
                Files.createFile(caminho);
            }

            // Limpa o arquivo: remove BOM, linhas em branco e espaços sobrando
            List<String> limpas = new ArrayList<>();

            for (String linha : Files.readAllLines(caminho)) {
                linha = linha.replace("\uFEFF", "").trim();

                if (!linha.isEmpty()) {
                    limpas.add(linha);
                }
            }

            Files.write(caminho, limpas);
        }
    }

    // Lê números inteiros sem encerrar o programa
    private static int lerInt() {
        while (!sc.hasNextInt()) {
            System.out.print("Digite um número válido: ");
            sc.nextLine();
        }

        int numero = sc.nextInt();
        sc.nextLine();

        return numero;
    }

    // Cadastro de usuário
    private static void cadastrarUsuario() throws IOException {
        System.out.println("\n===== CADASTRO =====");

        System.out.print("Email: ");
        String email = sc.nextLine().trim();

        if (email.isEmpty() || email.contains(";")) {
            System.out.println("Email inválido.");
            return;
        }

        if (Users.getById(email) != null) {
            System.out.println("Esse email já está cadastrado.");
            return;
        }

        System.out.print("Senha: ");
        String senha = sc.nextLine();

        if (senha.isEmpty() || senha.contains(";")) {
            System.out.println("Senha inválida.");
            return;
        }

        Cliente cliente = new Cliente(email, senha);
        Users.createUser(cliente.getEmail(), cliente.getSenha(), cliente.getRole());

        System.out.println("Usuário cadastrado com sucesso!");
    }

    // Login: devolve o Cliente logado, ou null se falhar
    private static Cliente login() throws IOException {
        System.out.println("\n===== LOGIN =====");

        System.out.print("Email: ");
        String email = sc.nextLine().trim();

        System.out.print("Senha: ");
        String senha = sc.nextLine();

        String usuario = Users.getById(email);

        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return null;
        }

        String[] dados = usuario.split(";", -1);

        if (dados.length < 3 || !dados[1].equals(senha)) {
            System.out.println("Senha incorreta.");
            return null;
        }

        System.out.println("Login realizado com sucesso!");
        System.out.println("Bem-vindo(a), " + email);

        return new Cliente(dados[0], dados[1]);
    }

    // Menu principal do cinema
    private static void menuCinema(Cliente cliente) throws IOException {
        int opcao;

        do {
            System.out.println("\n===== MENU DO CINEMA =====");
            System.out.println("1 - Listar filmes");
            System.out.println("2 - Listar documentários");
            System.out.println("3 - Buscar filme por ID");
            System.out.println("4 - Buscar documentário por ID");
            System.out.println("5 - Cadastrar filme");
            System.out.println("6 - Cadastrar documentário");
            System.out.println("7 - Excluir filme");
            System.out.println("8 - Excluir documentário");
            System.out.println("9 - Criar sessão");
            System.out.println("10 - Listar sessões");
            System.out.println("11 - Comprar ingresso");
            System.out.println("12 - Meus ingressos");
            System.out.println("0 - Logout");
            System.out.print("Escolha: ");

            opcao = lerInt();

            switch (opcao) {
                case 1:
                    listar(Filmes.getAll());
                    break;

                case 2:
                    listar(Documentarios.getAll());
                    break;

                case 3:
                    buscarFilme();
                    break;

                case 4:
                    buscarDocumentario();
                    break;

                case 5:
                    cadastrarFilme();
                    break;

                case 6:
                    cadastrarDocumentario();
                    break;

                case 7:
                    excluirFilme();
                    break;

                case 8:
                    excluirDocumentario();
                    break;

                case 9:
                    criarSessao();
                    break;

                case 10:
                    listarSessoes();
                    break;

                case 11:
                    comprarIngresso(cliente);
                    break;

                case 12:
                    meusIngressos(cliente);
                    break;

                case 0:
                    System.out.println("Logout realizado.");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    // Exibe as linhas retornadas pela API
    private static void listar(List<String> dados) {
        System.out.println();

        if (dados.isEmpty()) {
            System.out.println("Nenhum registro encontrado.");
            return;
        }

        for (String linha : dados) {
            System.out.println(linha);
        }
    }

    // Busca de filme
    private static void buscarFilme() throws IOException {
        System.out.print("ID do filme: ");
        int id = lerInt();

        String filme = Filmes.getById(id);

        if (filme == null) {
            System.out.println("Filme não encontrado.");
        } else {
            System.out.println(filme);
        }
    }

    // Busca de documentário
    private static void buscarDocumentario() throws IOException {
        System.out.print("ID do documentário: ");
        int id = lerInt();

        String doc = Documentarios.getById(id);

        if (doc == null) {
            System.out.println("Documentário não encontrado.");
        } else {
            System.out.println(doc);
        }
    }

    // Converte uma entrada separada por vírgulas em uma lista
    private static ArrayList<String> lerLista(String mensagem) {
        System.out.print(mensagem);
        String entrada = sc.nextLine().trim();

        ArrayList<String> lista = new ArrayList<>();

        if (!entrada.isEmpty()) {
            for (String item : entrada.split(",")) {
                lista.add(item.trim());
            }
        }

        return lista;
    }

    // Cadastro de filme
    private static void cadastrarFilme() throws IOException {
        System.out.println("\n===== NOVO FILME =====");

        System.out.print("ID: ");
        int id = lerInt();

        if (Filmes.getById(id) != null) {
            System.out.println("Já existe um filme com esse ID.");
            return;
        }

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Duração em minutos: ");
        int duracao = lerInt();

        System.out.print("Diretor: ");
        String diretor = sc.nextLine();

        System.out.print("ID da categoria: ");
        int idCat = lerInt();

        System.out.print("ID da classificação indicativa: ");
        int idClassInd = lerInt();

        System.out.print("Roteirista: ");
        String roteirista = sc.nextLine();

        ArrayList<String> elenco = lerLista("Atores separados por vírgula: ");

        Filmes.createFilme(id, nome, duracao, diretor, idCat, idClassInd, elenco, roteirista);

        System.out.println("Filme cadastrado!");
    }

    // Cadastro de documentário
    private static void cadastrarDocumentario() throws IOException {
        System.out.println("\n===== NOVO DOCUMENTÁRIO =====");

        System.out.print("ID: ");
        int id = lerInt();

        if (Documentarios.getById(id) != null) {
            System.out.println("Já existe um documentário com esse ID.");
            return;
        }

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Duração em minutos: ");
        int duracao = lerInt();

        System.out.print("Diretor: ");
        String diretor = sc.nextLine();

        System.out.print("ID da categoria: ");
        int idCat = lerInt();

        System.out.print("ID da classificação indicativa: ");
        int idClassInd = lerInt();

        System.out.print("Assunto: ");
        String assunto = sc.nextLine();

        ArrayList<String> fontes = lerLista("Fontes separadas por vírgula: ");

        Documentarios.createDoc(id, nome, duracao, diretor, idCat, idClassInd, fontes, assunto);

        System.out.println("Documentário cadastrado!");
    }

    // Exclusão de filme
    private static void excluirFilme() throws IOException {
        System.out.print("ID do filme que deseja excluir: ");
        int id = lerInt();

        if (Filmes.getById(id) == null) {
            System.out.println("Filme não encontrado.");
            return;
        }

        Filmes.deleteById(id);
        System.out.println("Filme excluído.");
    }

    // Exclusão de documentário
    private static void excluirDocumentario() throws IOException {
        System.out.print("ID do documentário que deseja excluir: ");
        int id = lerInt();

        if (Documentarios.getById(id) == null) {
            System.out.println("Documentário não encontrado.");
            return;
        }

        Documentarios.deleteById(id);
        System.out.println("Documentário excluído.");
    }

    // ===================== SESSÕES E INGRESSOS =====================

    // Criação de sessão
    private static void criarSessao() throws IOException {
        System.out.println("\n===== NOVA SESSÃO =====");

        System.out.print("Tipo (F = Filme, D = Documentário): ");
        String tipo = sc.nextLine().trim().toUpperCase();

        if (!tipo.equals("F") && !tipo.equals("D")) {
            System.out.println("Tipo inválido.");
            return;
        }

        System.out.print("ID do " + (tipo.equals("F") ? "filme" : "documentário") + ": ");
        int idMidia = lerInt();

        String midia = tipo.equals("F") ? Filmes.getById(idMidia) : Documentarios.getById(idMidia);

        if (midia == null) {
            System.out.println("Não encontrado.");
            return;
        }

        System.out.print("Data (dd/MM/aaaa): ");
        String dataTexto = sc.nextLine().trim();

        System.out.print("Hora (HH:mm): ");
        String horaTexto = sc.nextLine().trim();

        try {
            LocalDate data = LocalDate.parse(dataTexto, FORMATO_DATA);
            LocalTime hora = LocalTime.parse(horaTexto);

            int id = Sessoes.proximoId();
            Sessoes.createSessao(id, tipo, idMidia, data.format(FORMATO_DATA), hora.toString());

            System.out.println("Sessão " + id + " criada!");

        } catch (DateTimeParseException e) {
            System.out.println("Data ou hora inválida.");
        }
    }

    // Lista as sessões de forma legível
    private static void listarSessoes() throws IOException {
        System.out.println();

        List<String> sessoes = Sessoes.getAll();

        if (sessoes.isEmpty()) {
            System.out.println("Nenhuma sessão cadastrada.");
            return;
        }

        for (String linha : sessoes) {
            System.out.println(descreverSessao(linha));
        }
    }

    // Transforma a linha da sessão em texto: "Sessão 1 | Matrix | 26/10/2026 às 19:30"
    private static String descreverSessao(String linhaSessao) throws IOException {
        String[] s = linhaSessao.split(";");
        int idMidia = Integer.parseInt(s[2]);

        String linhaMidia = s[1].equals("F") ? Filmes.getById(idMidia) : Documentarios.getById(idMidia);
        String nome = (linhaMidia == null) ? "(removido)" : linhaMidia.split(";")[1];

        return "Sessão " + s[0] + " | " + nome + " | " + s[3] + " às " + s[4];
    }

    // Mostra a sala 10x10: O = livre, X = ocupado
    private static void mostrarAssentos(int idSessao) throws IOException {
        List<String> ocupados = Ingressos.getAssentosOcupados(idSessao);

        System.out.println("\n(O = livre, X = ocupado)");
        System.out.print("    ");
        for (int n = 1; n <= 10; n++) {
            System.out.printf("%-4d", n);
        }
        System.out.println();

        for (char letra = 'A'; letra <= 'J'; letra++) {
            System.out.print(letra + "   ");
            for (int n = 1; n <= 10; n++) {
                System.out.print(ocupados.contains("" + letra + n) ? "X   " : "O   ");
            }
            System.out.println();
        }
    }

    // Compra de ingresso
    private static void comprarIngresso(Cliente cliente) throws IOException {
        System.out.println("\n===== COMPRAR INGRESSO =====");

        if (Sessoes.getAll().isEmpty()) {
            System.out.println("Nenhuma sessão disponível.");
            return;
        }

        listarSessoes();

        System.out.print("\nID da sessão: ");
        int idSessao = lerInt();

        String linhaSessao = Sessoes.getById(idSessao);

        if (linhaSessao == null) {
            System.out.println("Sessão não encontrada.");
            return;
        }

        mostrarAssentos(idSessao);

        System.out.print("\nAssento (de A1 até J10): ");
        String assentoTexto = sc.nextLine().trim().toUpperCase();

        // Valida o assento: uma letra de A a J seguida de um número de 1 a 10
        char letra;
        int numero;

        try {
            letra = assentoTexto.charAt(0);
            numero = Integer.parseInt(assentoTexto.substring(1));
        } catch (RuntimeException e) {
            System.out.println("Assento inválido.");
            return;
        }

        if (letra < 'A' || letra > 'J' || numero < 1 || numero > 10) {
            System.out.println("Assento inválido. Use de A1 até J10.");
            return;
        }

        String assentoCodigo = "" + letra + numero; // "A01" vira "A1"

        // Aqui acontece a recusa se o assento já estiver ocupado
        if (Ingressos.assentoOcupado(idSessao, assentoCodigo)) {
            System.out.println("O assento " + assentoCodigo + " já está ocupado! Escolha outro.");
            return;
        }

        Ingressos.createIngresso(cliente.getEmail(), idSessao, assentoCodigo);

        // Monta os objetos só para emitir o ingresso
        Sessao sessao = montarSessao(linhaSessao);
        Assento assento = new Assento(letra, numero, sessao);
        assento.ocupar();

        Ingresso ingresso = new Ingresso(cliente, sessao, assento);
        ingresso.exibir();

        System.out.println("\nCompra realizada com sucesso!");
    }

    // Cria um objeto Sessao (com Filme ou Documentario dentro) a partir da linha do arquivo
    private static Sessao montarSessao(String linhaSessao) throws IOException {
        String[] s = linhaSessao.split(";");
        int idMidia = Integer.parseInt(s[2]);

        Midia midia;

        if (s[1].equals("F")) {
            String[] f = Filmes.getById(idMidia).split(";");
            midia = new Filme(idMidia, f[1], Integer.parseInt(f[2]), f[3],
                    Integer.parseInt(f[4]), Integer.parseInt(f[5]), f[6], new ArrayList<String>());
        } else {
            String[] d = Documentarios.getById(idMidia).split(";");
            midia = new Documentario(idMidia, d[1], Integer.parseInt(d[2]), d[3],
                    Integer.parseInt(d[4]), Integer.parseInt(d[5]), d[6], new ArrayList<String>());
        }

        LocalDate data = LocalDate.parse(s[3], FORMATO_DATA);
        LocalTime hora = LocalTime.parse(s[4]);

        return new Sessao(midia, data, hora);
    }

    // Lista os ingressos do usuário logado
    private static void meusIngressos(Cliente cliente) throws IOException {
        System.out.println("\n===== MEUS INGRESSOS =====");

        List<String> ingressos = Ingressos.getByEmail(cliente.getEmail());

        if (ingressos.isEmpty()) {
            System.out.println("Você ainda não comprou ingressos.");
            return;
        }

        for (String linha : ingressos) {
            String[] i = linha.split(";");
            String linhaSessao = Sessoes.getById(Integer.parseInt(i[1]));

            String descricao = (linhaSessao == null) ? "Sessão " + i[1] + " (removida)" : descreverSessao(linhaSessao);

            System.out.println(descricao + " | Assento " + i[2]);
        }
    }
}