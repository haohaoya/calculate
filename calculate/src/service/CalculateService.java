package service;

import domain.Calculate;
import util.MySpring;


public class CalculateService {
     private Calculate calculate = MySpring.getBean("domain.Calculate");
     public String getResult(String expression) throws Exception{
         return calculate.calculate(expression);
     }

}
