package com.kotlin.board.post.controller

import com.kotlin.board.comment.dto.CommentRequestDto
import com.kotlin.board.post.dto.PostRequestDto
import com.kotlin.board.post.dto.PostResponseDto
import com.kotlin.board.post.service.PostService
import com.kotlin.board.common.Constant
import com.kotlin.board.common.Result
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.IllegalArgumentException

@RestController
@RequestMapping("/post")
class PostController(val postService: PostService) {
    @GetMapping("/{id}")
    fun findId(@PathVariable("id") id:Long):Result<PostResponseDto>{
        val post = postService.findId(id)?:throw IllegalArgumentException("잘못된 id값")
        return Result(PostResponseDto.domainToDto(post))

    }
    @PostMapping("/save")
    fun save(@RequestBody requestDto: PostRequestDto):Long?{
        return postService.save(requestDto)
    }
    @PostMapping("/add/comment/{id}")
    fun commentSave(@PathVariable("id") postId: Long,@RequestBody requestDto: CommentRequestDto):Long?{
        return postService.addComment(postId,requestDto)
    }
    @PostMapping("/plus/viewcount/{id}")
    fun plusViewCount(@PathVariable id: Long):Long?{
        return postService.plusViewCount(id)
    }
    @PatchMapping("/{id}")
    fun update(@PathVariable id:Long,@RequestBody requestDto: PostRequestDto):Long?{
        return postService.update(id,requestDto.title,requestDto.content)
    }
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long):Long?{
        return postService.delete(id)
    }
    @GetMapping("/posts")
    fun findAllPaging(page:Int): Result<Page<PostResponseDto>> {
        val pageRequest = PageRequest.of(
            page, Constant.POST_PAGE_SIZE,
            Sort.by(Constant.POST_UPDATE_TIME).descending()
        )
        return Result(postService.findPostsPaging(pageRequest))
    }
    @GetMapping("/posts/{title}")
    fun findAllContentPaging(page:Int,@PathVariable title:String):Result<Page<PostResponseDto>>{
        val pageRequest = PageRequest.of(
            page, Constant.POST_PAGE_SIZE,
            Sort.by(Constant.POST_UPDATE_TIME).descending()
        )
        return Result(postService.findPostsTitlePaging(title,pageRequest))
    }
}