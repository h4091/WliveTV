package com.ywl5320.wlivetv;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.ywl5320.wlivetv.Activity.NavagationActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ywl on 15-10-17.
 */
public class MyApplication extends Application{

    private static NavagationActivity navagationActivity;

    public static NavagationActivity getNavagationActivity() {
        return navagationActivity;
    }

    public static void setNavagationActivity(NavagationActivity navagationActivity) {
        MyApplication.navagationActivity = navagationActivity;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(getApplicationContext());
        Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 4);
//        config.memoryCache(new WeakMemoryCache());
        config.memoryCacheExtraOptions(480, 800);
        config.memoryCacheSize(2 * 1024 * 1024);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.diskCacheExtraOptions(480, 800, null);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
//        config.writeDebugLogs(); // Remove for release app
        config.imageDownloader(new BaseImageDownloader(context, 5 * 1000, 10 * 1000));
        config.threadPoolSize(1);
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    /**
     * 记录错误崩溃日志
     */
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {

//            Intent i = new Intent(getApplicationContext(),
//                    NavagationActivity.class);
//            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            getApplicationContext().startActivity(i);
            System.out.println("我崩溃了。。。");
            String info= null;
            ByteArrayOutputStream baos = null;
            PrintStream printStream = null;
            try {
                baos = new ByteArrayOutputStream();
                printStream = new PrintStream(baos);
                ex.printStackTrace(printStream);
                byte[] data = baos.toByteArray();
                info = new String(data);
                data = null;
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                try {
                    if(printStream != null)
                    {
                        printStream.close();
                    }
                    if(baos != null)
                    {
                        baos.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("errorLog:" + info);
            writeErrorLog(info);
            System.exit(10);

        }
    };

    /**
     * 写入错误日志
     * @param info
     */
    private void writeErrorLog(String info)
    {
        info = getCurrentDateString() + "\n" + info;
        File dir = new File(Environment.getExternalStorageDirectory() + "/wlivetv/errorlog/");
        if(!dir.exists())
        {
            dir.mkdirs();
        }
        File file = new File(dir, "errorLog.txt");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            fileOutputStream.write(info.getBytes());
            fileOutputStream.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前时间
     * @return
     */
    private String getCurrentDateString()
    {
        String timeString;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date nowDate = new Date();
        timeString = sdf.format(nowDate);
        return timeString;
    }
}
