package com.kotlin.board.aop.exception

import com.kotlin.board.common.domain.exception.ResourceNotFoundException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionControlAdvice {
    @ExceptionHandler(ResourceNotFoundException::class)
    fun illegalArgumentHandler(e:Exception):String?{ return e.message}
    @ExceptionHandler(IllegalStateException::class)
    fun illegalStateHandler(e:Exception):String{
        e.printStackTrace()
        return "Illegal state"
    }
}