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
import org.hibernate.annotations.BatchSize

@Entity
class Post(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id:Long?=null,
        var title: String,
        var content: String,
        var writerId:String,
) :BaseEntity(){
    var viewCount:Long=0
    @OneToMany(cascade = [CascadeType.ALL])
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


}
