package com.example.cloudbreastcancer.viewModel

import android.content.Context
import android.content.res.AssetManager
import androidx.lifecycle.ViewModel
import com.example.cloudbreastcancer.model.Classification
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel

class ClassificationViewModel (context: Context): ViewModel() {

    private val assetManager: AssetManager = context.assets
    private val crudViewModel = CrudViewModel.getInstance(context)

    companion object {
        private var instance: ClassificationViewModel? = null
        fun getInstance(context: Context): ClassificationViewModel {
            return instance ?: ClassificationViewModel(context)
        }
    }

    //classification
    private fun loadModelFile(assetManager: AssetManager, modelPath: String): ByteBuffer {
        val fileDescriptor = assetManager.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(
            FileChannel.MapMode.READ_ONLY,
            startOffset, declaredLength)
    }

    fun classify(classification: Classification) : String {
        var res : String
        lateinit var tflite : Interpreter
        lateinit var tflitemodel : ByteBuffer

        try{
            tflitemodel = loadModelFile(assetManager, "cancer.tflite")
            tflite = Interpreter(tflitemodel)
        } catch(ex: Exception){
            ex.printStackTrace()
        }

        val inputVal: FloatArray = floatArrayOf(
            ((classification.age - 24) / (89 - 24)).toFloat(),
            ((classification.bmi - 18.37) / (38.5787585 - 18.37)).toFloat(),
            ((classification.glucose - 60) / (201 - 60)).toFloat(),
            ((classification.insulin - 2.432) / (58.46 - 2.432)).toFloat(),
            ((classification.homa - 4.311) / (90.28 - 4.311)).toFloat(),
            ((classification.leptin - 1.6502) / (38.4 - 1.6502)).toFloat(),
            ((classification.adiponectin - 3.21) / (82.1 - 3.21)).toFloat(),
            ((classification.resistin - 45.843) / (1698.44 - 45.843)).toFloat(),
            ((classification.mcp - 45.843) / (1698.44 - 45.843)).toFloat()
        )
        val outputVal: ByteBuffer = ByteBuffer.allocateDirect(8)
        outputVal.order(ByteOrder.nativeOrder())

        tflite.run(inputVal, outputVal)
        outputVal.rewind()

        val result = FloatArray(2)
        for (i in 0..1) {
            result[i] = outputVal.float
        }

        if (result[0]>result[1])
            res = "Result is negative"
        //"[1,0] = ["+ result[0].roundToInt()+","+  result[1].roundToInt()+"]"
        else
            res = "Result is positive"

        classification.outcome = res
        crudViewModel.persistClassification(classification)

        return res
        //"[0,1] = ["+ result[0].roundToInt()+","+  result[1].roundToInt()+"]"
    }
}
