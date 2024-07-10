package com.routebox.routebox.logger

class TraceId {
    val id: String
    val level: Int

    constructor() {
        this.id = createId()
        this.level = 0
    }

    private constructor(id: String, level: Int) {
        this.id = id
        this.level = level
    }

    private fun createId(): String = LogTraceUtils.logTraceId

    fun createNextId(): TraceId = TraceId(id, level + 1)

    fun createPrevId(): TraceId = TraceId(id, level - 1)

    val isFirstLevel: Boolean
        get() = level == 0
}
