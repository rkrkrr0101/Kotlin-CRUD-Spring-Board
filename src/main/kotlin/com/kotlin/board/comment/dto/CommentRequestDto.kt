package com.kotlin.board.comment.dto

import com.kotlin.board.comment.Comment

data class CommentRequestDto(val content:String,val writerId:String){

    fun dtoToDomain():Comment{
        return Comment(content, writerId)
    }

    fun domainToDto(comment: Comment):CommentRequestDto{
        return CommentRequestDto(comment.content,comment.writerId)
    }
}
