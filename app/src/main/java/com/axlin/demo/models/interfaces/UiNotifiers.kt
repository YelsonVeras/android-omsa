package com.axlin.demo.models.interfaces

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast

object UiNotifiers {
    fun toast(text: String, context: Context) =
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()

    fun alert(
        text: String,
        title: String,
        context: Context,
        confirmationHandle: () -> Unit,
        dismissHandle: () -> Unit = { Unit }
    ) =
        AlertDialog
            .Builder(context)
            .setTitle(title)
            .setMessage(text)
            .setPositiveButton(android.R.string.yes) { _, _ -> confirmationHandle.invoke() }
            .setNegativeButton(android.R.string.no) { _, _ -> dismissHandle.invoke() }
            .create().show()

    fun alert(
        text: String,
        context: Context,
        confirmationHandle: () -> Unit,
        dismissHandle: () -> Unit = { Unit }
    ) =
        AlertDialog
            .Builder(context)
            .setMessage(text)
            .setPositiveButton("Si") { _, _ -> confirmationHandle.invoke() }
            .setNegativeButton("No") { _, _ -> dismissHandle.invoke() }
            .create().show()

    fun alert(text: String, context: Context, confirmationHandle: () -> Unit) =
        AlertDialog
            .Builder(context)
            .setMessage(text)
            .setPositiveButton(android.R.string.yes) { _, _ -> confirmationHandle.invoke() }
            .create().show()

    fun alert(text: String, context: Context) = alert(text, context) { Unit }
}