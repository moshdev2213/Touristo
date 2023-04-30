package com.example.touristo.formData

import androidx.room.RoomOpenHelper
import com.example.touristo.validations.ValidationResult

class UserProfileValidation(
    private var uname:String,
    private var password:String,
    private var tel:String,
    private var propic:String,
    private var age:String,
    private var country:String,
) {
    fun validateUserName(): ValidationResult {
        val regex = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]+$" // Regex to match strings with at least one letter and one digit

        return if(uname.isEmpty()){
            ValidationResult.Empty("Please Enter Name")
        }else if(uname.length>15){
            ValidationResult.Invalid("Name Too Lenghty")
        }else if(!uname.matches(regex.toRegex())){
            ValidationResult.Invalid("Name Contains Number Only")
        }else{
            ValidationResult.Valid
        }
    }

    fun validatePassword():ValidationResult{
        // Regex to validate password with all uppercase ,lowwercase,special characters,and minimum 8 chars
        //val regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+\$).{8,}\$"

        // Regex to validate password with alphaNumerics,and minimum 8 chars
        val passwordPattern = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$"


        return if(password.isEmpty()){
            ValidationResult.Empty("Please Enter Password")
        } else if (!password.matches(passwordPattern.toRegex())) {
            ValidationResult.Invalid("Invalid ex: Aa@asda22") // Password is invalid
        } else{
            ValidationResult.Valid
        }

    }
    fun validateTel():ValidationResult{
        // Regex to validate phone number
        val regex = "^(\\+\\d{1,3}[- ]?)?\\d{10}\$"

        return  if (tel.isEmpty()){
            ValidationResult.Empty("Please Enter Telephone")
        }else if(!tel.matches(regex.toRegex())) {
            ValidationResult.Invalid("Phone number is invalid") // Phone number is invalid
        }else {
            ValidationResult.Valid // Phone number is valid
        }
    }
    fun validationAge():ValidationResult{
        val regex = "^[1-9]\\d*$"
        return if(age.toString().isEmpty()){
            ValidationResult.Empty("Please Enter Age")
        }else if(!age.toString().matches(regex.toRegex())){
            ValidationResult.Invalid("Age is invalid") // Phone number is invalid
        }else{
            ValidationResult.Valid
        }

    }
    fun validateCountry():ValidationResult{
        val regex = "^[a-zA-Z\\s]+\$"
        return if (country.isEmpty()){
            ValidationResult.Empty("Please Enter Country")
        }else if(!country.matches(regex.toRegex())){
            ValidationResult.Invalid("Country Not VAlid")
        }else{
            ValidationResult.Valid
        }
    }
}