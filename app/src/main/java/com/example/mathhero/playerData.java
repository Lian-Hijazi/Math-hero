package com.example.mathhero;

public class playerData {
    private int score,level,hint;

    public playerData(int score,int level,int hint){
        this.score=score;
        this.level=level;
        this.hint=hint;
    }

    public void setScore(int score){
        this.score=score;
    }

    public void setLevel(int level){
        this.level=level;
    }

    public void setHint(int hint){
        this.hint=hint;
    }

    public int getScore(){
        return score;
    }

    public int getHint() {
        return hint;
    }

    public int getLevel(){
        return level;
    }



}
