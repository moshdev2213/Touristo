package com.example.touristo.formData

import com.example.touristo.validations.ValidationResult

class UserLoginForm(
    private var uemail:String,
    private var password:String,
) {
    fun validatePassword(): ValidationResult {

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

    fun validateUserEmail():ValidationResult{
        val regex = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}" // Regex to match email addresses

        return if(uemail.isEmpty()){
            ValidationResult.Empty("Please Enter Email")
        }else if(!uemail.matches(regex.toRegex())){
            ValidationResult.Invalid("Please Enter Valid Email")
        }else{
            ValidationResult.Valid
        }
    }
}