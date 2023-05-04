package com.example.touristo.formData

import com.example.touristo.validations.ValidationResult

class VillaRegisterValidation(
    private  var villaName:String,
    private  var villaDescription:String,
    private  var villaPrice:String,
    private  var villaRating:String,
) {
    fun validateDescription():ValidationResult{
        val regex = "^.{150,}\$"
        return if(villaDescription.toString().isEmpty()){
            ValidationResult.Empty("Please Enter Description")
        }else if(!villaDescription.toString().matches(regex.toRegex())){
            ValidationResult.Invalid("Enter At Least 150 char") // Phone number is invalid
        }else{
            ValidationResult.Valid
        }
    }
    fun validateVillaName():ValidationResult{
        val regex ="^[0-9]+\$\n"
        return if(villaName.toString().isEmpty()){
            ValidationResult.Empty("Please Enter Name")
        }else if(villaName.toString().matches(regex.toRegex())){
            ValidationResult.Invalid("Only Alphabets") // Phone number is invalid
        }else if (villaName.toString().length<4) {
            ValidationResult.Invalid("At least 4 Letters")
        }else{
            ValidationResult.Valid
        }
    }
    fun validatePrice(): ValidationResult {
        val regex = "^\\d+(\\.\\d+)?\$"
        return if (villaPrice.isBlank()) {
            ValidationResult.Empty("Please enter price")
        } else {
            try {
                val price = villaPrice.toDouble()
                if (price <= 0) {
                    ValidationResult.Invalid("Price must be greater than 0")
                } else if (!price.toString().matches(regex.toRegex())) {
                    ValidationResult.Invalid("Invalid price format")
                } else {
                    ValidationResult.Valid
                }
            } catch (e: NumberFormatException) {
                ValidationResult.Invalid("Invalid price format")
            }
        }
    }

    fun validateRating():ValidationResult{
//        villaRating.toDouble()
        val regex = "^(0(\\.\\d+)?|[1-4](\\.\\d+)?|5(\\.\\d+)?)$"
        return if(villaRating.toString().isEmpty()){
            ValidationResult.Empty("Please Enter Rating")
        }else if(!villaRating.toString().matches(regex.toRegex())){
            ValidationResult.Invalid("between 0-5") // Phone number is invalid
        }else{
            ValidationResult.Valid
        }
    }
}