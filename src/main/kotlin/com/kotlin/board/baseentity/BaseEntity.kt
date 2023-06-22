package com.kotlin.board.baseentity

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@EntityListeners
@MappedSuperclass
abstract class BaseEntity {
    @CreatedDate
    var createTime:LocalDateTime= LocalDateTime.MIN
    @LastModifiedDate
    var lastUpdateDate:LocalDateTime=LocalDateTime.MIN
}