package com.example.showcase.features.shared.data

import android.content.Context
import java.io.File

interface TempFileManager {
    suspend fun saveToTempFile(bytes: ByteArray): File
    fun dispose(file: File)
}

class TempFileManagerImpl(private val context: Context) : TempFileManager {

    override suspend fun saveToTempFile(bytes: ByteArray): File {
        val file = File(context.cacheDir, "temp_${System.currentTimeMillis()}.pdf")
        file.outputStream().use { it.write(bytes) }
        return file
    }

    override fun dispose(file: File) {
        if (file.exists()) {
            file.delete()
        }
    }
}
