package com.example.gymmanagement

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.dialog.MaterialDialogs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.lang.StringBuilder
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {
    lateinit var memberList: ArrayList<Pair<Int, String>>
    lateinit var tempList: ArrayList<Pair<Int, String>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val searchView = findViewById<SearchView>(R.id.search_view_gym_member)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_members)
        val addButton = findViewById<FloatingActionButton>(R.id.btn_add)
        recyclerView.layoutManager = LinearLayoutManager(this)
        memberList = ArrayList()
        tempList = ArrayList()
        memberList.add(Pair(1, "Ajay"))
        memberList.add(Pair(2, "Amit"))
        memberList.add(Pair(3, "Lalit"))
        memberList.add(Pair(4, "Kabir"))
        memberList.add(Pair(5, "Ankit"))
        memberList.add(Pair(6, "Sanjay"))
        Log.d("Testing", memberList.size.toString())
        var adapter = MemberListAdapter(memberList)

        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                tempList.clear()
                searchView.clearFocus()
                if (isInt(p0)) {          // It is an Int
                    tempList.add(memberList[Integer.parseInt(p0)])
                } else {                   // Not an Int
                    if (p0 != null)
                        tempList = findMembers(p0)
                }
                adapter = MemberListAdapter(tempList)
                recyclerView.adapter = adapter
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                tempList.clear()
                if (isInt(p0)) {          // It is an Int
                    tempList.add(memberList[Integer.parseInt(p0) - 1])
                } else {                   // Not an Int
                    if (p0 != null)
                        tempList = findMembers(p0)
                }
                adapter = MemberListAdapter(tempList)
                recyclerView.adapter = adapter
                return false
            }

        })
        addButton.setOnClickListener {
            val dialog = Dialog(this, R.style.Theme_Dialog)
            dialog.setContentView(R.layout.custom_dialog_add)
            val dialog_et_name = dialog.findViewById<EditText>(R.id.et_name)
            val dialog_et_ID = dialog.findViewById<EditText>(R.id.et_ID)
            val dialog_chbx_ID_editable = dialog.findViewById<CheckBox>(R.id.chb_editable_ID)
            val dialog_btn_submit = dialog.findViewById<Button>(R.id.btn_submit)
            val dialog_btn_cancel = dialog.findViewById<Button>(R.id.btn_cancel)
            dialog.show()

            dialog_et_ID.setText(memberList.size.toString())
            dialog_chbx_ID_editable.setOnCheckedChangeListener { _,_ ->
                dialog_et_ID.isEnabled = true
            }
            dialog_btn_submit.setOnClickListener {
                val member_name = dialog_et_name.text.toString()
                val member_ID = dialog_et_ID.text.toString()
                if (member_name.isNotEmpty()) {
                        memberList.add(Pair(member_ID.toInt()+1, member_name))
                        Log.d("TestingTheElement", memberList.get(memberList.size-1).second)
                        dialog.dismiss()
                }
            }
        }
    }
    fun findMembers(check:String): ArrayList<Pair<Int, String>>{
        var members: ArrayList<Pair<Int, String>> = ArrayList()
        if(check.isNotEmpty()){
            memberList.forEach {
                if(it.second.lowercase().contains(check.lowercase())) {
                    members.add(it)
                }
            }
        }
        return members
    }
    fun isInt(check: String?):Boolean {
        if (check != null) {
            if(check.isNotEmpty()) {
                return TextUtils.isDigitsOnly(check)
            }
        }
        return false
    }

}



