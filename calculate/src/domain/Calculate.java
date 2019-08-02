package domain;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Calculate {


    private void check(String expression) throws Exception {
        //检查括号是否成对出现
        int beginCount = 0;
        int length = expression.length();
        for(int i = 0; i<length; i++){
            if(expression.charAt(i) == '('){
                beginCount++;
            }else if(expression.charAt(i) == ')'){
                if(beginCount<=0){
                    throw new Exception("error:括号不匹配");
                }else{
                    beginCount--;
                }
            }
        }
        if(beginCount != 0){
            throw new Exception("error:括号不匹配");
        }
        //检查运算符是否合法
        char str[] = expression.toCharArray();
        for(int i = 0; i<length; i++){
            if(str[i]=='+'||str[i]=='X'||str[i]=='/'){
                int j = i-1;
                while(str[j]=='('||str[j]==')'){
                    if(j==0){
                        throw new Exception("error:运算符不合法");
                    }
                    j--;
                }
                if(str[j]=='-'||str[j]=='+'||str[j]=='X'||str[j]=='/'){
                    throw new Exception("error:运算符不合法");
                }
            }
            if(str[i]=='.'){
                if(i==0||i==str.length-1){
                    throw new Exception("error:小数点不合法");
                }
                char c1 = str[i-1];
                char c2 = str[i+1];
                if(c1=='-'||c1=='+'||c1=='X'||c1=='/'||c1=='('||c1==')'||c1=='.'){
                    throw new Exception("error:小数点不合法");
                }
                if(c2=='-'||c2=='+'||c2=='X'||c2=='/'||c2=='('||c2==')'||c2=='.'){
                    throw new Exception("error:小数点不合法");
                }
            }
        }

    }

    private String subtractSet(String expression) throws Exception{
        StringBuilder str = new StringBuilder(expression);
        for(int i = 0; i<str.length(); i++){
            if(str.charAt(i)=='('){
                int j = str.lastIndexOf(")");
                String result =calculate(str.substring(i+1,j));
                str.delete(i,j+1);
                str.insert(i,result);
                break;
            }
        }

        for(int i = 0; i<str.length(); i++){
            if(str.charAt(i)=='-'){
                int j = i-1;
                if(str.charAt(j)!='+'&&str.charAt(j)!='X'&&str.charAt(j)!='/'){
                    str.insert(i,'+');
                }else if(str.charAt(j)=='-'){
                    str.delete(j,i);
                }
            }
        }
        return str.toString();
    }

    public String calculate(String expression) throws Exception {
        Stack<String> numberStack = new Stack<>();
        Stack<String> opStack = new Stack<>();
        Map<String,Integer> map = new HashMap<>();
        map.put("+",0);
        map.put("X",1);
        map.put("/",1);
        map.put("#",-1);
        this.check(expression);
        expression = this.subtractSet(expression);
        opStack.push("#");
        char[] chars = expression.toCharArray();
        for (int i = 0; i<chars.length;i++){
            switch (chars[i]){
                case '+':
                case 'X':
                case '/':
                    if(map.get(chars[i]+"")>map.get(opStack.peek())){
                        opStack.push(chars[i]+"");
                    }else{
                        while (!(map.get(chars[i]+"")>map.get(opStack.peek()))){
                            numberStack.push(opStack.pop());
                        }
                        opStack.push(chars[i]+"");
                    }
                    break;
                default:
                    StringBuilder sb = new StringBuilder();
                    while(chars[i]!='+'&&chars[i]!='X'&&chars[i]!='/'){
                        sb.append(chars[i]);
                        i++;
                        if(i==chars.length){
                            break;
                        }
                    }
                    numberStack.push(sb.toString());
                    i--;
            }
        }
        while(!opStack.peek().equals("#")){
            numberStack.push(opStack.pop());
        }
        Stack<String> stack = new Stack<>();
        while(!numberStack.empty()){
            stack.push(numberStack.pop());
        }
        Stack<String> tmp = new Stack<>();
        while (!stack.empty()){
            String s = stack.pop();
            if(!(s.equals("+")||s.equals("X")||s.equals("/"))){
                tmp.push(s);
            }else{
                String a = tmp.pop();
                BigDecimal ad = new BigDecimal(a);
                String b = tmp.pop();
                BigDecimal bd = new BigDecimal(b);
                switch (s){
                    case "+":
                        tmp.push(bd.add(ad).toString());
                        break;
                    case "X":
                        tmp.push(bd.multiply(ad).toString());
                        break;
                    case "/":
                        if(ad.equals(new BigDecimal("0"))){
                            throw new Exception("error:除数为0");
                        }
                        if(!bd.equals(new BigDecimal("0"))) {
                            tmp.push(bd.divide(ad, 12, BigDecimal.ROUND_HALF_UP).toString());
                        }else{
                            tmp.push("0");
                        }
                        break;
                }
            }
        }

        return tmp.pop();


    }
}
