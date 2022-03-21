package com.example.gymmanagement.data

import androidx.lifecycle.LiveData

class MemberRepository(private val memberDao: MemberDao) {

    val read: LiveData<List<Member>> = memberDao.read()
    val size: LiveData<Int> = memberDao.dbSize()

    suspend fun insert(member: Member){
        memberDao.insert(member)
    }
    suspend fun update(member: Member){
        memberDao.update(member)
    }
    suspend fun delete(member: Member){
        memberDao.delete(member)
    }


//    fun getSize():LiveData<Int> {
//       return memberDao.getSize()
//    }
}