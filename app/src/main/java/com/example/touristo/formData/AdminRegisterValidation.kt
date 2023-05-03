package com.example.touristo.formData

import com.example.touristo.validations.ValidationResult

class AdminRegisterValidation(
    private var fname:String,
    private var lname:String,
    private var uemail:String,
    private var password:String,
    private var repassword:String,
    private var tel:String,
    private var age:String,
) {
    fun validateUserEmail(): ValidationResult {
        val regex = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}" // Regex to match email addresses

        return if(uemail.isEmpty()){
            ValidationResult.Empty("Please Enter Email")
        }else if(uemail.equals(fname)){
            ValidationResult.Invalid("Name and Email Are Equal")
        }else if(!uemail.matches(regex.toRegex())){
            ValidationResult.Invalid("Please Enter Valid Email")
        }else{
            ValidationResult.Valid
        }
    }

    fun validateFirstName(): ValidationResult {
        val regex = "^[a-zA-Z ]+\$" // only alphabetic characters or alphabetic characters and spaces:

        return if(fname.isEmpty()){
            ValidationResult.Empty("Please Enter Name")
        }else if(fname.length>15){
            ValidationResult.Invalid("Name Too Lenghty")
        }else if(!fname.matches(regex.toRegex())){
            ValidationResult.Invalid("No Numbers allowed")
        }else{
            ValidationResult.Valid
        }
    }
    fun validateLastName(): ValidationResult {
        val regex = "^[a-zA-Z ]+\$" // only alphabetic characters or alphabetic characters and spaces:

        return if(lname.isEmpty()){
            ValidationResult.Empty("Please Enter Name")
        }else if(lname.length>15){
            ValidationResult.Invalid("Name Too Lenghty")
        }else if(!lname.matches(regex.toRegex())){
            ValidationResult.Invalid("No Numbers allowed")
        }else{
            ValidationResult.Valid
        }
    }

    fun validatePassword(): ValidationResult {
        // Regex to validate password with all uppercase ,lowwercase,special characters,and minimum 8 chars
        //val regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+\$).{8,}\$"

        // Regex to validate password with alphaNumerics,and minimum 8 chars
        val passwordPattern = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$"


        return if(password.isEmpty()){
            ValidationResult.Empty("Please Enter Password")
        } else if (!password.matches(passwordPattern.toRegex())) {
            ValidationResult.Invalid("Invalid ex: Amma12345") // Password is invalid
        } else if(repassword != password){
            ValidationResult.Invalid("Password Mismatch") // Password is invalid
        }else{
            ValidationResult.Valid
        }

    }
    fun validateTel(): ValidationResult {
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
    fun validationAge(): ValidationResult {
        val regex = "^[1-9]\\d*$"
        return if(age.toString().isEmpty()){
            ValidationResult.Empty("Please Enter Age")
        }else if(!age.toString().matches(regex.toRegex())){
            ValidationResult.Invalid("Age is invalid") // Phone number is invalid
        }else{
            ValidationResult.Valid
        }

    }
}