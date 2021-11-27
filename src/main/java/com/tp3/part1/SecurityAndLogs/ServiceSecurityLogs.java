package com.tp3.part1.SecurityAndLogs;

import com.tp3.part1.bank.Bank;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import java.util.Map;

@Aspect
@Component
public class ServiceSecurityLogs {

    @Before("execution(* com.tp3.part1.client.*.*(..)) " +
            "|| execution(* com.tp3.part1.store.*.*(..)) " +
            "|| execution(* com.tp3.part1.provider.*.*(..))" +
            "|| execution(* com.tp3.part1.bank.*.*(..))")
    public void log(JoinPoint joinPoint) {
        System.out.println("\n----METHOD CALL DETAILS----");
        System.out.println(">From : " + joinPoint.getTarget().getClass().getName());
        System.out.print(">Method : " + joinPoint.getSignature().getName() + "(");
        int i=1;
        for(Object o : joinPoint.getArgs()) {
            System.out.print(o.toString());
            if (i<joinPoint.getArgs().length){
                System.out.print(", ");
            }
            i++;
        }
        System.out.print(")\n");
        System.out.println("----END LOG DETAILS----\n");
    }


    @Around("execution(* com.tp3.part1.bank.Bank.transfer(..))")
    public boolean verify(ProceedingJoinPoint joinPoint) {
        Map<Integer, Integer> accounts = ((Bank)joinPoint.getTarget()).bankAccounts;
        Object[] args = joinPoint.getArgs();
        //senderId, receiverId, amount
        if(accounts.get((Integer)args[0]) < (Integer)args[2]) {
            return false;
        }
        else {
            try {
                joinPoint.proceed();
                return true;
            } catch (Throwable e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
        }


    }

}
