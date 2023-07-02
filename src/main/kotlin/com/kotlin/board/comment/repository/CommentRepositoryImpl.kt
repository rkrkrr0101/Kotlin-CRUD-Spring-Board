package com.kotlin.board.comment.repository

import com.kotlin.board.comment.Comment
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class CommentRepositoryImpl(val commentRepository: CommentJpaRepository):CommentRepository {
    override fun findById(id: Long): Comment? {
        return commentRepository.findByIdOrNull(id)
    }

    override fun save(comment: Comment): Comment {
        return commentRepository.save(comment)
    }

    override fun delete(comment: Comment): Long {
        val id = comment.id
        commentRepository.delete(comment)
        return id
    }
}