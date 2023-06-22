package com.kotlin.board

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class BoardApplication

fun main(args: Array<String>) {
	runApplication<BoardApplication>(*args)
}
