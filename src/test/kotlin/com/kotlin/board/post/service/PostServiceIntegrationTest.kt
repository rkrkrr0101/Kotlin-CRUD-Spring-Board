package com.kotlin.board.post.service

import com.kotlin.board.comment.Comment
import com.kotlin.board.comment.dto.CommentRequestDto
import com.kotlin.board.common.Constant
import com.kotlin.board.common.domain.exception.ResourceNotFoundException
import com.kotlin.board.post.Post
import com.kotlin.board.post.dto.PostRequestDto
import com.kotlin.board.post.repository.PostRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class PostServiceIntegrationTest(
    @Autowired
    var postRepository: PostRepository,
    @Autowired
    val postService:PostService) {
    val WRONGID=90000L
    @BeforeEach
    fun init(){

        for(i in 1..10){
            postRepository.save(Post("${i}title","${i}content","${i}writerid"))

        }


    }
    @Test
    fun 올바른_id를_입력하면_게시글을_단건검색할수있다() {
        val postId1 = postRepository.save(Post("1title", "1content", "1writerId")).id
        val postId2 =postRepository.save(Post("2title","2content","2writerId")).id


        val resultPost1 = postService.findId(postId1)?:throw Exception()
        val resultPost2 = postService.findId(postId2)?:throw Exception()

        Assertions.assertThat(resultPost1.title).isEqualTo("1title")
        Assertions.assertThat(resultPost2.title).isEqualTo("2title")

    }
    @Test
    fun 없는_id를_입력하면_null이_반환된다(){
        val wrongPost = postService.findId(WRONGID)
        Assertions.assertThat(wrongPost).isNull()
    }
    @Test
    fun dto를_넣어서_저장을_할수있다() {
        //g
        val requestDto4= PostRequestDto("savetitle1","savecontent1","savewriterid1")
        val requestDto5= PostRequestDto("savetitle2","savecontent2","savewriterid2")
        //w
        val resultId1 = postService.save(requestDto4)?:throw Exception()
        val resultId2 = postService.save(requestDto5)?:throw Exception()

        val resultPost1 = postService.findId(resultId1)?:throw Exception()
        val resultPost2 = postService.findId(resultId2)?:throw Exception()
        //t

        Assertions.assertThat(resultPost1.title).isEqualTo("savetitle1")
        Assertions.assertThat(resultPost2.title).isEqualTo("savetitle2")

    }

    @Test
    fun 올바른_id를_넣어서_게시물을_업데이트할수있다() {
        val postId1 = postRepository.save(Post("1title", "1content", "1writerId")).id
        val postId2 =postRepository.save(Post("2title","2content","2writerId")).id
        //w
        postService.update(postId2,"changeTitle","changeContent")
        val resultPost = postService.findId(postId2)?:throw Exception()
        //t
        Assertions.assertThat(resultPost.title).isEqualTo("changeTitle")
    }
    @Test
    fun 잘못된_id를_넣어서_업데이트를_하면_예외가_발생한다() {
        //w
        assertThrows<ResourceNotFoundException> {
            postService.update(
                WRONGID,"changeTitle","changeContent")
        }

    }
    @Test
    fun 올바른_id를_넣어서_게시물을_삭제할수있다() {
        val postId1 = postRepository.save(Post("1title", "1content", "1writerId")).id
        val postId2 =postRepository.save(Post("2title","2content","2writerId")).id
        //w
        postService.delete(postId2)
        val resultPost = postService.findId(postId2)
        //t
        Assertions.assertThat(resultPost).isNull()
    }
    @Test
    fun 잘못된_id를_넣어서_삭제를_하면_예외가_발생한다() {
        //w
        assertThrows<ResourceNotFoundException> { postService.delete(WRONGID) }

    }
    @Test
    fun 올바른_게시물id를_넣어서_게시물에_댓글을_추가할수있다() {
        //g
        val postId1 = postRepository.save(Post("1title", "1content", "1writerId")).id
        val postId2 =postRepository.save(Post("2title","2content","2writerId")).id

        val comment1= Comment("commentContent1","commentWriterId1")
        val comment2= Comment("commentContent1","commentWriterId1")
        //w
        postService.addComment(postId2, CommentRequestDto.domainToDto(comment1))
        postService.addComment(postId2, CommentRequestDto.domainToDto(comment2))

        val resultPost = postRepository.findById(postId2)?:throw Exception()
        //t
        Assertions.assertThat(resultPost.comments.size).isEqualTo(2)
    }
    @Test
    fun 잘못된_게시물id를_넣으면_댓글을_추가하지않고_예외가_난다() {
        val comment1= Comment("commentContent1","commentWriterId1")
        assertThrows<ResourceNotFoundException> {
            postService.addComment(WRONGID, CommentRequestDto.domainToDto(comment1))
        }
    }
    @Test
    fun 올바른_게시물id와_댓글id를_넣어서_게시물에_댓글을_삭제할수있다() {
        //g
        val postId1 = postRepository.save(Post("1title", "1content", "1writerId")).id
        val postId2 =postRepository.save(Post("2title","2content","2writerId")).id

        val comment1= Comment("commentContent1","commentWriterId1")
        val comment2= Comment("commentContent2","commentWriterId2")

        val commentId1 = postService.addComment(postId2, CommentRequestDto.domainToDto(comment1))
        val commentId2 =postService.addComment(postId2, CommentRequestDto.domainToDto(comment2))
        //w
        postService.deleteComment(postId2,commentId2)

        val resultPost = postRepository.findById(postId2)?:throw Exception()
        //t
        Assertions.assertThat(resultPost.comments.size).isEqualTo(1)
    }
    @Test
    fun 올바른_게시물id와_잘못된댓글id를_넣으면_게시물에_댓글을_삭제하지못하고_예외가_발생한다() {
        //g
        val postId1 = postRepository.save(Post("1title", "1content", "1writerId")).id
        val postId2 =postRepository.save(Post("2title","2content","2writerId")).id

        val comment1= Comment("commentContent1","commentWriterId1")
        val comment2= Comment("commentContent2","commentWriterId2")

        val commentId1 = postService.addComment(postId2, CommentRequestDto.domainToDto(comment1))
        val commentId2 =postService.addComment(postId2, CommentRequestDto.domainToDto(comment2))

        val resultPost = postRepository.findById(postId2)?:throw Exception()
        //w,t
        assertThrows<ResourceNotFoundException> {
            postService.deleteComment(postId2,WRONGID)
        }
        Assertions.assertThat(resultPost.comments.size).isEqualTo(2)
    }
    @Test
    fun 잘못된_게시물id와_올바른댓글id를_넣으면_게시물에_댓글을_삭제하지못하고_예외가_발생한다() {
        //g
        val postId1 = postRepository.save(Post("1title", "1content", "1writerId")).id
        val postId2 =postRepository.save(Post("2title","2content","2writerId")).id

        val comment1= Comment("commentContent1","commentWriterId1")
        val comment2= Comment("commentContent2","commentWriterId2")

        val commentId1 = postService.addComment(postId2, CommentRequestDto.domainToDto(comment1))
        val commentId2 =postService.addComment(postId2, CommentRequestDto.domainToDto(comment2))

        val resultPost = postRepository.findById(postId2)?:throw Exception()
        //w,t
        assertThrows<ResourceNotFoundException> {
            postService.deleteComment(WRONGID,comment2.id)
        }
        Assertions.assertThat(resultPost.comments.size).isEqualTo(2)
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



        Assertions.assertThat(resultPosts1.numberOfElements).isEqualTo(3)
        Assertions.assertThat(resultPosts2.numberOfElements).isEqualTo(3)
        Assertions.assertThat(lastPosts.numberOfElements).isEqualTo(1)

    }
    @Test
    fun 게시물의_총갯수보다_더높은페이지를_요청하면_빈배열을_반환한다() {
        val pageSize=3
        val outerPageRequest = PageRequest.of(
            300, pageSize,
            Sort.by(Constant.POST_UPDATE_TIME).descending()
        )

        val outerPosts = postService.findPostsPaging(outerPageRequest)



        Assertions.assertThat(outerPosts.numberOfElements).isEqualTo(0)

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


        Assertions.assertThat(resultPosts1.numberOfElements).isEqualTo(1)
        Assertions.assertThat(maxResultPosts2.numberOfElements).isEqualTo(10)


    }
    @Test
    fun 게시글에_해당제목이_없으면_빈배열을_반환한다() {
        val pageSize=3
        val noSuchPageRequest = PageRequest.of(
            3, pageSize,
            Sort.by(Constant.POST_UPDATE_TIME).descending()
        )

        val noSuchPosts = postService.findPostsTitlePaging("noSuch",noSuchPageRequest)

        Assertions.assertThat(noSuchPosts.numberOfElements).isEqualTo(0)

    }
    @Test
    fun 게시물제목_검색결과의_총갯수보다_더높은페이지를_요청하면_빈배열을_반환한다() {
        val pageSize=3
        val outerPageRequest = PageRequest.of(
            30, pageSize,
            Sort.by(Constant.POST_UPDATE_TIME).descending()
        )

        val outerPosts = postService.findPostsTitlePaging("2t",outerPageRequest)

        Assertions.assertThat(outerPosts.numberOfElements).isEqualTo(0)
    }




    @Test
    fun 올바른_게시물id를_넣어서_조회수를_증가시킬수있다() {
        val postId1 = postRepository.save(Post("1title", "1content", "1writerId")).id
        val postId2 =postRepository.save(Post("2title","2content","2writerId")).id

        postService.plusViewCount(postId2)
        postService.plusViewCount(postId2)
        postService.plusViewCount(postId2)
        val resultPost = postRepository.findById(postId2)?:throw Exception()

        Assertions.assertThat(resultPost.viewCount).isEqualTo(3)

    }
    @Test
    fun 잘못된_게시물id를_넣으면_조회수가_증가하지않고_예외가_발생한다() {
        assertThrows<ResourceNotFoundException> { postService.plusViewCount(WRONGID) }
    }
}