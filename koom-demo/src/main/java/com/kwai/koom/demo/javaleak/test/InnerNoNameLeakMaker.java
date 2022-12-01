/*
 * Copyright 2020 Kwai, Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author Rui Li <lirui05@kuaishou.com>
 */

package com.kwai.koom.demo.javaleak.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kwai.koom.base.LoggerUtils;

//匿名内部类导致内存泄漏
public class InnerNoNameLeakMaker extends LeakMaker<Activity> {

    @Override
    public void startLeak(Context context) {
        Intent intent = new Intent(context, InnerNoNameLeakMaker.LeakedActivity.class);
        context.startActivity(intent);
    }

    public static class LeakedActivity extends AppCompatActivity {

        Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                LoggerUtils.LOGV("do someting msg.what = " + msg.what + ", target = " + msg.getTarget());
                super.handleMessage(msg);
                if (msg.what == 3) {
                    new ThreadLeak().leak(handler);
                }
            }
        };

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            //匿名内部类导致内存泄漏
            Message msg = Message.obtain();
            msg.what = 3;
            LoggerUtils.LOGV("InnerNoName " + handler);
            handler.sendMessage(msg);
            this.finish();
        }

//        new Handler.Callback() {
//            @Override
//            public boolean handleMessage(@NonNull Message msg) {
//                LoggerUtils.LOGV("do someting msg.what = " + msg.what + ", target = " + msg.getTarget());
//                if(msg.what == 3){
//                    new ThreadLeak().doing(msg);
//                }
//
//                return true;
//            }
//        }

    }

}
