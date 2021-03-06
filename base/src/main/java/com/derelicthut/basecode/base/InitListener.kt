package com.derelicthut.basecode.base

/**
 * @author wjl
 */
interface InitListener {
    /**
     * 初始化view
     */
    fun initView()

    /**
     * 初始化值
     */
    fun initVariable()

    /**
     * 初始化监听
     */
    fun initListener()
}