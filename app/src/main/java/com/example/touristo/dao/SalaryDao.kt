package com.example.touristo.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.touristo.modal.Salary

@Dao
interface SalaryDao {
    @Insert
    suspend fun insertUser(salary: Salary)

    @Update
    suspend fun updateUser(salary: Salary)

    @Delete
    suspend fun deleteUser(salary: Salary)

    @Query("SELECT * FROM salary")
    fun getAllUsers(): LiveData<List<Salary>>
}