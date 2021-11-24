package com.example.gymmanagement.Data

import android.app.Application
import android.view.View
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MemberViewModel(application: Application) : AndroidViewModel(application) {

    val read: LiveData<List<Member>>
    private val repository: MemberRepository

    init {
        val memberDao = MemberDatabase.getDatabase(application).memberDao()
        repository = MemberRepository(memberDao)
        read = repository.readAllData
    }

    fun insert(member: Member) = viewModelScope.launch(Dispatchers.IO){
            repository.insert(member)
    }
//    fun getSize(): LiveData<Int>{
//        return repository.getSize()
//    }
    fun update(member: Member) = viewModelScope.launch(Dispatchers.IO){
            repository.update(member)
    }
    fun delete(member: Member) = viewModelScope.launch(Dispatchers.IO){
            repository.delete(member)
    }

}