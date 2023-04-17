package com.example.touristo.formData

import com.example.touristo.validations.ValidationResult

class UserRegisterForm (
    private var uname:String,
    private var uemail:String,
    private var password:String,
    private var repassword:String,
    private var tel:String,
    private var propic:String,
    private var age:Int,
    private var gender:String,
    private var country:String,
){
    fun validateUserName():ValidationResult{
        val regex = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]+$" // Regex to match strings with at least one letter and one digit

        return if(uname.isEmpty()){
            ValidationResult.Empty("Please Enter Name")
        }else if(uname.length>15){
            ValidationResult.Invalid("Name Too Lenghty")
        }else if(uname.equals(uemail)){
            ValidationResult.Invalid("Name and Email Are Equal")
        }else if(!uname.matches(regex.toRegex())){
            ValidationResult.Invalid("Name Contains Number Only")
        }else{
            ValidationResult.Valid
        }
    }

    fun validateUserEmail():ValidationResult{
        val regex = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}" // Regex to match email addresses

        return if(uemail.isEmpty()){
            ValidationResult.Empty("Please Enter Email")
        }else if(uemail.equals(uname)){
            ValidationResult.Invalid("Name and Email Are Equal")
        }else if(!uemail.matches(regex.toRegex())){
            ValidationResult.Invalid("Please Enter Valid Email")
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
        }else if(password!=repassword){
            ValidationResult.Invalid("Passwords MisMatch")
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
}