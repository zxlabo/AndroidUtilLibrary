package com.labo.library.crash

import com.labo.utils.AppGlobals
import java.io.File

object CrashMgr {
    private const val CRASH_DIR_JAVA = "java_crash"
    private const val CRASH_DIR_NATIVE = "native_crash"
    fun init() {
        val javaCrashDir = getJavaCrashDir()
        val nativeCrashDir = getNativeCrashDir()

        CrashHandler.init(javaCrashDir.absolutePath)
    }

    private fun getJavaCrashDir(): File {
        val javaCrashFile = File(AppGlobals.getContext()!!.cacheDir, CRASH_DIR_JAVA)
        if (!javaCrashFile.exists()) {
            javaCrashFile.mkdirs()
        }
        return javaCrashFile
    }


    private fun getNativeCrashDir(): File {
        val nativeCrashFile = File(AppGlobals.getContext()!!.cacheDir, CRASH_DIR_NATIVE)
        if (!nativeCrashFile.exists()) {
            nativeCrashFile.mkdirs()
        }
        return nativeCrashFile
    }

    fun crashFiles(): Array<File> {
        return getJavaCrashDir().listFiles() + getNativeCrashDir().listFiles()
    }

}