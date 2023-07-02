package com.kotlin.board.comment.dto

import com.kotlin.board.comment.Comment

data class CommentResponseDto(val id:Long,val content:String,val writerId:String){
    fun dtoToDomain(): Comment {
        return Comment(content, writerId)
    }
    companion object {
        fun domainToDto(comment: Comment): CommentResponseDto {
            return CommentResponseDto(comment.id,comment.content, comment.writerId)
        }
    }
}
