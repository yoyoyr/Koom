/**
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

public abstract class LeakMaker<T> {
    List<T> uselessObjectList = new ArrayList<>();

    static List<byte[]> mb50Leaks = new ArrayList<>();

    abstract void startLeak(Context context);

    private static List<LeakMaker> leakMakerList = new ArrayList<>();

    public static void makeLeak(Context context) {
        BitmapLeaker.bitmapLeakMaker = new BitmapLeakMaker();
        leakMakerList.add(new ActivityLeakMaker());
        leakMakerList.add(BitmapLeaker.bitmapLeakMaker);
        leakMakerList.add(new CollectionLeakMaker());
        leakMakerList.add(new FragmentLeakMaker());
        leakMakerList.add(new StringLeakMaker());
        leakMakerList.add(new InnerNoNameLeakMaker());
        for (LeakMaker leakMaker : leakMakerList) {
            leakMaker.startLeak(context);
        }
        new FileNoCloseMaker().startLeak(context);
        new InnerLocalLeakMaker().startLeak(context);
        new InnerMemberLeakMaker().startLeak(context);
        new InnerNoNameLeakMaker().startLeak(context);
        new InnerStaticLeakMaker().startLeak(context);

//        for (int i = 0; i < 10; i++) {
//            new Thread(() -> {
//                while (true) {
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }, "leakThread").start();
//        }
    }

    public static void leak100MB() {
        for (int i = 0; i < 1024 * 1024; i++) {
            byte[] bytes = new byte[100];
            mb50Leaks.add(bytes);
        }

    }
}
