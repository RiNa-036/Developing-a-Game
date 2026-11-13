package com.rina.controller;
import com.rina.dao.ScoreDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
@WebServlet("/leaderboard")
public class LeaderboardServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try { req.setAttribute("scores", new ScoreDAO().getTopScores()); }
        catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("dbError", "Unable to load leaderboard. Check MySQL connection. Reason: " + e);
        }
        req.getRequestDispatcher("/leaderboard.jsp").forward(req, resp);
    }
}