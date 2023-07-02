package com.kotlin.board.post.repository

import com.kotlin.board.post.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PostRepository {

    fun save(post: Post):Post;
    fun delete(post: Post):Long;
    fun findById(id:Long):Post?;

    fun findAll(pageable: Pageable): Page<Post>;
    fun findByTitleContaining(title:String,pageable: Pageable):Page<Post>;

}