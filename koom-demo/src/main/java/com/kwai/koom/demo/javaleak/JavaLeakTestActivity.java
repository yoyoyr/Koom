package com.kwai.koom.demo.javaleak;

import static com.kwai.koom.base.Monitor_SystemKt.getJavaHeap;
import static com.kwai.koom.base.Monitor_SystemKt.getMemoryInfo;
import static com.kwai.koom.base.Monitor_SystemKt.getProcessStatus;

import java.io.File;
import java.io.IOException;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.kwai.koom.base.MonitorLog;
import com.kwai.koom.demo.MainActivity;
import com.kwai.koom.demo.R;
import com.kwai.koom.demo.javaleak.test.LeakMaker;
import com.kwai.koom.fastdump.ForkJvmHeapDumper;
import com.kwai.koom.javaoom.hprof.ForkStripHeapDumper;
import com.kwai.koom.javaoom.monitor.OOMMonitor;
import com.kwai.koom.javaoom.monitor.utils.SizeUnit;

public class JavaLeakTestActivity extends AppCompatActivity {

    Handler handler = new Handler();

    public static void start(Context context) {
        context.startActivity(new Intent(context, JavaLeakTestActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        request();
        setContentView(R.layout.activity_java_leak_test);
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_make_java_leak:
//                showJavaLeakHint();

                /*
                 * Init OOMMonitor
                 */
                OOMMonitorInitTask.INSTANCE.init(JavaLeakTestActivity.this.getApplication());
                OOMMonitor.INSTANCE.startLoop(true, false, 5_000L);

                /*
                 * Make some leaks for test!
                 */
                LeakMaker.makeLeak(this);
                break;
            case R.id.btn_leak_more:

//                System.out.println("OMMonitor_HeapAnalysisService   freeIn " + SizeUnit.KB.INSTANCE.toMB(getMemoryInfo().freeInKb)
//                        + "  java  " + SizeUnit.BYTE.INSTANCE.toMB(getJavaHeap().used)
//                        + "  vss  " + SizeUnit.KB.INSTANCE.toMB(getProcessStatus().vssKbSize)
//                );
                LeakMaker.leak100MB();
//                System.out.println("OMMonitor_HeapAnalysisService   freeIn " + SizeUnit.KB.INSTANCE.toMB(getMemoryInfo().freeInKb)
//                        + "  java  " + SizeUnit.BYTE.INSTANCE.toMB(getJavaHeap().used)
//                        + "  vss  " + SizeUnit.KB.INSTANCE.toMB(getProcessStatus().vssKbSize)
//                );
                break;

            case R.id.btn_hprof_dump:
//                showHprofDumpHint();

                String path = Environment.getExternalStorageDirectory().getPath() + "/zkoom";
                File file = new File(path);
                file.mkdir();
                //Pull the hprof from the devices.
                //adb shell "run-as com.kwai.koom.demo cat 'files/test.hprof'" > ~/temp/test.hprof
                ForkJvmHeapDumper.getInstance().dump(getCacheDir() + "/test.hprof");
                break;
        }
    }

    private void showJavaLeakHint() {
        findViewById(R.id.btn_make_java_leak).setVisibility(View.INVISIBLE);
        findViewById(R.id.btn_hprof_dump).setVisibility(View.INVISIBLE);
        findViewById(R.id.tv_make_java_leak_hint).setVisibility(View.VISIBLE);
    }

    private void showHprofDumpHint() {
        findViewById(R.id.btn_make_java_leak).setVisibility(View.INVISIBLE);
        findViewById(R.id.btn_hprof_dump).setVisibility(View.INVISIBLE);
        findViewById(R.id.tv_hprof_dump_hint).setVisibility(View.VISIBLE);
    }


    private void request() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
    }
}
