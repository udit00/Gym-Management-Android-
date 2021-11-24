package com.example.gymmanagement.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "member_table")
data class Member(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val phoneNumber: String
)
