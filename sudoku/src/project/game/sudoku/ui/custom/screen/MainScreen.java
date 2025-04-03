package project.game.sudoku.ui.custom.screen;

import project.game.sudoku.model.Space;
import project.game.sudoku.service.BoardService;
import project.game.sudoku.service.NotifierService;
import project.game.sudoku.ui.custom.button.CheckGameStatusButton;
import project.game.sudoku.ui.custom.button.FinishGameButton;
import project.game.sudoku.ui.custom.button.ResetGameButton;
import project.game.sudoku.ui.custom.frame.MainFrame;
import project.game.sudoku.ui.custom.input.NumberText;
import project.game.sudoku.ui.custom.panel.MainPanel;
import project.game.sudoku.ui.custom.panel.SudokuSector;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

import static javax.swing.JOptionPane.*;
import static project.game.sudoku.service.EventEnum.CLEAR_SPACE;

public class MainScreen {
    private final static Dimension dimension = new Dimension(600,600);
    private final BoardService boardService;
    private final NotifierService notifierService;

    private JButton checkGameStatusButton;
    private JButton resetGameButton;
    private JButton finishGameButton;

    public MainScreen(final Map<String, String> gameConfig) {
        this.boardService = new BoardService(gameConfig);
        this.notifierService = new NotifierService();
    }

    public void buildMainScreen() {
        JPanel mainPanel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);
        for(int r = 0; r < 9; r+=3){
            var endRow = r + 2;
            for(int c = 0; c < 9; c+=3) {
                var endCol = c + 2;
                var spaces = getSpacesFromSector(boardService.getSpaces(), c, endCol, r, endRow);
                JPanel sector = generateSection(spaces);
                mainPanel.add(sector);
            }
        }
        addResetGameButton(mainPanel);
        addShowGameStatusButton(mainPanel);
        addFinishGameButton(mainPanel);

        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private List<Space> getSpacesFromSector(final List<List<Space>> spaces,
                                            final int initColl, final int endColl,
                                            final int initRow, final int endRow) {
        List<Space> spaceSector = new ArrayList<>();
        for(int r = initRow; r <= endRow; r++){
            for (int c = initColl; c <= endColl; c++){
                spaceSector.add(spaces.get(c).get(r));
            }
        }
        return spaceSector;
    }

    private JPanel generateSection(final List<Space> spaces){
        List<NumberText> fields = new ArrayList<>(spaces.stream().map(NumberText::new).toList());
        fields.forEach(t -> notifierService.subscribe(CLEAR_SPACE, t));
        return new SudokuSector(fields);
    }

    private void addFinishGameButton(JPanel mainPanel) {
        finishGameButton = new FinishGameButton(e -> {
            if(boardService.gameOver()){
                showMessageDialog(null, "Parabéns, você concluiu o jogo");
                resetGameButton.setEnabled(false);
                checkGameStatusButton.setEnabled(false);
                finishGameButton.setEnabled(false);
            }else{
                var message = "Seu jogo tem algo de errado, ajuste e tente novamente";
                showMessageDialog(null,message);
            }
        });
        mainPanel.add(finishGameButton);

    }

    private void addResetGameButton(final JPanel mainPanel) {
        resetGameButton = new ResetGameButton(e -> {
            var dialogResult = JOptionPane.showConfirmDialog(
                    null,
                    "Deseja realmente reiniciar o jogo?",
                    "Limpar o jogo",
                    YES_NO_OPTION, 
                    QUESTION_MESSAGE
            );
            if(dialogResult == 0){
                boardService.reset();
                notifierService.notify(CLEAR_SPACE);
            }
        });
        mainPanel.add(resetGameButton);
    }

    private void addShowGameStatusButton(JPanel mainPanel) {
         checkGameStatusButton = new CheckGameStatusButton(e -> {
            var hasErrors = boardService.hasErrors();
            var gameStatus = boardService.getGameStatus();
            var message = switch (gameStatus){
                case NON_STARTED -> "O jogo não foi iniciado";
                case INCOMPLETE -> "O jogo esta incompleto";
                case COMPLETE -> "O jogo esta completo";
            };
            message += hasErrors ? " e contém erros" : " e não contem erros";
            showMessageDialog(mainPanel, message);
        });
        mainPanel.add(checkGameStatusButton);
    }

}
