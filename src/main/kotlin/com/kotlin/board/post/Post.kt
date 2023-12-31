package com.kotlin.board.post

import com.kotlin.board.baseentity.BaseEntity
import com.kotlin.board.comment.Comment
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.BatchSize



@Entity
class Post(
        @NotNull
        var title: String,
        @NotNull
        var content: String,
        @NotNull
        var writerId:String,
        var viewCount:Long=0,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id:Long=0
) :BaseEntity(){

    @OneToMany
    @JoinColumn(name = "POST_ID")
    @BatchSize(size=20)
    val comments:MutableList<Comment> = mutableListOf()

    fun plusViewCount():Long{
        viewCount++
        return viewCount
    }
    fun addComment(comment: Comment):Int{
        comments.add(comment)
        return comments.size
    }
    fun deleteComment(comment: Comment):Int{
        comments.remove(comment)
        return comments.size
    }
    fun update(title:String, content: String):Long?{
        this.title=title
        this.content=content
        return id
    }


}
