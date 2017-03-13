package com.liuzhao.utils;

import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2月06日0006.
 * okhttp封装类
 */

public class OkhttpManager {
    private static  OkhttpManager okhttpManager;
    private OkHttpClient client;
    private Handler mHandler;

    /**
     * 单例模式
     * @return
     */
    private static OkhttpManager getInstance(){
        if (okhttpManager==null){
            okhttpManager = new OkhttpManager();
        }
        return okhttpManager;
    }
    private OkhttpManager(){
        client = new OkHttpClient();
        mHandler = new Handler(Looper.getMainLooper());
    }
    //内部方法
    private Response p_getSync(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return   response;
    }
    private String p_getSyncAsString(String url) throws IOException {
        return p_getSync(url).body().string();
    }
    private void  p_getAsync(String url, final DataCallBack dataCallBack){
        final  Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                    deliverDataFailure(request,e,dataCallBack);
            }

            @Override
            public void onResponse(Response response)  {
                try {
                    String result = response.body().string();
                    deliverDataSuccess(result,dataCallBack);
                } catch (IOException e) {
                    deliverDataFailure(request,e,dataCallBack);
                }
            }
        });
    }
    private void p_postAsynvc(String url,Map<String,String> params,final DataCallBack dataCallBack){
        RequestBody requestBody = null;
        if (params==null){
            params = new HashMap<String,String>();
        }
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Map.Entry<String,String> entry:params.entrySet()){
            String key = entry.getKey().toString();
            String value= null;
            if (entry.getValue() == null){
                value = "";
            }else {
                value = entry.getValue().toString();
            }
            builder.add(key,value);
        }
        requestBody = builder.build();
        final Request request = new Request.Builder().url(url).post(requestBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                deliverDataFailure(request,e,dataCallBack);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    String result = response.body().string();
                    deliverDataSuccess(result,dataCallBack);
                } catch (IOException e) {
                    deliverDataFailure(request,e,dataCallBack);
                }
            }
        });
    }
    private void p_downloadAsync(final String url, final String destDir, final DataCallBack dataCallBack){
        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                deliverDataFailure(request,e,dataCallBack);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                InputStream inputStream = null;
                FileOutputStream fileOutputStream = null;
                try {
                    File file = new File(destDir,getFileNeam(url));
                    fileOutputStream = new FileOutputStream(file);
                    inputStream = response.body().byteStream();
                    byte[] buffer = new byte[2048];
                    int len = 0;
                    while((len = inputStream.read(buffer))!=-1){
                        fileOutputStream.write(buffer,0,len);
                    }
                    fileOutputStream.flush();
                    deliverDataSuccess(file.getAbsolutePath(),dataCallBack);
                } catch (IOException e) {
                    deliverDataFailure(request,e,dataCallBack);
                }finally {
                    if (fileOutputStream!=null){
                        fileOutputStream.close();

                    }
                    if (inputStream!=null){
                        inputStream.close();
                    }
                }
            }
        });
    }
    //数据分发
    private void deliverDataFailure(final Request request,final IOException e, final DataCallBack dataCallBack){
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (dataCallBack!=null){
                        dataCallBack.requestFailure(request,e);
                    }
                }
            });
    }
    private  void deliverDataSuccess(final String result, final DataCallBack dataCallBack){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (dataCallBack!=null){
                    dataCallBack.requestSuccess(result);
                }
            }
        });
    }

    //外部方法
    public static Response getSync(String url) throws IOException {
        return  getInstance().p_getSync(url);
    }
    public static  String getSyncAsString(String url) throws IOException {
            return getInstance().p_getSyncAsString(url);
    }
    public static void getAsync(String url,DataCallBack dataCallBack){
            getInstance().p_getAsync(url,dataCallBack);
    }
    public  static  void postAsync(String url, Map<String ,String> params,DataCallBack dataCallBack){
            getInstance().p_postAsynvc(url,params,dataCallBack);
    }
    public static void downloadAsync(String url,String destDir,DataCallBack dataCallBack){
            getInstance().p_downloadAsync(url,destDir,dataCallBack);
    }
    //数据回调接口
    public interface DataCallBack{
        void requestFailure(Request request, IOException e);
        void requestSuccess(String result);
    }
    //获取文件路径文件名
    private String getFileNeam (String pUrl){
        int separatorIndex = pUrl.lastIndexOf("/");
        String path = (separatorIndex<0)?pUrl:pUrl.substring(separatorIndex +1 ,pUrl.length());
        return path;
    }
}
