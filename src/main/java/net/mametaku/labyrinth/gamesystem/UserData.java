package net.mametaku.labyrinth.gamesystem;

public class UserData {

    public int Max_HP;
    public int HP;
    public int Max_MP;
    public int MP;
    public int AP;//attackPoint
    public int DP;//defencePoint
    public int SP;//speedPoint
    public int MONEY;
    public Spell[] Having_SPELL;
    public StatusAilment[] SA;
    public Job JOB;

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
