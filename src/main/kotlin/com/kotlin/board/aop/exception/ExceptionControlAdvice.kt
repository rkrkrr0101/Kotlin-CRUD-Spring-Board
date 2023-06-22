package com.kotlin.board.aop.exception

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionControlAdvice {
    @ExceptionHandler(IllegalArgumentException::class)
    fun illegalArgumentHandler(e:Exception):String{ return "Illegal id"}
    @ExceptionHandler(IllegalStateException::class)
    fun illegalStateHandler(e:Exception):String{
        e.printStackTrace()
        return "Illegal state"
    }
}