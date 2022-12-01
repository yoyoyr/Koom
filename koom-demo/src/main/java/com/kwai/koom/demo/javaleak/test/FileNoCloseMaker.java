package com.kwai.koom.demo.javaleak.test;

import android.app.Activity;
import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileNoCloseMaker extends LeakMaker<Activity> {

    @Override
    void startLeak(Context context) {
        FileOutputStream inputStream = null;
        try {
            File file = new File(context.getCacheDir() + "/no_close.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            inputStream = new FileOutputStream(file);
            inputStream.write(1);
            inputStream.write(2);
            inputStream.write(3);
            inputStream.write(1);
            inputStream.write(4);
            inputStream.write(5);
            System.out.println("finish write");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
