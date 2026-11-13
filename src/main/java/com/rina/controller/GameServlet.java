package com.rina.controller;
import com.rina.dao.ScoreDAO;
import com.rina.model.GameQuestion;
import com.rina.service.MathService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;
@WebServlet({"/start","/submit"})
public class GameServlet extends HttpServlet {
    private static final String DIFFICULTY="NORMAL";
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException {
        req.setCharacterEncoding("UTF-8");
        if("/start".equals(req.getServletPath())) {
            String name=req.getParameter("playerName");
            if(name==null || name.trim().isEmpty()){resp.sendRedirect(req.getContextPath()+"/?error=name");return;}
            HttpSession s=req.getSession(); s.setAttribute("playerName",name.trim());
            s.setAttribute("difficulty",DIFFICULTY);
            List<GameQuestion> questions=List.of(MathService.primeQuestion(DIFFICULTY),MathService.modulusQuestion(DIFFICULTY),
                MathService.trigQuestion(DIFFICULTY),MathService.trigQuestion(DIFFICULTY));
            s.setAttribute("questions",questions);
            req.getRequestDispatcher("/game.jsp").forward(req,resp);
        } else {
            HttpSession s=req.getSession(false);
            if(s==null || s.getAttribute("questions")==null){resp.sendRedirect(req.getContextPath()+"/");return;}
            @SuppressWarnings("unchecked") List<GameQuestion> questions=(List<GameQuestion>)s.getAttribute("questions");
            int score=0; List<Boolean> correctList=new ArrayList<>();
            for(int i=0;i<questions.size();i++){
                boolean correct=MathService.isCorrect(questions.get(i),req.getParameter("answer"+i));
                correctList.add(correct); if(correct) score+=questions.get(i).getMarks();
            }
            String name=(String)s.getAttribute("playerName"), difficulty=(String)s.getAttribute("difficulty");
            boolean saved=true; String message="Score saved successfully.";
            try {new ScoreDAO().save(name,score,difficulty);} catch(Exception e){e.printStackTrace();saved=false;message="Could not save score. Reason: "+e;}
            req.setAttribute("questions",questions);req.setAttribute("correctList",correctList);
            req.setAttribute("score",score);req.setAttribute("playerName",name);req.setAttribute("saveMessage",message);
            s.removeAttribute("questions");
            req.getRequestDispatcher("/result.jsp").forward(req,resp);
        }
    }
}