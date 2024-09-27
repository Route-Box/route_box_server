package com.routebox.routebox.domain.route

import com.routebox.routebox.domain.common.FileEntity
import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

class RouteImage(
    storedFileName: String,
    fileUrl: String,
    id: Long = 0,
) : FileEntity(storedFileName, fileUrl) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_image_id")
    val id: Long = id
}
