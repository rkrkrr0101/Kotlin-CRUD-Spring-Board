package com.kotlin.board.post.service

import com.kotlin.board.comment.Comment
import com.kotlin.board.mock.MockPostRepository
import com.kotlin.board.post.Post
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach

class PostMockRepositoryTest {

    lateinit var postRepository:MockPostRepository
    @BeforeEach
    fun init(){
        postRepository= MockPostRepository()
        val post1=Post("1title","1content","1writerid")
        val post2=Post("2title","2content","2writerid")
        val post3=Post("3title","3content","3writerid")
        postRepository.save(post1)
        postRepository.save(post2)
        postRepository.save(post3)
    }

    @Test
    fun findId() {
        //g
        val id1:Long=1
        val id2:Long=2
        val id3:Long=3
        val wrongId:Long=400
        //w
        val findPost1 = postRepository.findById(id1)?:throw Exception()
        val findPost2 = postRepository.findById(id2)?:throw Exception()
        val findPost3 = postRepository.findById(id3)?:throw Exception()
        val wrongPost = postRepository.findById(wrongId)
        //t
        assertThat(findPost1.id).isEqualTo(1)
        assertThat(findPost2.id).isEqualTo(2)
        assertThat(findPost3.id).isEqualTo(3)
        assertThat(wrongPost).isEqualTo(null)



    }
    @Test
    fun save() {
        //g
        val post4=Post("4title","4content","4writerid")
        val post5=Post("5title","5content","5writerid")
        //w
        postRepository.save(post4)
        postRepository.save(post5)
        val findPost1 = postRepository.findById(4)?:throw Exception()
        val findPost2 = postRepository.findById(5)?:throw Exception()
        //t
        assertThat(findPost1.id).isEqualTo(4)
        assertThat(findPost2.id).isEqualTo(5)

    }

    @Test
    fun update() {
        //g
        val findPost = postRepository.findById(2)?:throw Exception()
        //w
        findPost.update("changeTitle","changeContent")
        val changePost =postRepository.findById(2)?:throw Exception()
        //t
        assertThat(changePost.title).isEqualTo("changeTitle")
        assertThat(changePost.content).isEqualTo("changeContent")
    }

    @Test
    fun delete() {
        val findPost = postRepository.findById(2)?:throw Exception()

        postRepository.delete(findPost)
        val resultPost = postRepository.findById(2)

        assertThat(resultPost).isNull()
    }

    @Test
    fun addComment() {
        //g
        val findPost = postRepository.findById(2)?:throw Exception()
        val comment1=Comment("commentContent1","commentWriterId1")
        val comment2=Comment("commentContent1","commentWriterId1")
        //w
        findPost.addComment(comment1)
        findPost.addComment(comment2)
        val resultPost = postRepository.findById(2)?:throw Exception()
        //t
        assertThat(resultPost.comments.size).isEqualTo(2)
    }

    @Test
    fun findPostsPaging() {
    }

    @Test
    fun findPostsTitlePaging() {
    }



    @Test
    fun plusViewCount() {
    }
}