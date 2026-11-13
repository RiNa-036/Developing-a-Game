package com.rina.model;
import java.util.List;
public class GameQuestion {
    private final String type, prompt, operator, correctAnswer;
    private final int marks;
    private final List<String> options;
    public GameQuestion(String type, String prompt, String operator, int marks, String correctAnswer, List<String> options) {
        this.type=type; this.prompt=prompt; this.operator=operator; this.marks=marks;
        this.correctAnswer=correctAnswer; this.options=options;
    }
    public String getType(){return type;} public String getPrompt(){return prompt;}
    public String getOperator(){return operator;} public int getMarks(){return marks;}
    public String getCorrectAnswer(){return correctAnswer;} public List<String> getOptions(){return options;}
}
