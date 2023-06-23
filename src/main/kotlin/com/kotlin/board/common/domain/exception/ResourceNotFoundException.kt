package com.kotlin.board.common.domain.exception


class ResourceNotFoundException: RuntimeException {
    constructor(source: String,id:Long) : super("$source to $id not found")
    constructor(source: String,id:String) : super("$source to $id not found")

}