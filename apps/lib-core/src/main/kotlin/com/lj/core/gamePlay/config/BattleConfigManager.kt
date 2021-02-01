package com.lj.core.gamePlay.config

import io.vertx.core.json.JsonObject
import jodd.util.ClassLoaderUtil
import kt.scaffold.Application
import kt.scaffold.ext.filePathJoin
import java.io.File

enum class BattleConfigType{
    SkillConfigs,
    StatusConfigs
}

object BattleConfigManager {


    fun initialize(){


    }



    fun getSkillConfig(id:String): SkillConfigObject? {
        val path = this.getPath(BattleConfigType.SkillConfigs,id)

        val file = File(path)
        if(!file.exists()) return  null

        var text = file.readText()

        var json = JsonObject(text)
        var configObject = json.mapTo(SkillConfigObject::class.java)

        return configObject
    }

    fun getStatusConfig(name:String): StatusConfigObject? {
        val path = this.getPath(BattleConfigType.StatusConfigs,name)

        val file = File(path)
        if(!file.exists()) return  null

        var text = file.readText()

        var json = JsonObject(text)
        var configObject = json.mapTo(StatusConfigObject::class.java)

        return configObject
    }

    private fun getPath(type:BattleConfigType, id:String):String{
        var prefix = "Skill_"
        if (type == BattleConfigType.StatusConfigs) prefix = "Status_"

        val fileName = "${prefix}${id}.json"

        var fbPath = filePathJoin(Application.appHome, "skills/${type.name}",fileName)
        if (File(fbPath).exists().not()){
            fbPath = ClassLoaderUtil.getDefaultClassLoader().getResource("skills/${type.name}/$fileName")!!.path
        }
        return fbPath
    }

}