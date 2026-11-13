# Rina Akter – Math Whiz

A web-based mathematical game built with Java Servlet, JSP, JDBC and MySQL. The player enters a name, answers four multiple-choice questions, sees the final score, and the result is saved to a database and shown on a Top 10 leaderboard.

## Project Objectives
- Develop reusable Java methods for mathematical calculations.
- Apply server-side processing with Servlets and JSP.
- Implement JDBC connectivity using `PreparedStatement`.
- Save, retrieve, sort and display database records (leaderboard).
- Document and submit a complete project on GitHub.

## Technologies Used
- Java 17+ (runs on newer JDKs too)
- Jakarta Servlet 6.0 / JSP
- JDBC with MySQL Connector/J
- MySQL
- Apache Tomcat 10.1
- Maven, IntelliJ IDEA

## Software Requirements
- JDK 17 or later
- Apache Tomcat 10.1
- MySQL Server and MySQL Workbench
- Maven (bundled with IntelliJ IDEA)

## Game Rules and Scoring
All answers are calculated by Java methods in `MathService`, not stored manually. Every question is multiple choice with random values and random option order.

| Question | Area | Options | Marks |
|---|---|---|---|
| Q1 | Prime number (1 = Prime, 0 = Not Prime) | 2 (1 - Yes, 0 - No) | 2 |
| Q2 | Modulus (integers only) | 3 (1 correct, 2 wrong) | 2 |
| Q3 | Trigonometry (sin/cos/tan, degrees) | 3 (1 correct, 2 wrong) | 3 |
| Q4 | Trigonometry (sin/cos/tan, degrees) | 3 (1 correct, 2 wrong) | 3 |
| Total | | | 10 |

- `isPrime(int)` returns 1 for prime, otherwise 0. Numbers less than 2 are not prime.
- `calculateMod(int a, int b)` uses `%` and throws a validation error if the divisor is 0.
- `calculateSin/Cos/Tan(double degree)` convert degrees to radians and use `Math.sin/cos/tan`. Tangent returns `NaN` for undefined angles (90°, 270°).
- Trigonometry answers are rounded to 2 decimals and compared with a small tolerance.
- Difficulty is `NORMAL` for all questions and is stored in the database.

## Database Configuration
Default connection: `jdbc:mysql://localhost:3306/math_whiz_db`, user `root`, empty password. To change it, set these environment variables in the Tomcat run configuration:
`MATHWHIZ_DB_URL`, `MATHWHIZ_DB_USER`, `MATHWHIZ_DB_PASSWORD`.

## Database Creation SQL
The same SQL is in `database/math_whiz_db.sql`.

```sql
CREATE DATABASE IF NOT EXISTS math_whiz_db;
USE math_whiz_db;
CREATE TABLE IF NOT EXISTS game_scores (
  id INT AUTO_INCREMENT PRIMARY KEY,
  player_name VARCHAR(100) NOT NULL,
  score INT NOT NULL,
  difficulty VARCHAR(20) NOT NULL DEFAULT 'NORMAL',
  played_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

Leaderboard query: `SELECT ... FROM game_scores ORDER BY score DESC, played_at ASC LIMIT 10;`

## Steps to Run
1. Extract the ZIP and open the `MathWhiz` folder in IntelliJ IDEA (the folder containing `pom.xml`).
2. Open MySQL Workbench and run `database/math_whiz_db.sql`.
3. Let Maven load the dependencies.
4. Add a Tomcat 10.1 server and deploy the artifact `MathWhiz:war exploded`.
5. If your MySQL password is not empty, set `MATHWHIZ_DB_PASSWORD` in the Tomcat run configuration.
6. Run Tomcat and open `http://localhost:8080/MathWhiz/`.

## Project Structure
```
MathWhiz/
├── src/main/java/com/rina/{controller,model,service,dao,config}
├── src/main/webapp/{index,game,result,leaderboard}.jsp, css/style.css
├── database/math_whiz_db.sql
└── README.md
```

## Application Screenshots

### Welcome Page
![Welcome Page](screenshots/home.png)

### Game Page
![Game Page](screenshots/game.png)

### Result Page
![Result Page](screenshots/result.png)

### Leaderboard
![Leaderboard](screenshots/leaderboard.png)

## GitHub Project Directory
https://github.com/username/repository/tree/main/MathWhiz
