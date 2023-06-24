package com.kotlin.board.comment.service

import com.kotlin.board.comment.repository.CommentRepository
import com.kotlin.board.common.domain.exception.ResourceNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(val commentRepository: CommentRepository) {
    @Transactional
    fun update(id:Long,content:String):Long?{
        val comment = commentRepository.findById(id)?:throw ResourceNotFoundException("comment",id)
        comment.update(content)
        return id

    }
}