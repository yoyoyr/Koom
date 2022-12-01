package com.kwai.koom.demo.javaleak.test;

import android.graphics.Bitmap;

public class BitmapLeaker {

    static BitmapLeakMaker bitmapLeakMaker;
    static Bitmap bitmap;
    static long SIZE;
    static long ALLOCATION_SIZE;

    long size;
    long allocation_size;
}
