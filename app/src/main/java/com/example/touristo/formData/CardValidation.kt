package com.example.touristo.formData

import com.example.touristo.validations.ValidationResult

class CardValidation(
    private var cardHolderName:String,
    private var cvc:Int,
    private var cnumber:String,
    private var month:Int,
    private var year:Int,
) {
    fun validateCardNumber():ValidationResult{
        //card regex checker
        val regex = "^4[0-9]{12}(?:[0-9]{3})?$|^5[1-5][0-9]{14}$|^3[47][0-9]{13}$|^3(?:0[0-5]|[68][0-9])[0-9]{11}$|^6(?:011|5[0-9]{2})[0-9]{12}$|^(?:2131|1800|35\\d{3})\\d{11}$"

        return if(cnumber.isEmpty()){
            ValidationResult.Empty("Please Enter CardNumber")
        }else if(cnumber.length<16){
            ValidationResult.Invalid("Please Enter Valid CardNumber")
        }else if(cnumber.matches(regex.toRegex())){
            ValidationResult.Invalid("Please Enter Valid CardNumber")
        }else{
            ValidationResult.Valid
        }
    }
    fun validateCVC():ValidationResult{
        return if(cvc.toString().isEmpty()){
            ValidationResult.Empty("Please Enter CVC")
        }else if (cvc.toString().length>4 || cvc.toString().length<1){
            ValidationResult.Invalid("Please Enter Valid CVC")
        }else{
            ValidationResult.Valid
        }
    }
    fun validateMonth():ValidationResult{
        return if(month.toString().isEmpty()){
            ValidationResult.Empty("Please Enter Month")
        }else if(month<=0){
            ValidationResult.Invalid("Please Enter Valid Month")
        }else if(month>12){
            ValidationResult.Invalid("Please Enter Valid Month")
        }else if(month.toString().length>2){
            ValidationResult.Invalid("Please Enter Valid Month")
        } else{
            ValidationResult.Valid
        }
    }
    fun validateYear():ValidationResult{
        return if(year.toString().isEmpty()){
            ValidationResult.Empty("Please Enter Year")
        }else if(year<=0){
            ValidationResult.Invalid("Please Enter Valid Year")
        }else if(year.toString().length>4){
            ValidationResult.Invalid("Please Enter Valid Year")
        } else{
            ValidationResult.Valid
        }
    }
    fun validateCardName():ValidationResult{
        val regex = Regex("[^a-zA-Z]+")
        return if(cardHolderName.isEmpty()) {
            ValidationResult.Empty("Please Enter Card Name")
        }else if (regex.containsMatchIn(cardHolderName)) {
                ValidationResult.Invalid("Should Not Have Numbers")
        }else{
            ValidationResult.Valid
        }
    }

}