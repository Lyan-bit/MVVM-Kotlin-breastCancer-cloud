package com.example.cloudbreastcancer.usecase

import android.content.Context
import com.example.cloudbreastcancer.model.Classification
import com.example.cloudbreastcancer.viewModel.ClassificationViewModel
import com.example.cloudbreastcancer.viewModel.CrudViewModel

class DiagnoseBean(c: Context) {
    private var classificationViewModel: ClassificationViewModel = ClassificationViewModel.getInstance(c)
    private var crudViewModel: CrudViewModel = CrudViewModel.getInstance(c)

    private var classification = ""
    private var instanceClassification: Classification? = null

    private var errors = ArrayList<String>()

    fun setclassification(classificationx: String) {
        classification = classificationx
    }

    fun resetData() {
        classification = ""
    }

     fun isdiagnoseerror(): Boolean {
        errors.clear()
        instanceClassification = crudViewModel.getClassificationByPK(classification)
        if (instanceClassification == null) {
            errors.add("classification must be a valid Classification id")
        }
        return errors.size > 0
    }

    fun errors(): String {
        return errors.toString()
    }

    fun diagnose (): String {
        return classificationViewModel.classify(instanceClassification!!)
    }
}