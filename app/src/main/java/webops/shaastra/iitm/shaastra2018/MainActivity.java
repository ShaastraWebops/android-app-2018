package webops.shaastra.iitm.shaastra2018;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.utils.StorageUtils;

import org.json.JSONObject;

import java.io.File;

import webops.shaastra.iitm.shaastra2018.imageCaching.ImageUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*HttpPostRequest.sendparams("name","Shashank");
        HttpPostRequest.sendparams("Age","15");
        JSONObject p3 = HttpPostRequest.sendparams("Email","Shas5566@gmail.com");
        now that p3 is jsonobject of all params so now convert to string
        by p3.tostring()
        Note: give keys uniquely .
        and send it along with string url to execute() of asynctask.
        example: execute(url,p3.toString());
        will do the trick!
        */

        String testImageURL = "http://placehold.it/240x120&text=image1";

        ImageLoader imageLoader = ImageUtil.getImageLoader(this);


        ImageView imview = (ImageView) findViewById(R.id.imView);

        imageLoader.displayImage(testImageURL,imview);
    }

}
