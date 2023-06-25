package com.kotlin.board.post.dto

import com.kotlin.board.comment.Comment
import com.kotlin.board.comment.dto.CommentUpdateDto
import com.kotlin.board.post.Post
import jakarta.validation.constraints.Size

class PostUpdateDto(
    @field:Size(min=1,max=72,message = "게시물의 이름길이를 확인해주세요")
    val title:String,
    @field:Size(min=1,max=2000,message = "게시물의 내용길이를 확인해주세요")
    val content:String) {
    companion object {
        fun domainToDto(post: Post): PostUpdateDto {
            return PostUpdateDto(post.title,post.content)
        }
    }
}