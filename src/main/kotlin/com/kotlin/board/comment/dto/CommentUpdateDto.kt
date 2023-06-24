package com.kotlin.board.comment.dto

import com.kotlin.board.comment.Comment

data class CommentUpdateDto(val content:String){

    companion object {
        fun domainToDto(comment: Comment): CommentUpdateDto {
            return CommentUpdateDto(comment.content)
        }
    }
}
