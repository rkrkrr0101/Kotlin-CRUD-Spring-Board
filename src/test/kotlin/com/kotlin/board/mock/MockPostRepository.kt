package com.kotlin.board.mock

import com.kotlin.board.post.Post
import com.kotlin.board.post.repository.PostRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.lang.IllegalArgumentException
import java.lang.Integer.min
import java.util.Collections
import java.util.concurrent.atomic.AtomicLong

class MockPostRepository : PostRepository {
    private var generatedId:AtomicLong= AtomicLong(0)
    private val posts:MutableList<Post> =Collections.synchronizedList(mutableListOf<Post>())
    override fun save(post: Post): Post {
        if(post.id==null) {
            val newPost = Post(
                post.title,
                post.content,
                post.writerId,
                post.viewCount,
                generatedId.incrementAndGet()
            )
            posts.add(newPost)
            return newPost

        }else{
            posts.removeIf { it.id == post.id }
            posts.add(post)
            return post
        }
    }

    override fun delete(post: Post): Long? {
        val removeIf = posts.removeIf { it.id == post.id }
        if (removeIf==false){
            throw IllegalArgumentException()
        }
        return post.id
    }

    override fun findById(id: Long): Post? {
        return posts.find { it.id == id }

    }

    override fun findAll(pageable: Pageable): Page<Post> {
        val sortList = posts.sortedBy { it.lastUpdateDate }
        val startIndex = pageable.pageNumber * pageable.pageSize
        val lastIndex=startIndex+pageable.pageSize

        //페이지가 현재배열크기보다 크면 빈배열반환
        if(startIndex>sortList.size) return PageImpl(mutableListOf<Post>())
        val pagingList = sortList.subList(startIndex, min(lastIndex,sortList.size))
        return PageImpl(pagingList)
    }

    override fun findByTitleContaining(title: String, pageable: Pageable): Page<Post> {
        val startIndex = pageable.pageNumber * pageable.pageSize
        val lastIndex=startIndex+pageable.pageSize
        val sortList = posts.sortedBy { it.lastUpdateDate }
        val filterList = sortList.filter { it.title.contains(title) }
        if(startIndex>filterList.size) return PageImpl(mutableListOf<Post>())
        val pagingList = filterList.subList(startIndex, min(lastIndex,filterList.size))
        return PageImpl(pagingList)
    }
}