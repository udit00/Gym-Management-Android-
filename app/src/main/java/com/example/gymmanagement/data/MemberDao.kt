package com.example.gymmanagement.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MemberDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(member: Member)

    @Query("SELECT * FROM member_table ORDER BY id ASC")
    fun read(): LiveData<List<Member>>

    @Update
    fun update(member: Member)

    @Delete
    fun delete(member: Member)

    @Query("Select Count(id) from member_table")
    fun dbSize(): LiveData<Int>

//    @Query("SELECT COUNT(*) FROM member_table")
//    fun getSize(): LiveData<Int>
}

