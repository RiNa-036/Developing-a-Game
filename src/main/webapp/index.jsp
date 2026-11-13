<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html><html><head><title>Math Whiz</title><link rel="stylesheet" href="css/style.css"></head><body>
<main class="card hero"><div class="badge">JAVA SERVLET • JDBC • MYSQL</div><h1>Rina Akter – <span>Math Whiz</span></h1>
<p class="muted">A quick mathematical challenge. Four questions. Ten marks.</p>
<form method="post" action="start"><label>Your name</label><input name="playerName" maxlength="100" required placeholder="Enter your name">
<button type="submit">Start Game →</button></form>
<% if(request.getParameter("error")!=null){ %><p class="error">Please enter your name.</p><% } %>
<a class="link" href="leaderboard">View Top 10 Leaderboard</a></main></body></html>