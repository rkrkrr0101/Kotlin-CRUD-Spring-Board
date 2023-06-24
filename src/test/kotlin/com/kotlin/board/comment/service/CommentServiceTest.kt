package com.kotlin.board.comment.service

import com.kotlin.board.comment.Comment
import com.kotlin.board.common.domain.exception.ResourceNotFoundException
import com.kotlin.board.mock.MockCommentRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows

class CommentServiceTest {
    lateinit var commentRepository: MockCommentRepository
    lateinit var commentService:CommentService
    @BeforeEach
    fun init(){
        commentRepository= MockCommentRepository()
        commentService= CommentService(commentRepository)
        for(i in 1..10){
            commentRepository.testSave(Comment("${i}content","${i}writerId"))
        }
    }

    @Test
    fun 올바른_id를_넣으면_댓글을_수정할수있다() {
        val changeContent="changeContent"
        commentService.update(2, changeContent)
        val comment = commentRepository.findById(2) ?: throw Exception()
        assertThat(comment.content).isEqualTo(changeContent)
    }
    @Test
    fun 잘못된_id를_넣으면_댓글을_수정할수없고_예외가_발생한다() {
        val changeContent="changeContent"
        val wrongId:Long=300

        assertThrows<ResourceNotFoundException> {commentService.update(wrongId, changeContent)  }

    }
}