package com.example.xxnan.crasher.picpreview.imageUtil;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;

import com.example.xxnan.crasher.picpreview.bean.ImageBean;
import com.example.xxnan.crasher.picpreview.bean.ImageSize;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;


/**
 * Created by Administrator on 2016/8/29.
 */
public class ImageLoader {
    private static ImageLoader imageLoader;
    private static final int DEAFULT_MSG = 0X110;
    private static final int DEAFULT_THREAD_COUNT = 3;
    private LruCache<String, Bitmap> lruCache;
    private ExecutorService mThreadPool;
    private LinkedList<Runnable> taskQuene;
    private Semaphore poolHandlerSemaphore = new Semaphore(0);
    private Handler mUiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ImageBean bean = (ImageBean) msg.obj;
            ImageView imageView = bean.imageView;
            String path = bean.path;
            Bitmap bitmap = bean.bitmap;
            if (path.equals(imageView.getTag().toString()))
                imageView.setImageBitmap(bitmap);
        }
    };
    private Thread mThreadPoolThread;
    private Handler poolHandler;

    private ImageLoader() {
        mThreadPoolThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                poolHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        if (msg.what == 0X110) {
                            mThreadPool.execute(getTask());
                        }
                    }
                };
                poolHandlerSemaphore.release();
                Looper.loop();
            }
        });
        mThreadPoolThread.start();
        int maxCache = (int) (Runtime.getRuntime().maxMemory() / 8);
        lruCache = new LruCache<String, Bitmap>(maxCache) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
        mThreadPool = Executors.newFixedThreadPool(DEAFULT_THREAD_COUNT);
        taskQuene = new LinkedList<Runnable>();
    }

    private Runnable getTask() {
        return taskQuene.removeLast();
    }

    /**
     * 缓存图片
     *
     * @param key
     * @param bitmap
     */
    public void putCache(String key, Bitmap bitmap) {
        lruCache.put(key, bitmap);
    }

    /**
     * 取出缓存图片
     *
     * @param key
     * @return
     */
    public Bitmap getCache(String key) {
        return lruCache.get(key);
    }

    public static ImageLoader getInstance() {
        if (imageLoader == null) {
            synchronized (ImageLoader.class) {
                if (imageLoader == null)
                    imageLoader = new ImageLoader();
            }
        }
        return imageLoader;
    }

    /**
     * 加载图片
     *
     * @param path
     * @param imageView
     */
    public void loadImage(String path, final ImageView imageView) {
        imageView.setTag(path);
        Bitmap bitmap = lruCache.get(path);
        if (bitmap != null) {
            updateUi(path, bitmap, imageView);
        } else {
            //开启线程加载图片
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    ImageSize imageSize = Util.getImageSize(imageView);
                }
            };
            addTask(runnable);
        }
    }

    private void addTask(Runnable runnable) {
        try {
            if (poolHandler == null)
                poolHandlerSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        taskQuene.add(runnable);
        Message msg = poolHandler.obtainMessage();
        msg.what = DEAFULT_MSG;
        poolHandler.sendMessage(msg);
    }

    private void updateUi(String path, Bitmap bitmap, ImageView imageView) {
        Message msg = mUiHandler.obtainMessage();
        ImageBean bean = new ImageBean();
        bean.bitmap = bitmap;
        bean.path = path;
        bean.imageView = imageView;
        msg.obj = bean;
        mUiHandler.sendMessage(msg);
    }


}
