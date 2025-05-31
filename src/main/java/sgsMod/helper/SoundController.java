package sgsMod.helper;

import java.util.HashMap;
import java.util.Map;

public class SoundController {
    public static Map<String, Integer> battleSoundMap = new HashMap<>();//本场战斗播放过的音效
    public static Map<String, Integer> turnSoundMap = new HashMap<>();//本回合播放过的音效

    public static void clearAll(){
        battleSoundMap.clear(); //每场战斗开始时，清除已记录的音效
        turnSoundMap.clear();
    }

    public static void clearTurnBased(){
        turnSoundMap.clear();
    }

    public static int getBattleTimes(String ID){
        return battleSoundMap.getOrDefault(ID, 0);
    }

    public static void addID(String ID){
        int time = battleSoundMap.getOrDefault(ID, 0);
        battleSoundMap.put(ID, time + 1);
    }



    public static enum SoundType{
        Turn, //每回合只能响起一次的音效，
        Battle, //每场战斗只有第一次触发时响起，后续不再触发
        BattleAndRate, //第一次触发必定响起，后续以很低的概率触发
        Rate //按照固定的概率触发
    }


}


