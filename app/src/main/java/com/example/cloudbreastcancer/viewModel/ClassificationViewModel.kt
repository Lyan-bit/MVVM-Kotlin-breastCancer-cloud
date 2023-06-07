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

        val done: Float = (classification.one - 24) / (89 - 24)
        val dtwo = (classification.two - 18.37f) / (38.5787585f - 18.37f)
        val dthree = (classification.three - 60) / (201 - 60)
        val dfour = (classification.four - 2.432f) / (58.46f - 2.432f)
        val dfive = (classification.five - 4.311f) / (90.28f - 4.311f)
        val dsix = (classification.six - 1.6502f) / (38.04f - 1.6502f)
        val dseven = (classification.seven - 3.21f) / (82.1f - 3.21f)
        val deight = (classification.eight - 45.843f) / (1698.44f - 45.843f)
        val dnine = (classification.nine - 45.843f) / (1698.44f - 45.843f)

        val inputVal: FloatArray = floatArrayOf(done, dtwo, dthree, dfour, dfive, dsix, dseven,deight, dnine)
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

        classification.result = res
        crudViewModel.persistClassification(classification)

        return res
        //"[0,1] = ["+ result[0].roundToInt()+","+  result[1].roundToInt()+"]"
    }
}
