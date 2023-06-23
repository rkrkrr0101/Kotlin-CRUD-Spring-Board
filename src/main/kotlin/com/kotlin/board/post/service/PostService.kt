package com.kotlin.board.post.service

import com.kotlin.board.comment.dto.CommentRequestDto
import com.kotlin.board.common.domain.exception.ResourceNotFoundException
import com.kotlin.board.post.Post
import com.kotlin.board.post.dto.PostRequestDto
import com.kotlin.board.post.dto.PostResponseDto
import com.kotlin.board.post.repository.PostRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(val postRepository: PostRepository) {
    @Transactional
    fun save(postRequestDto: PostRequestDto):Long?{
        val entity = postRepository.save(postRequestDto.dtoToDomain())
        return entity.id
    }
    @Transactional
    fun update(postId: Long,title:String,content:String):Long{
        val post = presentPost(postId)
        post.updatePost(title, content)
        return postId
    }
    @Transactional
    fun delete(postId: Long):Long{
        val post = presentPost(postId)
        postRepository.delete(post)
        return postId
    }
    fun findId(postId: Long):Post?{
        return postRepository.findById(postId)
    }
    fun findPostsPaging(pageable: Pageable):Page<PostResponseDto>{
        val posts = postRepository.findAll(pageable)
        return posts.map { PostResponseDto.domainToDto(it)}
    }
    fun findPostsTitlePaging(title: String,pageable: Pageable):Page<PostResponseDto>{
        val posts = postRepository.findByTitleContaining(title, pageable)
        return posts.map { PostResponseDto.domainToDto(it) }
    }


    @Transactional
    fun addComment(postId:Long,requestDto:CommentRequestDto):Long{
        val post:Post = presentPost(postId)
        val comment = requestDto.dtoToDomain()
        post.addComment(comment)
        return postId
    }
    @Transactional
    fun plusViewCount(postId: Long):Long{
        val post = presentPost(postId)
        return post.plusViewCount()
    }

    private fun presentPost(postId: Long) =
        postRepository.findById(postId) ?: throw ResourceNotFoundException("post",postId)

}