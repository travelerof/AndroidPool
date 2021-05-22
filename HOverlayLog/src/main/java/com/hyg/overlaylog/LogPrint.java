package com.hyg.overlaylog;

import android.util.Log;

import com.hyg.hlog.IPrinter;
import com.hyg.overlaylog.log.LogDataManager;
import com.hyg.overlaylog.log.LogModel;

/**
 * @Author hanyonggang
 * @Date 2021/5/22 0022
 * @Desc
 */
class LogPrint implements IPrinter {
    @Override
    public void println(int priority, String tag, String message) {
        LogModel model = new LogModel();
        model.priority = priority;
        model.tag = tag;
        model.message = message;
        LogDataManager.getInstance().add(model);
    }
}
