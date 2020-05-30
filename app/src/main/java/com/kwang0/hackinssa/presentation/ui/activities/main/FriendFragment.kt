package com.kwang0.hackinssa.presentation.ui.activities.main

import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.jakewharton.rxbinding4.widget.textChanges

import com.kwang0.hackinssa.R
import com.kwang0.hackinssa.helper.IntentHelper
import com.kwang0.hackinssa.helper.hideKeyboard
import com.kwang0.hackinssa.presentation.ui.activities.friendadd.FriendAddActivity
import com.kwang0.hackinssa.presentation.ui.views.FriendView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class FriendFragment : Fragment() {

    lateinit var search_et: EditText

    private var friendView: FriendView? = null
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_friend, container, false)

        search_et = v.findViewById<EditText>(R.id.searchbar_et)

        friendViewSetUp(v)

        searchTextChanges(search_et)
        
        return v
    }

    private fun searchTextChanges(et: EditText) {
        et.textChanges()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { chars ->
                    val searchTerm: String = chars.trim().toString()
                    if (chars.trim().isEmpty()) {
                        hideKeyboard()
                        friendView?.friendPresenter?.search()
                    } else {
                        friendView?.friendPresenter?.search(searchTerm)
                    }
                }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_friend, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        return if (id == R.id.menu_friend_add_friend) {
            IntentHelper.activityIntent(context, FriendAddActivity::class.java)
            true
        } else if(id == R.id.menu_friend_name_q) {
            friendView?.sortNameResults()
            true
        } else if(id == R.id.menu_friend_create_q) {
            friendView?.sortCreatedResults()
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun friendViewSetUp(v: View) {
        friendView = FriendView(v.context)
        friendView?.bindView(v)
    }
}