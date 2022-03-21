package com.example.gymmanagement.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "member_table")
data class Member(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val phoneNumber: String
)
