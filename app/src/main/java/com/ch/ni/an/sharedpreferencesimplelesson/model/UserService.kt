package com.ch.ni.an.sharedpreferencesimplelesson.model

import com.bumptech.glide.Glide.init
import com.bumptech.glide.load.resource.SimpleResource
import com.ch.ni.an.sharedpreferencesimplelesson.UserNotFoundExceptions
import com.ch.ni.an.sharedpreferencesimplelesson.tasks.SimpleTask
import com.ch.ni.an.sharedpreferencesimplelesson.tasks.Task
import com.github.javafaker.Faker
import java.util.*
import java.util.concurrent.Callable
import kotlin.collections.ArrayList


typealias UsersListener = (users: List<User>) -> Unit

class UserService() {
    private var users = mutableListOf<User>()
    private val listeners = mutableSetOf<UsersListener>()
    private var loaded: Boolean = false


        fun loadUsers(): Task<Unit> = SimpleTask<Unit>(Callable {
            Thread.sleep(2000)
            val faker = Faker.instance()
            images.shuffle()
            users = (1..100).map {

                User(
                    id = it.toLong(),
                    name = faker.name().name(),
                    company = faker.company().name(),
                    photo = images[it % images.size]
                )

            }.toMutableList()
            loaded = true
        })



    fun getById(id: Long): Task<UserDetails> = SimpleTask<UserDetails>(Callable {
        Thread.sleep(2000)
        val user = users.firstOrNull { id == it.id } ?: throw UserNotFoundExceptions()
        return@Callable UserDetails(
            user = user,
            details =  Faker.instance().lorem().paragraphs(3).joinToString ("\n\n")
        )
    })




        fun deleteUser(user: User): Task<Unit> =SimpleTask<Unit>(Callable {
            Thread.sleep(2000)
            val indexToDelete = users.indexOfFirst { it.id == user.id }
            if(indexToDelete != -1){
                users = ArrayList(users)
                users.removeAt(indexToDelete)
                notifyChanges()
            } })



        fun moveUser(user: User, moveBy: Int): Task<Unit> = SimpleTask<Unit>(Callable {
            Thread.sleep(2000)
            val oldIndex = users.indexOfFirst { it.id == user.id }
            if(oldIndex == -1) return@Callable
            val newIndex = oldIndex + moveBy
            if(newIndex < 0 || newIndex >= users.size) return@Callable
            users = ArrayList(users)
            Collections.swap(users, oldIndex, newIndex)
            notifyChanges()
        })

        fun addListener(listener: UsersListener){
            listeners.add(listener)
            if(loaded) {
                listener.invoke(users)
            }
        }

        fun removeListener(listener: UsersListener){
            listeners.remove(listener)
        }

        fun fireUser(user: User){
            val index = users.indexOfFirst { user.id == it.id }
            if(index == -1) return
            val updatedUser = users[index].copy(company = "")
            users = ArrayList(users)
            users[index] = updatedUser
            notifyChanges()
         }






    private fun notifyChanges(){
        if(!loaded) return
        listeners.forEach{ it.invoke(users)}
    }

    companion object {
        private val images = mutableListOf<String>("https://images.unsplash.com/photo-1600267185393-e158a98703de?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NjQ0&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800 ",
                "https://images.unsplash.com/photo-1579710039144-85d6bdffddc9?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0Njk1&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800 ",
                "https://images.unsplash.com/photo-1488426862026-3ee34a7d66df?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0ODE0&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800 ",
                "https://images.unsplash.com/photo-1620252655460-080dbec533ca?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NzQ1&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800 ",
                "https://images.unsplash.com/photo-1613679074971-91fc27180061?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NzUz&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800 ",
                "https://images.unsplash.com/photo-1485795959911-ea5ebf41b6ae?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NzU4&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800 ",
                "https://images.unsplash.com/photo-1545996124-0501ebae84d0?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NzY1&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800 ",
                "https://images.unsplash.com/flagged/photo-1568225061049-70fb3006b5be?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0Nzcy&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800 ",
                "https://images.unsplash.com/photo-1567186937675-a5131c8a89ea?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0ODYx&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800 ",
                "https://images.unsplash.com/photo-1546456073-92b9f0a8d413?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0ODY1&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800 ")
    }

}