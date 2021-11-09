package com.example.gymmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val searchView = findViewById<SearchView>(R.id.search_view_gym_member)
        val listView = findViewById<ListView>(R.id.list_view_gym_member)
        val memberList = ArrayList<Pair<Int, String>>()
        memberList.add(Pair(1, "Ajay"))
        memberList.add(Pair(2, "Amit"))
        memberList.add(Pair(3, "Lalit"))
        memberList.add(Pair(4, "Kabir"))
        memberList.add(Pair(5, "Ankit"))
        memberList.add(Pair(6, "Sanjay"))
        val adapter:ArrayAdapter<Pair<Int, String>> = ArrayAdapter(this, android.R.layout.simple_list_item_1
            , memberList)

        listView.adapter=adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                searchView.clearFocus()
                var isMemberRegistered = false
                var member: Pair<Int, String> = Pair(0, "")
                for(i in 0..memberList.size-1){
                    if(memberList[i].first.toString() == p0 || memberList[i].second == p0){
                        member = memberList[i]
                        isMemberRegistered = true
                    }
                }
                if(isMemberRegistered){
                    adapter.filter.filter(member.toString())
                }else{
                    Toast.makeText(applicationContext, "Member not registered yet.", Toast.LENGTH_SHORT).show()
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapter.filter.filter(p0.toString())
                return false
            }

        })
    }
}