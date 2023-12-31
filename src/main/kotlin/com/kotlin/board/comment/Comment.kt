package com.kotlin.board.comment

import com.kotlin.board.baseentity.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotNull

@Entity
class Comment(
        @NotNull
        var content: String,
        @NotNull
        var writerId:String,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id:Long=0
):BaseEntity() {
        fun update(content: String):Long{
                this.content=content
                return id
        }

}