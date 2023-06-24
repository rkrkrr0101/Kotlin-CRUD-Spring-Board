package com.kotlin.board.post.service

import com.kotlin.board.comment.Comment
import com.kotlin.board.comment.dto.CommentRequestDto
import com.kotlin.board.common.Constant
import com.kotlin.board.common.domain.exception.ResourceNotFoundException
import com.kotlin.board.mock.MockPostRepository
import com.kotlin.board.post.Post
import com.kotlin.board.post.dto.PostRequestDto
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

class PostServiceTest {
    lateinit var postRepository: MockPostRepository
    lateinit var postService:PostService
    @BeforeEach
    fun init(){
        postRepository= MockPostRepository()
        postService= PostService(postRepository)
        for(i in 1..10){
            postRepository.save(Post("${i}title","${i}content","${i}writerid"))
        }
    }
    @Test
    fun 올바른_id를_입력하면_게시글을_단건검색할수있다() {
        val resultPost1 = postService.findId(2)?:throw Exception()
        val resultPost2 = postService.findId(3)?:throw Exception()

        assertThat(resultPost1.title).isEqualTo("2title")
        assertThat(resultPost2.title).isEqualTo("3title")

    }
    @Test
    fun 없는_id를_입력하면_null이_반환된다(){
        val wrongPost = postService.findId(400)
        assertThat(wrongPost).isNull()
    }
    @Test
    fun dto를_넣어서_저장을_할수있다() {
        //g
        val requestDto4=PostRequestDto("savetitle1","savecontent1","savewriterid1")
        val requestDto5=PostRequestDto("savetitle2","savecontent2","savewriterid2")
        //w
        val resultId1 = postService.save(requestDto4)?:throw Exception()
        val resultId2 = postService.save(requestDto5)?:throw Exception()

        val resultPost1 = postService.findId(resultId1)?:throw Exception()
        val resultPost2 = postService.findId(resultId2)?:throw Exception()
        //t
        assertThat(resultPost1.title).isEqualTo("savetitle1")
        assertThat(resultPost2.title).isEqualTo("savetitle2")

    }

    @Test
    fun 올바른_id를_넣어서_게시물을_업데이트할수있다() {
        //w
        postService.update(2,"changeTitle","changeContent")
        val resultPost = postService.findId(2)?:throw Exception()
        //t
        assertThat(resultPost.title).isEqualTo("changeTitle")
    }
    @Test
    fun 잘못된_id를_넣어서_업데이트를_하면_예외가_발생한다() {
        //w
        assertThrows<ResourceNotFoundException> {
            postService.update(
            2000,"changeTitle","changeContent")
        }

    }
    @Test
    fun 올바른_id를_넣어서_게시물을_삭제할수있다() {
        //w
        postService.delete(2)
        val resultPost = postService.findId(2)
        //t
        assertThat(resultPost).isNull()
    }
    @Test
    fun 잘못된_id를_넣어서_삭제를_하면_예외가_발생한다() {
        //w
        assertThrows<ResourceNotFoundException> { postService.delete(2000) }

    }
    @Test
    fun 올바른_게시물id를_넣어서_게시물에_댓글을_추가할수있다() {
        //g
        val comment1= Comment("commentContent1","commentWriterId1")
        val comment2= Comment("commentContent1","commentWriterId1")
        //w
        postService.addComment(2, CommentRequestDto.domainToDto(comment1))
        postService.addComment(2, CommentRequestDto.domainToDto(comment2))

        val resultPost = postRepository.findById(2)?:throw Exception()
        //t
        assertThat(resultPost.comments.size).isEqualTo(2)
    }
    @Test
    fun 잘못된_게시물id를_넣으면_댓글을_추가하지않고_예외가_난다() {
        val comment1= Comment("commentContent1","commentWriterId1")
    }


    @Test
    fun 게시물들을_페이징해서_가져올수있다() {
        val pageSize=3
        val pageRequest1 = PageRequest.of(
            0, pageSize,
            Sort.by(Constant.POST_UPDATE_TIME).descending()
        )
        val pageRequest2 = PageRequest.of(
            1, pageSize,
            Sort.by(Constant.POST_UPDATE_TIME).descending()
        )
        val lastPageRequest = PageRequest.of(
            3, pageSize,
            Sort.by(Constant.POST_UPDATE_TIME).descending()
        )

        val resultPosts1 = postService.findPostsPaging(pageRequest1)
        val resultPosts2 = postService.findPostsPaging(pageRequest2)
        val lastPosts = postService.findPostsPaging(lastPageRequest)



        assertThat(resultPosts1.size).isEqualTo(3)
        assertThat(resultPosts2.size).isEqualTo(3)
        assertThat(lastPosts.size).isEqualTo(1)

    }
    @Test
    fun 게시물의_총갯수보다_더높은페이지를_요청하면_빈배열을_반환한다() {
        val pageSize=3
        val outerPageRequest = PageRequest.of(
            300, pageSize,
            Sort.by(Constant.POST_UPDATE_TIME).descending()
        )

        val outerPosts = postService.findPostsPaging(outerPageRequest)

        assertThat(outerPosts.size).isEqualTo(0)

    }


    @Test
    fun 게시글의_제목으로_페이징조회를_할수있다() {
        val pageSize=3
        val pageRequest1 = PageRequest.of(
            0, pageSize,
            Sort.by(Constant.POST_UPDATE_TIME).descending()
        )
        val maxPageRequest = PageRequest.of(
            0, 20,
            Sort.by(Constant.POST_UPDATE_TIME).descending()
        )


        val resultPosts1 = postService.findPostsTitlePaging("2t",pageRequest1)
        val maxResultPosts2 = postService.findPostsTitlePaging("title",maxPageRequest)


        for (dto in resultPosts1) {
            println("dto.title = ${dto.title}")
        } 

        assertThat(resultPosts1.size).isEqualTo(1)
        assertThat(maxResultPosts2.size).isEqualTo(10)


    }
    @Test
    fun 해당제목이_없으면_빈배열을_반환한다() {
        val pageSize=3
        val noSuchPageRequest = PageRequest.of(
            3, pageSize,
            Sort.by(Constant.POST_UPDATE_TIME).descending()
        )

        val noSuchPosts = postService.findPostsTitlePaging("noSuch",noSuchPageRequest)

        assertThat(noSuchPosts.size).isEqualTo(0)

    }
    @Test
    fun 게시물제목_검색결과의_총갯수보다_더높은페이지를_요청하면_빈배열을_반환한다() {
        val pageSize=3
        val outerPageRequest = PageRequest.of(
            30, pageSize,
            Sort.by(Constant.POST_UPDATE_TIME).descending()
        )

        val outerPosts = postService.findPostsTitlePaging("2t",outerPageRequest)

        assertThat(outerPosts.size).isEqualTo(0)
    }




    @Test
    fun 올바른_게시물id를_넣어서_조회수를_증가시킬수있다() {
        postService.plusViewCount(2)
        postService.plusViewCount(2)
        postService.plusViewCount(2)
        val resultPost = postRepository.findById(2)?:throw Exception()

        assertThat(resultPost.viewCount).isEqualTo(3)

    }
    @Test
    fun 잘못된_게시물id를_넣으면_조회수가_증가하지않고_예외가_발생한다() {
        assertThrows<ResourceNotFoundException> { postService.plusViewCount(2000) }
    }

}