package com.kotlin.board.post.service

import com.kotlin.board.comment.dto.CommentRequestDto
import com.kotlin.board.comment.repository.CommentRepository
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
class PostService(val postRepository: PostRepository,val commentRepository: CommentRepository) {
    @Transactional
    fun save(postRequestDto: PostRequestDto):Long?{
        val entity = postRepository.save(postRequestDto.dtoToDomain())
        return entity.id
    }
    @Transactional
    fun update(postId: Long,title:String,content:String):Long{
        val post = presentPost(postId)
        post.update(title, content)
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
    fun addComment(postId:Long, commentRequestDto:CommentRequestDto):Long{
        val post:Post = presentPost(postId)
        val comment =commentRepository.save(commentRequestDto.dtoToDomain())
        post.addComment(comment)
        return comment.id
    }
    @Transactional
    fun deleteComment(postId: Long,commentId:Long):Long{
        val post:Post=presentPost(postId)
        val comment = presentComment(commentId)

        post.deleteComment(comment)
        commentRepository.delete(comment)


        return commentId
    }
    @Transactional
    fun plusViewCount(postId: Long):Long{
        val post = presentPost(postId)
        return post.plusViewCount()
    }

    private fun presentPost(postId: Long) =
        postRepository.findById(postId) ?: throw ResourceNotFoundException("post",postId)
    private fun presentComment(commentId: Long) =
        commentRepository.findById(commentId) ?: throw ResourceNotFoundException("comment",commentId)
}