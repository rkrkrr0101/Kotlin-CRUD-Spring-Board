package com.kotlin.board.comment.dto

import com.kotlin.board.comment.Comment
import jakarta.validation.constraints.Size

data class CommentRequestDto(
    @field:Size(min=1,max=300,message = "댓글의 내용길이를 확인해주세요")
    val content:String,
    @field:Size(min=1,max=30,message = "댓글의 글쓴이이름길이를 확인해주세요")
    val writerId:String){

    fun dtoToDomain():Comment{
        return Comment(content, writerId)
    }
    companion object {
        fun domainToDto(comment: Comment): CommentRequestDto {
            return CommentRequestDto(comment.content, comment.writerId)
        }
    }
}
