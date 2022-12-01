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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kwai.koom.base.LoggerUtils;

//成员内部类导致内存泄漏
public class InnerMemberLeakMaker extends LeakMaker<Activity> {

    @Override
    public void startLeak(Context context) {
        Intent intent = new Intent(context, InnerMemberLeakMaker.LeakedActivity.class);
        context.startActivity(intent);
    }

    public static class LeakedActivity extends AppCompatActivity {

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            InnerMemberClass innerMemberClass = new InnerMemberClass();
            LoggerUtils.LOGV("InnerMemberClass " + innerMemberClass);
            new ThreadLeak().leak(innerMemberClass);
            this.finish();
        }

        class InnerMemberClass {

        }

    }
}
