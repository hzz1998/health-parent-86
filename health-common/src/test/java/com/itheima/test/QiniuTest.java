package com.itheima.test;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

public class QiniuTest {

    @Test
    public void delete(){
        Configuration cfg = new Configuration(Region.huadong());

        //七牛给你分配账号密码
        String accessKey = "3Zy-khB5LooHTnLnGONlS8Nl3yoSMLOzUasXKJAf";
        String secretKey = "TbBP5xjKWh5ReslyzCz69pPLJDE95TgN9KybdoLK";
        //自己创建的存储空间名称
        String bucket = "86health";

        //文件名(默认不指定key的情况下，以文件内容的hash值作为文件名)
        String key = "q3.jpg";
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            Response res = bucketManager.delete(bucket, key);
            System.out.println(JSON.toJSONString(res));
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }


    @Test
    public void upload(){
//        华东	Region.region0(), Region.huadong()
//        华北	Region.region1(), Region.huabei()
//        华南	Region.region2(), Region.huanan()
//        北美	Region.regionNa0(), Region.beimei()
//        东南亚	Region.regionAs0(), Region.xinjiapo()
        //在创建存储空间时选择的存储区域
        Configuration cfg = new Configuration(Region.huadong());
        UploadManager uploadManager = new UploadManager(cfg);

        //七牛给你分配账号密码
        String accessKey = "3Zy-khB5LooHTnLnGONlS8Nl3yoSMLOzUasXKJAf";
        String secretKey = "TbBP5xjKWh5ReslyzCz69pPLJDE95TgN9KybdoLK";
        //自己创建的存储空间名称
        String bucket = "86health";

        String localFilePath = "C:\\Users\\ThinkPad\\Pictures\\q5l.jpg";
        //文件名(默认不指定key的情况下，以文件内容的hash值作为文件名)
        String key = "q5l.jpg";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

}
