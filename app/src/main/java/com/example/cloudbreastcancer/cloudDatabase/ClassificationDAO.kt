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
        val line1vals: ArrayList<String> = Ocl.tokeniseCSV(line)
        var classificationx: Classification? = Classification.ClassificationIndex[line1vals[0]]
        if (classificationx == null) {
            classificationx = Classification.createByPKClassification(line1vals[0])
        }
        classificationx.one = line1vals[0].toFloat()
        classificationx.two = line1vals[1].toFloat()
        classificationx.three = line1vals[2].toFloat()
        classificationx.four = line1vals[3].toFloat()
        classificationx.five = line1vals[4].toFloat()
        classificationx.six = line1vals[5].toFloat()
        classificationx.seven = line1vals[6].toFloat()
        classificationx.eight = line1vals[7].toFloat()
        classificationx.nine = line1vals[8].toFloat()
        classificationx.result = line1vals[9]
        classificationx.id = line1vals[10]
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
                classificationx.one = obj.getDouble("one").toFloat()
                classificationx.two = obj.getDouble("two").toFloat()
                classificationx.three = obj.getDouble("three").toFloat()
                classificationx.four = obj.getDouble("four").toFloat()
                classificationx.five = obj.getDouble("five").toFloat()
                classificationx.six = obj.getDouble("six").toFloat()
                classificationx.seven = obj.getDouble("seven").toFloat()
                classificationx.eight = obj.getDouble("eight").toFloat()
                classificationx.nine = obj.getDouble("nine").toFloat()
                classificationx.result = obj.getString("result")
                classificationx.id = obj.getString("id")
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
        val rows: ArrayList<String> = Ocl.parseCSVtable(lines)
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
                result.put("one", x.one)
                result.put("two", x.two)
                result.put("three", x.three)
                result.put("four", x.four)
                result.put("five", x.five)
                result.put("six", x.six)
                result.put("seven", x.seven)
                result.put("eight", x.eight)
                result.put("nine", x.nine)
                result.put("result", x.result)
                result.put("id", x.id)
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
                classificationx.one = (map["one"]as Long?)!!.toLong().toFloat()
                classificationx.two = (map["two"] as Long?)!!.toLong().toFloat()
                classificationx.three = (map["three"]as Long?)!!.toLong().toFloat()
                classificationx.four = (map["four"] as Long?)!!.toLong().toFloat()
                classificationx.five = (map["five"]as Long?)!!.toLong().toFloat()
                classificationx.six = (map["six"] as Long?)!!.toLong().toFloat()
                classificationx.seven = (map["seven"]as Long?)!!.toLong().toFloat()
                classificationx.eight = (map["eight"] as Long?)!!.toLong().toFloat()
                classificationx.nine = (map["nine"]as Long?)!!.toLong().toFloat()
                classificationx.result = map["result"].toString()
                classificationx.id = map["id"].toString()
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
