package com.example.cloudbreastcancer.cloudDatabase

import com.example.cloudbreastcancer.model.Classification
import com.example.cloudbreastcancer.viewModel.Ocl
import org.json.JSONObject
import java.lang.Exception
import org.json.JSONArray
import kotlin.collections.ArrayList

class ClassificationDAO {

    companion object {

        fun getURL(command: String?, pars: ArrayList<String>, values: ArrayList<String>): String {
            var res = "base url for the data source"
            if (command != null) {
                res += command
            }
            if (pars.isEmpty()) {
                return res
            }
            res = "$res?"
            for (item in pars.indices) {
                val par = pars[item]
                val vals = values[item]
                res = "$res$par=$vals"
                if (item < pars.size - 1) {
                    res = "$res&"
                }
            }
            return res
        }

        fun isCached(id: String?): Boolean {
            Classification.ClassificationIndex[id] ?: return false
            return true
        }

        fun getCachedInstance(id: String): Classification? {
            return Classification.ClassificationIndex[id]
        }

    fun parseCSV(line: String?): Classification? {
        if (line == null) {
            return null
        }
        val line1vals: List<String> = Ocl.tokeniseCSV(line)
        var classificationx: Classification? = Classification.ClassificationIndex[line1vals[0]]
        if (classificationx == null) {
            classificationx = Classification.createByPKClassification(line1vals[0])
        }
        classificationx.id = line1vals[0]
        classificationx.age = line1vals[1].toInt()
        classificationx.bmi = line1vals[2].toFloat()
        classificationx.glucose = line1vals[3].toFloat()
        classificationx.insulin = line1vals[4].toFloat()
        classificationx.homa = line1vals[5].toFloat()
        classificationx.leptin = line1vals[6].toFloat()
        classificationx.adiponectin = line1vals[7].toFloat()
        classificationx.resistin = line1vals[8].toFloat()
        classificationx.mcp = line1vals[9].toFloat()
        classificationx.outcome = line1vals[10]
        return classificationx
    }


        fun parseJSON(obj: JSONObject?): Classification? {
            return if (obj == null) {
                null
            } else try {
                val id = obj.getString("id")
                var classificationx: Classification? = Classification.ClassificationIndex[id]
                if (classificationx == null) {
                    classificationx = Classification.createByPKClassification(id)
                }
                classificationx.id = obj.getString("id")
                classificationx.age = obj.getDouble("age").toInt()
                classificationx.bmi = obj.getDouble("bmi").toFloat()
                classificationx.glucose = obj.getDouble("glucose").toFloat()
                classificationx.insulin = obj.getDouble("insulin").toFloat()
                classificationx.homa = obj.getDouble("homa").toFloat()
                classificationx.leptin = obj.getDouble("leptin").toFloat()
                classificationx.adiponectin = obj.getDouble("adiponectin").toFloat()
                classificationx.resistin = obj.getDouble("resistin").toFloat()
                classificationx.mcp = obj.getDouble("mcp").toFloat()
                classificationx.outcome = obj.getString("outcome")
                classificationx
            } catch (e: Exception) {
                null
            }
        }

    fun makeFromCSV(lines: String?): ArrayList<Classification> {
        val result: ArrayList<Classification> = ArrayList<Classification>()
        if (lines == null) {
            return result
        }
        val rows: List<String> = Ocl.parseCSVtable(lines)
        for (item in rows.indices) {
            val row = rows[item]
            if (row == null || row.trim { it <= ' ' }.isEmpty()) {
                //trim
            } else {
                val x: Classification? = parseCSV(row)
                if (x != null) {
                    result.add(x)
                }
            }
        }
        return result
    }


        fun parseJSONArray(jarray: JSONArray?): ArrayList<Classification>? {
            if (jarray == null) {
                return null
            }
            val res: ArrayList<Classification> = ArrayList<Classification>()
            val len = jarray.length()
            for (i in 0 until len) {
                try {
                    val x = jarray.getJSONObject(i)
                    if (x != null) {
                        val y: Classification? = parseJSON(x)
                        if (y != null) {
                            res.add(y)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return res
        }


        fun writeJSON(x: Classification): JSONObject? {
            val result = JSONObject()
            try {
                result.put("id", x.id)
                result.put("age", x.age)
                result.put("bmi", x.bmi)
                result.put("glucose", x.glucose)
                result.put("insulin", x.insulin)
                result.put("homa", x.homa)
                result.put("leptin", x.leptin)
                result.put("adiponectin", x.adiponectin)
                result.put("resistin", x.resistin)
                result.put("mcp", x.mcp)
                result.put("outcome", x.outcome)
            } catch (e: Exception) {
                return null
            }
            return result
        }


        fun parseRaw(obj: Any?): Classification? {
             if (obj == null) {
                 return null
            }
            try {
                val map = obj as HashMap<String, Object>
                val id: String = map["id"].toString()
                var classificationx: Classification? = Classification.ClassificationIndex[id]
                if (classificationx == null) {
                    classificationx = Classification.createByPKClassification(id)
                }
                classificationx.id = map["id"].toString()
                classificationx.age = (map["age"] as Long?)!!.toLong().toInt()
                classificationx.bmi = (map["bmi"]as Long?)!!.toLong().toFloat()
                classificationx.glucose = (map["glucose"] as Long?)!!.toLong().toFloat()
                classificationx.insulin = (map["insulin"]as Long?)!!.toLong().toFloat()
                classificationx.homa = (map["homa"] as Long?)!!.toLong().toFloat()
                classificationx.leptin = (map["leptin"]as Long?)!!.toLong().toFloat()
                classificationx.adiponectin = (map["adiponectin"] as Long?)!!.toLong().toFloat()
                classificationx.resistin = (map["resistin"]as Long?)!!.toLong().toFloat()
                classificationx.mcp = (map["mcp"]as Long?)!!.toLong().toFloat()
                classificationx.outcome = map["outcome"].toString()
                return classificationx
            } catch (e: Exception) {
                return null
            }
        }

        fun writeJSONArray(es: ArrayList<Classification>): JSONArray {
            val result = JSONArray()
            for (i in 0 until es.size) {
                val ex: Classification = es[i]
                val jx = writeJSON(ex)
                if (jx == null) {
                    //null
                } else {
                    try {
                        result.put(jx)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            return result
        }
    }
}
