package project.game.sudoku.ui.custom.panel;

import project.game.sudoku.ui.custom.input.NumberText;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.util.List;
import java.awt.*;

public class SudokuSector extends JPanel {

    public SudokuSector(final List<NumberText> textFields) {
        var dimension = new Dimension(170,170);
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setBorder(new LineBorder(Color.black,2,true));
        this.setVisible(true);
        textFields.forEach(this::add);
    }
}
