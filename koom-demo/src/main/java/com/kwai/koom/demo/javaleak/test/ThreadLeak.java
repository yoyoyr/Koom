package com.kwai.koom.demo.javaleak.test;

import android.os.Message;

import com.kwai.koom.base.Logger;
import com.kwai.koom.base.LoggerUtils;

public class ThreadLeak {

    public void leak(Object o) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //这里不使用o不会内存泄露
                LoggerUtils.LOGV("leak " + o);
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "leak activity").start();
    }

    public void doing(Message msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    LoggerUtils.LOGV("do someting msg.what = " + msg.what + ", target = " + msg.getTarget());
                }
            }
        }, "leak doing").start();
    }
}
