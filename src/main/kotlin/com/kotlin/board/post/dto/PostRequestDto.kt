package com.kotlin.board.post.dto

import com.kotlin.board.post.Post
import jakarta.validation.constraints.Size
import org.jetbrains.annotations.NotNull

data class PostRequestDto(
        @field:Size(min=1,max=72, message = "게시물의 제목길이를 확인해주세요")
        val title:String,
        @field:Size(min=1,max=2000,message = "게시물의 내용길이를 확인해주세요")
        val content:String,
        @field:Size(min=1,max=30,message = "글쓴이의 이름길이를 확인해주세요")
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