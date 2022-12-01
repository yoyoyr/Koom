/*
 * Copyright (c) 2021. Kwai, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author KOOM Team
 *
 */
package com.kwai.koom.base

object MonitorLog {
    @JvmStatic
    fun v(tag: String, msg: String): Int {
        return MonitorManager.commonConfig.log.v(tag, msg)
    }

    @JvmStatic
    fun i(tag: String, msg: String) {
        LoggerUtils.LOGI(tag, msg)
    }

    @JvmStatic
    fun d(tag: String, msg: String) {
        LoggerUtils.LOGD(tag, msg)
    }

    @JvmStatic
    fun w(tag: String, msg: String) {
        LoggerUtils.LOGW(tag, msg)
    }

    @JvmStatic
    fun e(tag: String, msg: String) {
        LoggerUtils.LOGE(tag, msg)
    }

    @JvmStatic
    fun v(tag: String, msg: String, syncToLogger: Boolean): Int {
        if (syncToLogger) MonitorLogger.addCustomStatEvent(tag, msg)
        this.v(tag, msg)
        return 1
    }

    @JvmStatic
    fun i(tag: String, msg: String, syncToLogger: Boolean): Int {
        if (syncToLogger) MonitorLogger.addCustomStatEvent(tag, msg)
        this.i(tag, msg)
        return 1
    }

    @JvmStatic
    fun d(tag: String, msg: String, syncToLogger: Boolean): Int {
        if (syncToLogger) MonitorLogger.addCustomStatEvent(tag, msg)
        this.d(tag, msg)
        return 1
    }

    @JvmStatic
    fun w(tag: String, msg: String, syncToLogger: Boolean): Int {
        if (syncToLogger) MonitorLogger.addCustomStatEvent(tag, msg)
        this.w(tag, msg)
        return 1
    }

    @JvmStatic
    fun e(tag: String, msg: String, syncToLogger: Boolean): Int {
        if (syncToLogger) MonitorLogger.addCustomStatEvent(tag, msg)
        this.e(tag, msg)
        return 1
    }
}

interface Log {
    fun v(tag: String, msg: String) = runIfDebug { android.util.Log.v(tag, msg) }

    fun i(tag: String, msg: String) = runIfDebug { android.util.Log.i(tag, msg) }

    fun d(tag: String, msg: String) = runIfDebug { android.util.Log.d(tag, msg) }

    fun w(tag: String, msg: String) = runIfDebug { android.util.Log.w(tag, msg) }

    fun e(tag: String, msg: String) = runIfDebug { android.util.Log.e(tag, msg) }
}

internal inline fun runIfDebug(block: () -> Int): Int {
    if (MonitorBuildConfig.DEBUG) {
        return block()
    }

    return -1
}