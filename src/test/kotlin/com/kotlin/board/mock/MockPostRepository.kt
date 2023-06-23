package com.kotlin.board.mock

import com.kotlin.board.post.Post
import com.kotlin.board.post.repository.PostRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class MockPostRepository : PostRepository {
    override fun save(post: Post): Post {
        TODO("Not yet implemented")
    }

    override fun delete(post: Post): Long? {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): Post? {
        TODO("Not yet implemented")
    }

    override fun findAll(pageable: Pageable): Page<Post> {
        TODO("Not yet implemented")
    }

    override fun findByTitleContaining(title: String, pageable: Pageable): Page<Post> {
        TODO("Not yet implemented")
    }
}