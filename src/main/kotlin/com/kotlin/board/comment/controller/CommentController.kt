package com.kotlin.board.comment.controller

import com.kotlin.board.comment.dto.CommentUpdateDto
import com.kotlin.board.comment.service.CommentService
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/comment")
class CommentController(val commentService: CommentService) {
    @PatchMapping("/{id}")
    fun update(@PathVariable id:Long,@RequestBody updateDto: CommentUpdateDto):Long{
        commentService.update(id,updateDto.content)
        return id
    }
}