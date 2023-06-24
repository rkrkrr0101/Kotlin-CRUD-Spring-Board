package com.kotlin.board.comment

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class CommentTest {

    @Test
    fun 댓글은_자기자신의_내용을_변경할수있다() {
        val comment=Comment("testContent","testWriterId")
        val changeContent="changeContent"
        comment.update(changeContent)

        Assertions.assertThat(comment.content).isEqualTo(changeContent)
    }
}