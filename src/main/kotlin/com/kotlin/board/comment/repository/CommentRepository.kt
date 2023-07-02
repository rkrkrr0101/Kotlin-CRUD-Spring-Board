package com.kotlin.board.comment.repository

import com.kotlin.board.comment.Comment
import com.kotlin.board.post.Post

interface CommentRepository {
    fun save(comment: Comment): Comment;
    fun delete(comment: Comment):Long;
    fun findById(id:Long):Comment?;

}