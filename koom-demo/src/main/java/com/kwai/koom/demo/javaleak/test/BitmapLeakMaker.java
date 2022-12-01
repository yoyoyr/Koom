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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.kwai.koom.demo.R;

public class BitmapLeakMaker extends LeakMaker<Bitmap> {

    BitmapLeaker bitmapLeaker = new BitmapLeaker();

    @Override
    public void startLeak(Context context) {

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.bg_mine_nav);
        uselessObjectList.add(bitmap);
        BitmapLeaker.ALLOCATION_SIZE = bitmap.getAllocationByteCount();
        BitmapLeaker.SIZE = bitmap.getByteCount();
        bitmapLeaker.bitmap = bitmap;
        bitmapLeaker.allocation_size = BitmapLeaker.ALLOCATION_SIZE;
        bitmapLeaker.size = BitmapLeaker.SIZE;
        System.out.println("allocation_size11 = " + BitmapLeaker.ALLOCATION_SIZE + ",size = " + BitmapLeaker.SIZE);
    }
}
