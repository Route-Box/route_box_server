package com.routebox.routebox.logger

import org.aspectj.lang.annotation.Pointcut

class Pointcuts {
    @Pointcut("execution(* com.routebox.routebox.*..controller..*(..))")
    fun controllerPoint() {
    }

    @Pointcut("execution(* com.routebox.routebox.*..service..*(..))")
    fun servicePoint() {
    }

    @Pointcut("execution(* com.routebox.routebox.*..infrastructure..*(..))")
    fun infrastructurePoint() {
    }
}
