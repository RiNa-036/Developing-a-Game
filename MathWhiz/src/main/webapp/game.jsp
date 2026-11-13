<%@ page import="java.util.*,com.rina.model.GameQuestion" contentType="text/html;charset=UTF-8" %>
<% List<GameQuestion> qs=(List<GameQuestion>)session.getAttribute("questions"); if(qs==null){response.sendRedirect(request.getContextPath()+"/");return;} String player=(String)session.getAttribute("playerName"); %>
<!DOCTYPE html><html><head><title>Math Whiz - Play</title><link rel="stylesheet" href="css/style.css"></head><body><main class="wrap">
<header><div><div class="badge">PLAYER: <%= player.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;") %></div><h1>Math Challenge</h1></div><div class="scorebox">TOTAL <b>10</b> MARKS</div></header>
<form method="post" action="submit">
<% for(int i=0;i<qs.size();i++){GameQuestion q=qs.get(i); %><section class="question"><div class="qtop"><b>Question <%=i+1%></b><span><%=q.getMarks()%> Marks</span></div><h2><%=q.getPrompt()%></h2>
<% for(int j=0;j<q.getOptions().size();j++){String opt=q.getOptions().get(j); %>
<label class="option"><input type="radio" name="answer<%=i%>" value="<%=opt%>" required><span><%= (char)('A'+j) %>.</span> <%=opt%></label>
<% } %>
</section><% } %><button type="submit">Submit Answers</button></form></main></body></html>