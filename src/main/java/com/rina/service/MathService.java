package com.rina.service;
import com.rina.model.GameQuestion;
import java.util.*;
public class MathService {
    private static final Random RANDOM = new Random();
    public static int isPrime(int number) {
        if(number < 2) return 0;
        for(int i=2;i<=Math.sqrt(number);i++) if(number%i==0) return 0;
        return 1;
    }
    public static int calculateMod(int a,int b) {
        if(b==0) throw new IllegalArgumentException("Validation error: divisor must not be zero.");
        return a%b;
    }
    public static double calculateSin(double degree){return Math.sin(Math.toRadians(degree));}
    public static double calculateCos(double degree){return Math.cos(Math.toRadians(degree));}
    public static double calculateTan(double degree){
        double d=((degree%360)+360)%360;
        if(d==90 || d==270) return Double.NaN; // tangent is undefined here
        return Math.tan(Math.toRadians(degree));
    }
    private static int bound(String difficulty) {
        if("EASY".equalsIgnoreCase(difficulty)) return 30;
        if("HARD".equalsIgnoreCase(difficulty)) return 200;
        return 100;
    }
    // Every question has exactly 3 options: 1 correct + 2 wrong, in random order.
    private static GameQuestion mcq(String type,String prompt,String operator,int marks,String correct,Collection<String> wrong) {
        List<String> options=new ArrayList<>(wrong); options.add(correct);
        Collections.shuffle(options,RANDOM);
        return new GameQuestion(type,prompt,operator,marks,correct,options);
    }
    public static GameQuestion primeQuestion(String difficulty) {
        int n=RANDOM.nextInt(bound(difficulty))+1;
        // Only two possible answers, so prime has exactly 2 options: 1 (Yes) and 0 (No).
        String correct=isPrime(n)==1 ? "1 - Yes" : "0 - No";
        return new GameQuestion("prime","Is "+n+" a prime number?","",2,correct,List.of("1 - Yes","0 - No"));
    }
    public static GameQuestion modulusQuestion(String difficulty) {
        int max=bound(difficulty), a=RANDOM.nextInt(max)+1, b=RANDOM.nextInt(max-1)+2;
        int correct=calculateMod(a,b);
        LinkedHashSet<String> wrong=new LinkedHashSet<>();
        while(wrong.size()<2) {
            int candidate=correct+(RANDOM.nextBoolean()?1:-1)*(1+RANDOM.nextInt(5));
            if(candidate>=0 && candidate!=correct) wrong.add(String.valueOf(candidate));
        }
        return mcq("mod","What is "+a+" % "+b+" ?","",2,String.valueOf(correct),wrong);
    }
    public static GameQuestion trigQuestion(String difficulty) {
        String[] ops={"sin","cos","tan"};
        String op=ops[RANDOM.nextInt(ops.length)];
        int degree;
        do { degree=RANDOM.nextInt(360); } while(op.equals("tan") && degree%180==90);
        double answer=switch(op){case "sin"->calculateSin(degree);case "cos"->calculateCos(degree);default->calculateTan(degree);};
        // Keep choices distinct after rounding; exactly one option is correct.
        double correct=round(answer);
        LinkedHashSet<Double> values=new LinkedHashSet<>(); values.add(correct);
        while(values.size()<3) {
            double candidate=round(correct + (RANDOM.nextBoolean()?1:-1)*(0.15+RANDOM.nextInt(10)*0.1));
            if(Math.abs(candidate-correct)>0.02) values.add(candidate);
        }
        List<Double> shuffled=new ArrayList<>(values); Collections.shuffle(shuffled,RANDOM);
        List<String> options=new ArrayList<>(); for(double v:shuffled) options.add(String.format(Locale.US,"%.2f",v));
        return new GameQuestion("trig","Choose the correct value of "+op+"("+degree+"°).",op,3,String.format(Locale.US,"%.2f",correct),options);
    }
    private static double round(double n){return Math.round(n*100.0)/100.0;}
    public static boolean isCorrect(GameQuestion q,String answer) {
        if(answer==null) return false;
        try {
            // Decimal (trigonometry) answers are compared with a small tolerance.
            if(q.getType().equals("trig")) return Math.abs(Double.parseDouble(answer.trim())-Double.parseDouble(q.getCorrectAnswer()))<=0.011;
        } catch(NumberFormatException e){ return false; }
        return answer.trim().equals(q.getCorrectAnswer());
    }
}
