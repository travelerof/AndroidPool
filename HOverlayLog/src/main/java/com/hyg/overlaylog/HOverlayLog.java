package com.hyg.overlaylog;

import com.hyg.hlog.HLog;
import com.hyg.hlog.IPrinter;

/**
 * @Author hanyonggang
 * @Date 2021/5/22 0022
 * @Desc
 */
public final class HOverlayLog {

    public static void debug(boolean debug){
        if (!debug) {
            return;
        }
        HLog.debug(debug,new LogPrint());
        OverlayManager.getInstance().init();
    }

}
