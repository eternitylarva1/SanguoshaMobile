package sgsMod.actions.unique;

import sgsMod.helper.SoundController;
import sgsMod.patch.SkinSelectScreen;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import java.util.Random;

public class LineAction extends AbstractGameAction {
    public String ID;
    public AbstractPlayer player;
    public SoundController.SoundType type;
    private final boolean gender;
    private int rate = 10;

    public LineAction(String ID) {
        this(ID, SoundController.SoundType.BattleAndRate, false);
    }

    public LineAction(String ID, SoundController.SoundType type) {
        this(ID, type, false);
    }

    public LineAction(String ID, SoundController.SoundType type, boolean gender) {
        this.ID = ID;
        this.type = type;
        this.gender = gender;
    }

    public void update() {
        //SgsMod.add(ID);
        if(gender && !SkinSelectScreen.getSkin().isMale){
            ID = ID + "_f";
        }

        boolean play = false;
        if(type == SoundController.SoundType.Turn){
            if(!SoundController.turnSoundMap.containsKey(ID)){
                play = true;
                SoundController.turnSoundMap.put(ID, 1);
            }
        }
        else if(type == SoundController.SoundType.Battle || type == SoundController.SoundType.BattleAndRate){
            if(SoundController.getBattleTimes(ID) == 0){
                play = true;
                SoundController.addID(ID);
            }
            else{
                if(type == SoundController.SoundType.BattleAndRate){
                    int rate = new Random().nextInt(100);
                    if(rate <= this.rate){
                        play = true;
                        SoundController.addID(ID);
                    }
                }
            }
        }
        else{
            int rate = new Random().nextInt(100);
            if(rate <= this.rate){
                play = true;
            }
        }

        if(play){

        }

        this.tickDuration();

    }
}