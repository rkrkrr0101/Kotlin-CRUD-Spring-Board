package com.kotlin.board.post

import com.kotlin.board.comment.Comment
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class PostTest {

    @Test
    fun 게시물은_자신의_조회수를_증가시킬수있다() {
        //g
        val post = Post("title", "content", "writerId")
        //w
        post.plusViewCount()
        post.plusViewCount()
        post.plusViewCount()
        //t
        assertThat(post.viewCount).isEqualTo(3)

    }

    @Test
    fun 게시물은_자신의_댓글을_추가할수있다() {
        //g
        val post = Post("title", "content", "writerId")
        val comment = Comment("commentContent", "commentWriterId")
        //w
        post.addComment(comment)
        //t
        assertThat(post.comments[0]).isEqualTo(comment)
    }

    @Test
    fun 게시물은_자신의_제목과_내용을_수정할수있다() {
        //g
        val post = Post("title", "content", "writerId")
        //w
        post.updatePost("changeTitle","changeContent")
        //t
        assertThat(post.title).isEqualTo("changeTitle")
        assertThat(post.content).isEqualTo("changeContent")

    }
}