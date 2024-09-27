package com.routebox.routebox.domain.route

import com.routebox.routebox.domain.common.FileEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Table(name = "route_activity_image")
@Entity
class RouteActivityImage(
    activity: RouteActivity,
    storedFileName: String,
    fileUrl: String,
    id: Long = 0,
) : FileEntity(storedFileName, fileUrl) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_activity_image_id")
    val id: Long = id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_activity_id")
    val activity: RouteActivity = activity
}
