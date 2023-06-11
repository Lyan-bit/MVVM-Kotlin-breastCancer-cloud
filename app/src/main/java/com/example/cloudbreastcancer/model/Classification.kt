package com.example.cloudbreastcancer.model

import java.util.HashMap

class Classification {

    init {
        ClassificationAllInstances.add(this)
    }

    companion object {
        var ClassificationAllInstances = ArrayList<Classification>()

        fun createClassification(): Classification {
            return Classification()
        }

        var ClassificationIndex = HashMap<String, Classification>()

        fun createByPKClassification(idx: String): Classification {
            var result: Classification? = ClassificationIndex[idx]
            if (result != null) {return result}
            result = Classification ()
            ClassificationIndex.put(idx, result)
            result.id = idx
            return result
        }

        fun killClassification(idx: String) {
            val rem = ClassificationIndex[idx] ?: return
            val remd = ArrayList<Classification>()
            remd.add(rem)
            ClassificationIndex.remove(idx)
            ClassificationAllInstances.removeAll(remd.toSet())
        }
    }

    var id = ""  /* identity */
    var age = 0
    var bmi = 0.0F
    var glucose = 0.0F
    var insulin = 0.0F
    var homa = 0.0F
    var leptin = 0.0F
    var adiponectin = 0.0F
    var resistin = 0.0F
    var mcp = 0.0F
    var outcome = ""  /* derived */
}
