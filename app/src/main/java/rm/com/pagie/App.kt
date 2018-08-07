@file:Suppress("unused")

package rm.com.pagie

import android.app.Application
import common.isDebugBuild
import io.paperdb.Paper
import timber.log.Timber

/**
 * Created by alex
 */

class App : Application() {

  override fun onCreate() {
    super.onCreate()

    // anti-pattern, i know
    Paper.init(applicationContext)

    if (isDebugBuild) {
      Timber.plant(Timber.DebugTree())
    }
  }
}