package fb
import jodd.util.ClassLoaderUtil
import kt.scaffold.Application
import kt.scaffold.ext.filePathJoin
import java.io.File
import java.nio.ByteBuffer
object FBManager {
    lateinit var heroconfig:heroconfigTB
    lateinit var skillconfig:skillconfigTB
    lateinit var unitconfig:unitconfigTB
    fun initialize(){
        heroconfig = heroconfigTB.getRootAsheroconfigTB(ByteBuffer.wrap(File(getPath("heroconfig.bin")).readBytes()))
        skillconfig = skillconfigTB.getRootAsskillconfigTB(ByteBuffer.wrap(File(getPath("skillconfig.bin")).readBytes()))
        unitconfig = unitconfigTB.getRootAsunitconfigTB(ByteBuffer.wrap(File(getPath("unitconfig.bin")).readBytes()))
    }
    private fun getPath(name:String):String{
        var fbPath = filePathJoin(Application.appHome, "fb",name)
        if (File(fbPath).exists().not()){
            fbPath = ClassLoaderUtil.getDefaultClassLoader().getResource("fb/$name")!!.path
        }
        return fbPath
    }
}
