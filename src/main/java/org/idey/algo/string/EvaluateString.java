package org.idey.algo.string;

import java.util.Stack;

public class EvaluateString {

    public static int evaluate(String str){
        Stack<Integer> values = new Stack<>();
        Stack<Character> ops = new Stack<>();
        char[] tokens =str.toCharArray();
        for(int i=0;i<tokens.length;i++){
            if(tokens[i]==' ')
                continue;

            if (tokens[i] >= '0' && tokens[i] <= '9'){
                StringBuilder builder = new StringBuilder();
                while (i<tokens.length && tokens[i]>='0' && tokens[i]<='9')
                    builder.append(tokens[i++]);
                values.push(Integer.parseInt(builder.toString()));
                i--;
            }else if (tokens[i]=='('){
                ops.push(tokens[i]);
            }else if(tokens[i]==')'){
                while (ops.peek()!='('){
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.pop();
            }else if(tokens[i]=='+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/'){
                while (!ops.isEmpty() && hasPrecedence(tokens[i], ops.peek())){
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.push(tokens[i]);
            }
        }

        while (!ops.isEmpty()){
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
        }

        return values.pop();

    }

    public static int applyOp(char op, int b, int a){
        switch (op){
            case '+' : return  a + b;
            case '-' : return  a - b;
            case '*' : return  a * b;
            case '/' : if(b==0) throw new ArithmeticException();
                        else return a/b;
        }
        return 0;
    }

    public static boolean hasPrecedence(char op1, char op2){
        if(op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }

    public static void main(String[] args) {
        System.out.println(evaluate("1+2*(4-3)"));
    }

}
