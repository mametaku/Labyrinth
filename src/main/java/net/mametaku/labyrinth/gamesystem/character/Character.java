package net.mametaku.labyrinth.gamesystem.character;

import net.mametaku.labyrinth.gamesystem.LabyrinthEntity;

public class Character extends LabyrinthEntity {

    protected void setName(String enterName){
        name =  enterName;
    }

    protected void set_Max_HP(int enterValue){
        Max_HP = enterValue;
    }

    protected void set_HP(int enterValue){
        HP = enterValue;
    }

    protected void set_Max_MP(int enterValue){
        Max_MP = enterValue;
    }

    protected void set_MP(int enterValue){
        MP = enterValue;
    }
}
