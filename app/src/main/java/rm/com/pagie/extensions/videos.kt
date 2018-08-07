package rm.com.pagie.extensions

import com.yqritc.scalablevideoview.ScalableVideoView
import common.tryTimber

/**
 * Created by alex
 */

// this is not scalable at all, in a real world I wouldn't be doing stuff like that
fun ScalableVideoView.playFromPath(path: String) {
  tryTimber {
    setDataSource(path)
    isLooping = true
    setVolume(0F, 0F)
    prepare { start() }
  }
}