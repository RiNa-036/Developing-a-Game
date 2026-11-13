package com.rina.dao;
import com.rina.config.DB;
import com.rina.model.GameResult;
import java.sql.*;
import java.util.*;
public class ScoreDAO {
    public void save(String name,int score,String difficulty) throws SQLException {
        String sql="INSERT INTO game_scores(player_name,score,difficulty) VALUES(?,?,?)";
        try(Connection con=DB.getConnection();PreparedStatement ps=con.prepareStatement(sql)){
            ps.setString(1,name);ps.setInt(2,score);ps.setString(3,difficulty);ps.executeUpdate();
        }
    }
    public List<GameResult> getTopScores() throws SQLException {
        List<GameResult> rows=new ArrayList<>();
        String sql="SELECT player_name,score,difficulty,DATE_FORMAT(played_at,'%Y-%m-%d %H:%i') played_at FROM game_scores ORDER BY score DESC, played_at ASC LIMIT 10";
        try(Connection con=DB.getConnection();PreparedStatement ps=con.prepareStatement(sql);ResultSet rs=ps.executeQuery()){
            while(rs.next()) rows.add(new GameResult(rs.getString("player_name"),rs.getInt("score"),rs.getString("difficulty"),rs.getString("played_at")));
        } return rows;
    }
}