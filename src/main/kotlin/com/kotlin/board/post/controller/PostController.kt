package com.kotlin.board.post.controller

import com.kotlin.board.comment.dto.CommentRequestDto
import com.kotlin.board.post.dto.PostRequestDto
import com.kotlin.board.post.dto.PostResponseDto
import com.kotlin.board.post.service.PostService
import com.kotlin.board.common.Constant
import com.kotlin.board.common.Result
import com.kotlin.board.common.domain.exception.ResourceNotFoundException
import com.kotlin.board.post.dto.PostUpdateDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/post")
class PostController(val postService: PostService) {


    @GetMapping("/{id}")
    fun findId(@PathVariable("id") id:Long):Result<PostResponseDto>{
        val post = postService.findId(id)?:throw ResourceNotFoundException("post",id)
        return Result(PostResponseDto.domainToDto(post))

    }
    @PostMapping("/save")
    fun save(@Validated @RequestBody requestDto: PostRequestDto,
             bindingResult: BindingResult):Result<Any?>{
        if(bindingResult.hasErrors()){
            return Result(bindingResult.fieldError)
        }
        return Result(postService.save(requestDto))
    }
    @PostMapping("/comment/{id}")
    fun commentSave(@PathVariable("id") postId: Long,
                    @Validated @RequestBody commentRequestDto: CommentRequestDto,
                    bindingResult: BindingResult):Result<Any?>{
        if(bindingResult.hasErrors()){
            return Result(bindingResult.fieldError)
        }
        return Result(postService.addComment(postId,commentRequestDto))
    }
    @DeleteMapping("/comment/{id}")
    fun commentDelete(@PathVariable("id") postId: Long,
                    @RequestParam commentId: Long):Result<Any?>{

        return Result(postService.deleteComment(postId,commentId))
    }
    @PostMapping("/viewcount/plus/{id}")
    fun plusViewCount(@PathVariable id: Long):Result<Long?>{
        return Result(postService.plusViewCount(id))
    }
    @PatchMapping("/{id}")
    fun update(@PathVariable id:Long,@Validated @RequestBody requestDto: PostUpdateDto,
               bindingResult: BindingResult):Result<Any?>{
        if(bindingResult.hasErrors()){
            return com.kotlin.board.common.Result(bindingResult.fieldError)
        }
        return Result(postService.update(id,requestDto.title,requestDto.content))
    }
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long):Result<Long?>{
        return Result(postService.delete(id))
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