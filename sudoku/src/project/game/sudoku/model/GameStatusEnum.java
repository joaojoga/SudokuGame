package project.game.sudoku.model;

public enum GameStatusEnum {

    NON_STARTED("ainda não iniciado"),
    INCOMPLETE("incompleto"),
    COMPLETE("completo"), ;

    private String label;

    GameStatusEnum(final String label){
        this.label = label;
    }

    public String getLabel(){
        return label;
    }
}
