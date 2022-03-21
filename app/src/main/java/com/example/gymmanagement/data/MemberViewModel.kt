package com.example.gymmanagement.data

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemberViewModel(application: Application) : AndroidViewModel(application) {

    val read: LiveData<List<Member>>
    val size: LiveData<Int>
    private val repository: MemberRepository

    init {
        val memberDao = MemberDatabase.getDatabase(application).memberDao()
        repository = MemberRepository(memberDao)
        read = repository.read
        size = repository.size
    }

    fun insert(member: Member) {
        viewModelScope.launch(Dispatchers.IO){
            repository.insert(member)
        }
    }
    fun update(member: Member) {
        viewModelScope.launch(Dispatchers.IO){
            repository.update(member)
        }
    }
    fun delete(member: Member) {
        viewModelScope.launch(Dispatchers.IO){
            repository.delete(member)
        }
    }

}