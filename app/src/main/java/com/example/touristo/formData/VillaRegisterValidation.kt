package com.example.touristo.formData

import com.example.touristo.validations.ValidationResult

class VillaRegisterValidation(
    private  var villaName:String,
    private  var villaDescription:String,
    private  var villaPrice:String,
    private  var villaRating:String,
) {
    fun validateDescription():ValidationResult{
        val regex = "^.{150,}\$\n"
        return if(villaDescription.toString().isEmpty()){
            ValidationResult.Empty("Please Enter Description")
        }else if(villaDescription.toString().matches(regex.toRegex())){
            ValidationResult.Invalid("Enter At Least 150 char") // Phone number is invalid
        }else{
            ValidationResult.Valid
        }
    }
    fun validateVillaName():ValidationResult{
        val regex ="^[a-zA-Z]+\$\n"
        return if(villaName.toString().isEmpty()){
            ValidationResult.Empty("Please Enter Name")
        }else if(villaName.toString().matches(regex.toRegex())){
            ValidationResult.Invalid("Only Alphabets") // Phone number is invalid
        }else{
            ValidationResult.Valid
        }
    }
    fun validatePrice():ValidationResult{
        val regex = "^\\d+(\\.\\d+)?\$\n"
        return if(villaPrice.toString().isEmpty()){
            ValidationResult.Empty("Please Enter Price")
        }else if(villaPrice.toString().matches(regex.toRegex())){
            ValidationResult.Invalid("Only Positive Numbers") // Phone number is invalid
        }else{
            ValidationResult.Valid
        }
    }
    fun validateRating():ValidationResult{
        val regex = "^(0(\\.\\d+)?|[1-4](\\.\\d+)?|5(\\.0+)?)\$\n"
        return if(villaRating.toString().isEmpty()){
            ValidationResult.Empty("Please Enter Rating")
        }else if(villaRating.toString().matches(regex.toRegex())){
            ValidationResult.Invalid("between 0-5") // Phone number is invalid
        }else{
            ValidationResult.Valid
        }
    }
}