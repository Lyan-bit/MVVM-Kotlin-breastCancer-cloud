package com.example.cloudbreastcancer.model

import android.content.Context
import com.example.cloudbreastcancer.viewModel.CrudViewModel
import java.lang.Exception

class ClassificationBean(c: Context) {
    private var model: CrudViewModel = CrudViewModel.getInstance(c)

    private var one : String = ""
    private var done : Float = 0F
    private var two : String = ""
    private var dtwo : Float = 0F
    private var three : String = ""
    private var dthree : Float = 0F
    private var four : String = ""
    private var dfour : Float = 0F
    private var five : String = ""
    private var dfive : Float = 0F
    private var six : String = ""
    private var dsix : Float = 0F
    private var seven : String = ""
    private var dseven : Float = 0F
    private var eight : String = ""
    private var deight : Float = 0F
    private var nine : String = ""
    private var dnine : Float = 0F
    private var result : String = ""
    private var id = ""

    private var errors = ArrayList<String>()

    fun setone(x: String) {
        one = x
    }

    fun settwo(x: String) {
        two = x
    }

    fun setthree(x: String) {
        three = x
    }

    fun setfour(x: String) {
        four = x
    }

    fun setfive(x: String) {
        five = x
    }

    fun setsix(x: String) {
        six = x
    }

    fun setseven(x: String) {
        seven = x
    }

    fun seteight(x: String) {
        eight = x
    }

    fun setnine(x: String) {
        nine = x
    }

    fun setresult(x: String) {
        result = x
    }

    fun setid(x: String) {
        id = x
    }

    fun resetData() {
        one = ""
        two = ""
        three = ""
        four = ""
        five = ""
        six = ""
        seven = ""
        eight = ""
        nine = ""
        result = ""
        id = ""
    }

    fun iscreateClassificationError(): Boolean {
        errors.clear()

        try {
            done = one.toFloat()
//            done = (done - 24) / (89 - 24)
        } catch (e: Exception) {
            errors.add("one is not a float")
        }

        try {
            dtwo = two.toFloat()
//            dtwo = (dtwo - 18.37f) / (38.5787585f - 18.37f)
        } catch (e: Exception) {
            errors.add("two is not a float")
        }

        try {
            dthree = three.toFloat()
//            dthree = (dthree - 60) / (201 - 60)
        } catch (e: Exception) {
            errors.add("three is not a float")
        }

        try {
            dfour = four.toFloat()
//            dfour = (dfour - 2.432f) / (58.46f - 2.432f)
        } catch (e: Exception) {
            errors.add("four is not a float")
        }

        try {
            dfive = five.toFloat()
//            dfive = (dfive - 4.311f) / (90.28f - 4.311f)
        } catch (e: Exception) {
            errors.add("five is not a float")
        }

        try {
            dsix = six.toFloat()
//            dsix = (dsix - 1.6502f) / (38.04f - 1.6502f)
        } catch (e: Exception) {
            errors.add("six is not a float")
        }

        try {
            dseven = seven.toFloat()
//            dseven = (dseven - 3.21f) / (82.1f - 3.21f)
        } catch (e: Exception) {
            errors.add("seven is not a float")
        }

        try {
            deight = eight.toFloat()
//            deight = (deight - 45.843f) / (1698.44f - 45.843f)
        } catch (e: Exception) {
            errors.add("eight is not a float")
        }

        try {
            dnine = nine.toFloat()
//            dnine = (dnine - 45.843f) / (1698.44f - 45.843f)
        } catch (e: Exception) {
            errors.add("nine is not a float")
        }

        return errors.size > 0
    }

    fun islistClassificationError(): Boolean {
        errors.clear()
        return errors.size > 0
    }

    fun errors(): String {
        return errors.toString()
    }

    fun createClassification () {
        //Log.i("data:", done.toString() )
        model.createClassification (ClassificationVO(done, dtwo, dthree, dfour, dfive, dsix, dseven, deight, dnine, result, id))
        resetData()
    }

    fun deleteClassification() {
        model.deleteClassification(id)
        resetData()
    }
}