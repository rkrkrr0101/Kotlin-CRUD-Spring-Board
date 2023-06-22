package com.kotlin.board.post.dto

import com.kotlin.board.comment.Comment
import com.kotlin.board.comment.dto.CommentResponseDto
import com.kotlin.board.post.Post
import java.time.LocalDateTime

data class PostResponseDto(
    val title:String,
    val content:String,
    val writerId:String,
    val viewCount:Long,
    val lastUpdateDate:LocalDateTime,
    val comments:List<CommentResponseDto>
){
    companion object {
        fun domainToDto(post: Post): PostResponseDto {

            return PostResponseDto(
                post.title,
                post.content,
                post.writerId,
                post.viewCount,
                post.lastUpdateDate,
                post.comments.map { CommentResponseDto.domainToDto(it) }
            )
        }
    }
}
