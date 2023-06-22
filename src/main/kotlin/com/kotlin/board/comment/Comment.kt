package com.kotlin.board.comment

import com.kotlin.board.baseentity.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Comment(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id:Long?=null,
        var content: String,
        var writerId:String,
):BaseEntity() {
}