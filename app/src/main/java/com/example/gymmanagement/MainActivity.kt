package com.example.gymmanagement

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymmanagement.data.Member
import com.example.gymmanagement.data.MemberViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, MemberItemClicked {
    lateinit var tempList: ArrayList<Member>
    lateinit var mMemberViewModel: MemberViewModel
    lateinit var menu: Menu
    var positionSpinner: Int = 0
    var rollNo : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val spinner = findViewById<Spinner>(R.id.spn_search_helper)
        val searchView = findViewById<SearchView>(R.id.search_view_gym_member)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_members)
        val addButton = findViewById<FloatingActionButton>(R.id.btn_add)
        recyclerView.layoutManager = LinearLayoutManager(this)
        tempList = ArrayList()
        var adapter = MemberListAdapter(this)
        recyclerView.adapter = adapter

        spinner.onItemSelectedListener = this

        ArrayAdapter.createFromResource(
            this,
            R.array.search_helper_spinner_content,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        mMemberViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[MemberViewModel::class.java]
        mMemberViewModel.read.observe(this@MainActivity, Observer { list ->
            list?.let {
                adapter.setData(it)
                rollNo = if(it.isNotEmpty())
                    it[it.size-1].id
                else
                    0
            }
        })




        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                tempList.clear()
                searchView.clearFocus()
                if (p0 != null)
                    mMemberViewModel.read.observe(this@MainActivity, Observer {
                        tempList.addAll(it)
                        adapter.setData(findMembers(p0, tempList))
                    })
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 == "") {
                    mMemberViewModel.read.observe(this@MainActivity, Observer {
                        adapter.setData(it)
                    })
                } else {
                    tempList.clear()           // Not an Int
                    if (p0 != null)
                        mMemberViewModel.read.observe(this@MainActivity, Observer {
                            tempList.addAll(it)
                            adapter.setData(findMembers(p0, tempList))
                        })
                }
                return false
            }

        })

        addButton.setOnClickListener {
            val dialog = Dialog(this, R.style.Theme_Dialog)
            dialog.setContentView(R.layout.custom_dialog_add)
            dialog.setCancelable(true)
            val dialogEtName = dialog.findViewById<EditText>(R.id.et_name)
            val dialogIvDelete = dialog.findViewById<ImageView>(R.id.iv_delete)
            val dialogEtPhone = dialog.findViewById<EditText>(R.id.et_phone_number)
            val dialogEtId = dialog.findViewById<EditText>(R.id.et_ID)
            val dialogChbxIdEditable = dialog.findViewById<CheckBox>(R.id.chb_editable_ID)
            val dialogBtnSubmit = dialog.findViewById<TextView>(R.id.btn_submit)
            val dialogBtnCancel = dialog.findViewById<TextView>(R.id.btn_cancel)
            dialogIvDelete.visibility = View.INVISIBLE

            dialogEtId.setText((rollNo + 1).toString())

            dialog.show()
            dialogChbxIdEditable.setOnCheckedChangeListener { _, _ ->
                dialogEtId.isEnabled = dialogChbxIdEditable.isChecked
            }

            dialogBtnSubmit.setOnClickListener {
                val memberName = dialogEtName.text.toString()
                val memberPhone = dialogEtPhone.text.toString()
                val memberID = dialogEtId.text.toString().toInt()
                if(!ifExist(memberID)) {
                    if (memberName.isNotEmpty() && memberPhone.isNotEmpty()) {
                        rollNo += 1
                        val member = Member(memberID, memberName, memberPhone)
                        mMemberViewModel.insert(member)
                        dialog.dismiss()
                        recyclerView.scrollToPosition(rollNo)
                    } else {
                        Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                else{
                    Toast.makeText(this, "Roll Number $memberID Already Taken", Toast.LENGTH_SHORT).show()
                }
                dialogBtnCancel.setOnClickListener {
                    dialog.dismiss()
                }
            }


        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        positionSpinner = p2+1
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


    fun findMembers(check: String, memberList: List<Member>?): ArrayList<Member> {
        var members: ArrayList<Member> = ArrayList()
        memberList?.let {
            if (check.isNotEmpty()) {
                memberList.forEach { member ->
                    if(positionSpinner == 1){
                        if(member.phoneNumber.contains(check))
                            members.add(member)
                    }
                    else if(positionSpinner == 2){
                        if(member.id.toString().contains(check))
                            members.add(member)
                    }
                    else{
                        if(member.name.lowercase().contains(check.lowercase()))
                            members.add(member)
                    }
                }
            }
        }
        return members
    }

    override fun onItemClicked(member: Member) {
        val dialog = Dialog(this, R.style.Theme_Dialog)
        dialog.setContentView(R.layout.custom_dialog_add)
        val dialogTvTile = dialog.findViewById<TextView>(R.id.tv_title)
        val dialogEtName = dialog.findViewById<EditText>(R.id.et_name)
        val dialogEtPhone = dialog.findViewById<EditText>(R.id.et_phone_number)
        val dialogEtId = dialog.findViewById<EditText>(R.id.et_ID)
        val dialogChbxIdEditable = dialog.findViewById<CheckBox>(R.id.chb_editable_ID)
        val dialogBtnSubmit = dialog.findViewById<TextView>(R.id.btn_submit)
        val dialogBtnCancel = dialog.findViewById<TextView>(R.id.btn_cancel)
        val dialogIvDelete = dialog.findViewById<ImageView>(R.id.iv_delete)
        dialogTvTile.setText("Update a member")
        dialogTvTile.setTextColor(ContextCompat.getColor(
                applicationContext,
                R.color.odd
            ))
        dialogEtName.setText(member.name)
        dialogEtPhone.setText(member.phoneNumber)
        dialogEtId.setText(member.id.toString())
        dialog.show()


        dialogChbxIdEditable.setOnCheckedChangeListener { _, _ ->
            dialogEtId.isEnabled = dialogChbxIdEditable.isChecked
        }

        dialogBtnSubmit.setOnClickListener {
            val memberName: String = dialogEtName.text.toString()
            val memberPhone: String = dialogEtPhone.text.toString()

            if (memberName.isNotEmpty() && memberPhone.isNotEmpty()) {
                val member = Member(member.id, memberName, memberPhone)
                mMemberViewModel.update(member)
                dialog.dismiss()
            }else{
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }
        dialogBtnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialogIvDelete.setOnClickListener{
            val alertDialog = AlertDialog.Builder(this)
                .setTitle("Delete Member")
                .setMessage("Are you sure ?")
                .setCancelable(false)
            alertDialog.setPositiveButton("Yes"){ alert, which ->
                mMemberViewModel.delete(member)
                alert.dismiss()
                dialog.dismiss()
                Toast.makeText(this, "Member Deleted", Toast.LENGTH_SHORT).show()
            }
            alertDialog.setNegativeButton("No"){ alert, which->
                alert.dismiss()
            }
            alertDialog.show()
        }
    }

    private fun ifExist(rollNo : Int): Boolean{
        var ifRollNoExist = false
        mMemberViewModel.read.observe(this@MainActivity, Observer { list ->
            list?.let {
                if(rollNo > 0)
                list.forEach {
                    if(it.id == rollNo) {
                        ifRollNoExist = true
                        Log.d("member", it.name)
                    }
                }
            }
        })
        return ifRollNoExist
    }


}





