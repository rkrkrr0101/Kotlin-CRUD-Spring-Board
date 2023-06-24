package com.kotlin.board.comment.repository

import com.kotlin.board.comment.Comment

interface CommentRepository {
    fun findById(id:Long):Comment?;
}