package com.example.gymmanagement.Data

import androidx.lifecycle.LiveData

class MemberRepository(private val memberDao: MemberDao) {

    val readAllData: LiveData<List<Member>> = memberDao.read()

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