package com.lzp.baseui

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.widget.Toast

/**
 * Created by li.zhipeng on 2018/8/24.
 */
class LifeCycleBean(val context: Context) : LifecycleObserver{

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(){
        Toast.makeText(context, "onResume", Toast.LENGTH_SHORT).show()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop(){
        Toast.makeText(context, "onStop", Toast.LENGTH_SHORT).show()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        Toast.makeText(context, "onDestroy", Toast.LENGTH_SHORT).show()
    }

}