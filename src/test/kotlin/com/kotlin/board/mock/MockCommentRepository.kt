package com.kotlin.board.mock

import com.kotlin.board.comment.Comment
import com.kotlin.board.comment.repository.CommentRepository
import com.kotlin.board.post.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.lang.IllegalArgumentException
import java.lang.Integer.min
import java.util.Collections
import java.util.concurrent.atomic.AtomicLong

class MockCommentRepository : CommentRepository {
    private var generatedId:AtomicLong= AtomicLong(0)
    private val comments:MutableList<Comment> =Collections.synchronizedList(mutableListOf<Comment>())
    fun testSave(comment: Comment): Comment {
        if(comment.id==null) {
            val newComment = Comment(
                comment.content,
                comment.writerId,
                generatedId.incrementAndGet()
            )
            comments.add(newComment)
            return newComment

        }else{
            comments.removeIf { it.id == comment.id }
            comments.add(comment)
            return comment
        }
    }



    override fun findById(id: Long): Comment? {
        return comments.find { it.id == id }

    }

}