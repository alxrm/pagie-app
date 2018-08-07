package common

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.checkSelfPermission

const val DEFAULT_PERMISSIONS_CODE = 101

fun Activity.requestAll(vararg permissions: String) {
  ActivityCompat.requestPermissions(this, permissions, DEFAULT_PERMISSIONS_CODE)
}

fun Fragment.isWriteExternalStorageGranted(): Boolean {
  return activity?.isWriteExternalStorageGranted() ?: false
}

fun Context.isWriteExternalStorageGranted(): Boolean {
  return isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)
}

fun Context.isPermissionGranted(permission: String): Boolean {
  return checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}
