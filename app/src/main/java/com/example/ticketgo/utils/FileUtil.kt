package com.example.ticketgo.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream


class FileUtil {

    companion object {

        private const val TAG: String = "FileUtil"

        private fun createBitmap(
            view: View,
            activity: Activity,
        ): Bitmap {
            val displayMetrics = DisplayMetrics()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                activity.display?.getRealMetrics(displayMetrics)
            } else {
                @Suppress("DEPRECATION")
                val display = activity.windowManager.defaultDisplay
                @Suppress("DEPRECATION")
                display.getMetrics(displayMetrics)
            }
            view.measure(
                View.MeasureSpec.makeMeasureSpec(
                    displayMetrics.widthPixels, View.MeasureSpec.EXACTLY
                ),
                View.MeasureSpec.makeMeasureSpec(
                    displayMetrics.heightPixels, View.MeasureSpec.EXACTLY
                )
            )
            view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
            val bitmap = Bitmap.createBitmap(
                view.measuredWidth,
                view.measuredHeight, Bitmap.Config.ARGB_8888
            )

            val canvas = Canvas(bitmap)
            view.draw(canvas)
            return Bitmap.createScaledBitmap(bitmap, 595, 842, true)
        }


        fun createPdf(
            view: View,
            activity: Activity,
            fileName: String,
            context: Context
        ) {
            val bitmap = createBitmap(view, activity)
            convertBitmapToPdf(bitmap, fileName, context)
        }


        private fun convertBitmapToPdf(bitmap: Bitmap, fileName: String, context: Context) {
            val pdfDocument = PdfDocument()
            val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
            val page = pdfDocument.startPage(pageInfo)
            page.canvas.drawBitmap(bitmap, 0F, 0F, null)
            pdfDocument.finishPage(page)

            val path = getFilePath(fileName)

            pdfDocument.writeTo(FileOutputStream(path))
            pdfDocument.close()
            renderPdf(context, path)
        }

        private fun getFilePath(fileName: String): File {
            val appPath =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

            val myFile = File(appPath, "$fileName.pdf")
            myFile.createNewFile()
            return myFile
        }

        private fun renderPdf(context: Context, filePath: File) {
            val uri = FileProvider.getUriForFile(
                context,
                context.applicationContext.packageName + ".provider",
                filePath
            )
            val intent = Intent(Intent.ACTION_VIEW)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setDataAndType(uri, "application/pdf")

            try {
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Log.e(TAG, "renderPdf: ", e)
            }
        }

    }
}