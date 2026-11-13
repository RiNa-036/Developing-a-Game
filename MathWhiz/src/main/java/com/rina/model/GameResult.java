package com.rina.model;
public class GameResult {
    private final String playerName, difficulty, playedAt;
    private final int score;
    public GameResult(String playerName,int score,String difficulty,String playedAt){
        this.playerName=playerName;this.score=score;this.difficulty=difficulty;this.playedAt=playedAt;
    }
    public String getPlayerName(){return playerName;} public int getScore(){return score;}
    public String getDifficulty(){return difficulty;} public String getPlayedAt(){return playedAt;}
}