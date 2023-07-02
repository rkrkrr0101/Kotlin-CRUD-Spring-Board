package com.kotlin.board.post.repository

import com.kotlin.board.post.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class PostRepositoryImpl(val postJpaRepository: PostJpaRepository):PostRepository {
    override fun save(post: Post): Post {
        return postJpaRepository.save(post)
    }

    override fun delete(post: Post): Long {
        val id = post.id
        postJpaRepository.delete(post)
        return id
    }

    override fun findById(id: Long): Post? {
        return postJpaRepository.findByIdOrNull(id)
    }


    override fun findAll(pageable: Pageable): Page<Post> {
        return postJpaRepository.findAll(pageable)
    }

    override fun findByTitleContaining(title: String, pageable: Pageable): Page<Post> {
        return postJpaRepository.findByTitleContaining(title, pageable)
    }
}