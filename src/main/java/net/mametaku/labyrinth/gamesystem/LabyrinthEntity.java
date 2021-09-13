package net.mametaku.labyrinth.gamesystem;

public class LabyrinthEntity {

    protected String name;
    protected String skinURL;
    protected int Max_HP;
    protected int HP;
    protected int Max_MP;
    protected int MP;
    protected int AP;//attackPoint
    protected int DP;//defencePoint
    protected int SP;//speedPoint
    protected int MONEY;
    protected String[] MSG;
    protected LabyrinthEntity.Spell[] Having_SPELL;
    protected LabyrinthEntity.StatusAilment[] SA;
    protected LabyrinthEntity.Job JOB;

    public enum Job{
        WARRIOR,
        WIZARD,
    }

    public enum Spell{

        BURN("メラメラ",StatusAilment.BURN);

        private final String label;
        private final  StatusAilment add_SA;

        Spell(String label,StatusAilment add_SA) {   //コンストラクタはprivateで宣言
            this.label = label;
            this.add_SA = add_SA;
        }


        public String getLabel() {
            return label;
        }

        public StatusAilment getAdd_SA(){
             return add_SA;
        }
    }

    public enum StatusAilment{
        BURN("やけど"),
        SLEEP("ねむり"),
        POISON("どく");

        private final String label;

        StatusAilment(String label) {   //コンストラクタはprivateで宣言
            this.label = label;
        }


        public String getLabel() {
            return label;
        }
    }
}
