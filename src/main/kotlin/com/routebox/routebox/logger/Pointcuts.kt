package com.routebox.routebox.logger

import org.aspectj.lang.annotation.Pointcut

class Pointcuts {
    @Pointcut("execution(* com.routebox.routebox.controller..*Controller.*(..))")
    fun controllerPoint() {
    }

    @Pointcut("execution(* com.routebox.routebox.application..*UseCase.*(..))")
    fun useCasePoint() {
    }

    @Pointcut("execution(* com.routebox.routebox.domain..*Service.*(..))")
    fun domainServicePoint() {
    }

    @Pointcut("execution(* com.routebox.routebox.infrastructure..*Repository.*(..))")
    fun repositoryPoint() {
    }
}
