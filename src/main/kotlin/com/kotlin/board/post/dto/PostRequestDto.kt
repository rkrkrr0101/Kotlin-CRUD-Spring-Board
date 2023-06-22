package com.kotlin.board.post.dto

import com.kotlin.board.post.Post

data class PostRequestDto(
        val title:String,
        val content:String,
        val writerId:String="",
        val viewCount:Long=0) {

    companion object {
        fun domainToDto(post: Post): PostRequestDto {
            return PostRequestDto(post.title, post.content, post.writerId, post.viewCount)
        }
    }

    fun dtoToDomain():Post{

        return Post(title=title, content = content, writerId = writerId, viewCount = viewCount)
    }
}