package com.kotlin.board.mock

import com.kotlin.board.comment.Comment
import com.kotlin.board.comment.repository.CommentRepository
import java.lang.IllegalArgumentException
import java.util.Collections
import java.util.concurrent.atomic.AtomicLong

class FakeCommentRepository : CommentRepository {
    private var generatedId:AtomicLong= AtomicLong(0)
    private val comments:MutableList<Comment> =Collections.synchronizedList(mutableListOf<Comment>())
    override fun delete(comment: Comment): Long {
        val removeIf = comments.removeIf { it.id == comment.id }
        if (removeIf==false){
            throw IllegalArgumentException()
        }
        return comment.id
    }


    override fun save(comment: Comment): Comment {
        if(comment.id==null || comment.id==0L) {
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