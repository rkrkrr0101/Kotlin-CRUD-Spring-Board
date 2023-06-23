package com.kotlin.board.post.repository

import com.kotlin.board.post.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository


interface PostJpaRepository:JpaRepository<Post,Long> {
    fun findByTitleContaining(title:String,pageable: Pageable):Page<Post>;
}