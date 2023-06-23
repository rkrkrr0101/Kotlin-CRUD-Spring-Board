package com.kotlin.board.post.service

import com.kotlin.board.comment.Comment
import com.kotlin.board.comment.dto.CommentRequestDto
import com.kotlin.board.common.Constant
import com.kotlin.board.mock.MockPostRepository
import com.kotlin.board.post.Post
import com.kotlin.board.post.dto.PostRequestDto
import org.assertj.core.api.Assertions.assertThat

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
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
    fun findId() {
        val resultPost1 = postService.findId(2)?:throw Exception()
        val resultPost2 = postService.findId(3)?:throw Exception()
        val wrongPost = postService.findId(400)

        assertThat(resultPost1.title).isEqualTo("2title")
        assertThat(resultPost2.title).isEqualTo("3title")
        assertThat(wrongPost).isNull()
    }
    @Test
    fun save() {
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
    fun update() {
        //w
        postService.update(2,"changeTitle","changeContent")
        val resultPost = postService.findId(2)?:throw Exception()
        //t
        assertThat(resultPost.title).isEqualTo("changeTitle")
    }

    @Test
    fun delete() {
        //w
        postService.delete(2)
        val resultPost = postService.findId(2)
        //t
        assertThat(resultPost).isNull()
    }
    @Test
    fun addComment() {
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
    fun findPostsPaging() {
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
        val outerPageRequest = PageRequest.of(
            30, pageSize,
            Sort.by(Constant.POST_UPDATE_TIME).descending()
        )
        val resultPosts1 = postService.findPostsPaging(pageRequest1)
        val resultPosts2 = postService.findPostsPaging(pageRequest2)
        val lastPosts = postService.findPostsPaging(lastPageRequest)
        val outerPosts = postService.findPostsPaging(outerPageRequest)


        assertThat(resultPosts1.size).isEqualTo(3)
        assertThat(resultPosts2.size).isEqualTo(3)
        assertThat(lastPosts.size).isEqualTo(1)
        assertThat(outerPosts.size).isEqualTo(0)
    }

    @Test
    fun findPostsTitlePaging() {
        val pageSize=3
        val pageRequest1 = PageRequest.of(
            0, pageSize,
            Sort.by(Constant.POST_UPDATE_TIME).descending()
        )
        val maxPageRequest = PageRequest.of(
            0, 20,
            Sort.by(Constant.POST_UPDATE_TIME).descending()
        )
        val noSuchPageRequest = PageRequest.of(
            3, pageSize,
            Sort.by(Constant.POST_UPDATE_TIME).descending()
        )
        val outerPageRequest = PageRequest.of(
            30, pageSize,
            Sort.by(Constant.POST_UPDATE_TIME).descending()
        )
        val resultPosts1 = postService.findPostsTitlePaging("2t",pageRequest1)
        val maxResultPosts2 = postService.findPostsTitlePaging("title",maxPageRequest)
        val noSuchPosts = postService.findPostsTitlePaging("noSuch",noSuchPageRequest)

        for (dto in resultPosts1) {
            println("dto.title = ${dto.title}")
        } 

        assertThat(resultPosts1.size).isEqualTo(1)
        assertThat(maxResultPosts2.size).isEqualTo(10)
        assertThat(noSuchPosts.size).isEqualTo(0)

    }



    @Test
    fun plusViewCount() {
        postService.plusViewCount(2)
        postService.plusViewCount(2)
        postService.plusViewCount(2)
        val resultPost = postRepository.findById(2)?:throw Exception()

        assertThat(resultPost.viewCount).isEqualTo(3)

    }
}