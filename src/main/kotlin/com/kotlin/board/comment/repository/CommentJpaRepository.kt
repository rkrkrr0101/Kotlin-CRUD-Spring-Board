package com.kotlin.board.comment.repository

import com.kotlin.board.comment.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentJpaRepository:JpaRepository<Comment,Long> {
}