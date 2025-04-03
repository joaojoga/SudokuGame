package project.game.sudoku.service;

import project.game.sudoku.model.Board;
import project.game.sudoku.model.DifficultyLevel;
import project.game.sudoku.model.GameStatusEnum;
import project.game.sudoku.model.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardService {
    private final static int BOARD_LIMIT = 9;

    private final Board board;

    public BoardService(final Map<String, String> gameConfig, DifficultyLevel level) {
        this.board = new Board(initBoard(gameConfig, level));
    }

    public List<List<Space>> getSpaces() {
        return board.getSpaces();
    }

    public void reset(){
        board.reset();
    }

    public boolean hasErrors(){
        return board.hasErrors();
    }

    public GameStatusEnum getGameStatus(){
        return board.getGameStatus();
    }

    public boolean gameOver(){
        return board.gameOver();
    }

    private List<List<Space>> initBoard(final Map<String,String> gameConfig, DifficultyLevel level) {
        List<List<Space>> spaces = new ArrayList<>();

        for (int i = 0; i < BOARD_LIMIT; i++) {
            spaces.add(new ArrayList<>());
            for (int j = 0; j < BOARD_LIMIT; j++) {
                var positionConfig = gameConfig.get("%s,%s".formatted(i, j));
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);

                if (level == DifficultyLevel.MEDIUM && Math.random() < 0.4) {
                    fixed = false;
                } else if (level == DifficultyLevel.HARD && Math.random() < 0.6) {
                    fixed = false;
                }

                var currentSpace = new Space(expected, fixed);
                spaces.get(i).add(currentSpace);
            }
        }
        return spaces;
    }

}
