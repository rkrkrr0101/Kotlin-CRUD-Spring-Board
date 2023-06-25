package com.kotlin.board.comment.dto

import com.kotlin.board.comment.Comment
import jakarta.validation.constraints.Size

data class CommentUpdateDto(
    @field:Size(min=1,max=300,message = "댓글의 내용길이를 확인해주세요")
    val content:String){

    companion object {
        fun domainToDto(comment: Comment): CommentUpdateDto {
            return CommentUpdateDto(comment.content)
        }
    }
}
