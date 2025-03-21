import project.game.sudoku.model.Board;
import project.game.sudoku.model.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;

public class Main {
    private final static Scanner tec = new Scanner(System.in);

    private static Board board;

    private final static int BOARD_LIMIT = 9;

    public static void main(String[] args) {
        final var positions = Stream.of(args)
                .collect(toMap(k -> k.split(";")[0], v -> v.split(";")[1]));

        var option = 1;

        while(true){
            System.out.println("Selecione uma das opções a seguir");
            System.out.println("1 - Iniciar um novo Jogo");
            System.out.println("2 - Colocar um novo número");
            System.out.println("3 - Remover um número");
            System.out.println("4 - Visualizar jogo atual");
            System.out.println("5 - Verificar status do jogo");
            System.out.println("6 - limpar jogo");
            System.out.println("7 - Finalizar jogo");
            System.out.println("8 - Sair");
            option = tec.nextInt();

            switch (option){
                case 1 -> startGame(positions);
                case 2 -> inputNumber();
                case 3 -> removeNumber();
                case 4 -> showCurrentGame();
                case 5 -> showGameStatus();
                case 6 -> clearGame();
                case 7 -> finishGame();
                case 8 -> System.exit(0);
                default -> System.out.println("Opção inválida, selecione uma das opções do menu");
            }
        }
    }

    private static void startGame(final Map<String, String> positions) {
        if(nonNull(board)){
            System.out.println("O jogo já foi iniciado");
            return;
        }
        List<List<Space>> spaces = new ArrayList<>();
        for (int i = 0; i < BOARD_LIMIT; i++) {
            spaces.add(new ArrayList<>());
            for (int j = 0; j < BOARD_LIMIT; j++) {
                var positionConfig = positions.get("%s,%s".formatted(i,j));
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                var currentSpace = new Space(expected, fixed);
                spaces.get(i).add(currentSpace);
            }
        }

        board = new Board(spaces);
        System.out.println("Pronto para iniciar");
    }

    private static void inputNumber() {
        if(isNull(board)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        System.out.println("Informe a coluna que deseja que o numero seja inserido");
        var col = runUntilGetValidValue(0, 8);

        System.out.println("Agora informe a linha que deseja que o numero seja inserido");
        var row = runUntilGetValidValue(0, 8);

        System.out.printf("Informe o numero que vai ser adicionado na posição [%s,%s]\n", col, row);
        var value = runUntilGetValidValue(1, 9);

        if(!board.changeValue(col, row, value)){
            System.out.printf("Erro ao atualizar o valor, pois a posição [%s,%s] tem valor fixo", col, row);
        }
    }

    private static void removeNumber() {
        if(isNull(board)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        System.out.println("Informe a coluna que deseja que o numero seja inserido");
        var col = runUntilGetValidValue(0, 8);

        System.out.println("Agora informe a linha que deseja que o numero seja inserido");
        var row = runUntilGetValidValue(0, 8);

        System.out.printf("Informe o numero que vai ser adicionado na posição [%s,%s]\n", col, row);
        var value = runUntilGetValidValue(1, 9);

        if(!board.clearValue(col, row)){
            System.out.printf("Erro ao remover o valor, pois a posição [%s,%s] tem valor fixo", col, row);
        }
    }

    private static void showCurrentGame() {
    }

    private static void showGameStatus() {
    }

    private static void clearGame() {
    }

    private static void finishGame() {
    }

    private static int runUntilGetValidValue(final int min, final int max){
        int current;

        while (true) {
            try {
                System.out.printf("Insira um valor entre %d e %d: ", min, max);
                String value = tec.next();
                current = Integer.parseInt(value);

                if (current >= min && current <= max) {
                    return current;
                } else {
                    System.out.printf("Número fora do intervalo. Tente inserir um numero entre [%s,%s].", min, max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite apenas números.");
                tec.nextLine();
            }
        }
    }
}