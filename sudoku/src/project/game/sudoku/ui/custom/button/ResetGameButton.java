package project.game.sudoku.ui.custom.button;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ResetGameButton extends JButton {

    public ResetGameButton(final ActionListener actionListener) {
        this.setText("Reiniciar");
        this.addActionListener(actionListener);
    }

}
