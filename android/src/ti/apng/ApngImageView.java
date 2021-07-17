package ti.apng;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import oupson.apng.decoder.*;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.TiC;
import org.appcelerator.titanium.TiFileProxy;
import org.appcelerator.titanium.io.TiBaseFile;
import org.appcelerator.titanium.io.TiFileFactory;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.util.TiUrl;
import org.appcelerator.titanium.view.TiDrawableReference;
import org.appcelerator.titanium.view.TiUIView;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


public class ApngImageView extends TiUIView {
    private final ImageView imageView;
    private final RelativeLayout layout;
    private AnimationDrawable aniDrawable = null;
    public boolean isPlaying = false;

    public ApngImageView(TiViewProxy proxy) {
        super(proxy);
        this.layout = new RelativeLayout(proxy.getActivity());
        this.imageView = new ImageView(proxy.getActivity());

        this.layout.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));

        this.imageView.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));

        this.layout.addView(this.imageView);

        setNativeView(this.layout);
    }

    @Override
    public void processProperties(KrollDict properties) {
        super.processProperties(properties);
        if (properties.containsKeyAndNotNull(TiC.PROPERTY_IMAGE)){
            processProperty(TiC.PROPERTY_IMAGE, properties.get(TiC.PROPERTY_IMAGE));
        }
    }

    @Override
    public void propertyChanged(String key, Object oldValue, Object newValue, KrollProxy proxy) {
        super.propertyChanged(key, oldValue, newValue, proxy);
        this.processProperty(key, newValue);
    }

    public void processProperty(String name, Object value)  {
        Context context = TiApplication.getAppCurrentActivity();


        if (name == TiC.PROPERTY_IMAGE) {
            if (value instanceof TiFileProxy) {
                TiBaseFile file = ((TiFileProxy) value).getBaseFile();
                ApngDecoder.Callback callback = new ApngDecoder.Callback() {
                    @Override
                    public void onSuccess(@NotNull Drawable drawable) {}

                    @Override
                    public void onError(@NotNull Exception e) {
                        Log.e("---", "error " + e.getMessage());
                    }
                };
                try {
                    InputStream iostream = file.getInputStream();
                    Drawable draw = ApngDecoder.decodeApng(context,iostream, 1.0f);
                    imageView.setImageDrawable(draw);
                    aniDrawable = (AnimationDrawable) draw;
                    aniDrawable.start();
                    isPlaying = true;
                } catch (IOException e) {
                    Log.e(TiApngModule.LCAT, "File error: " + e.getMessage());
                }
            } else if (value instanceof String){
                try {
                    URL img = new URL((String) value);
                    ApngDecoder.decodeApngAsyncInto(context, img, imageView);
                } catch ( MalformedURLException e) {
                    Log.e(TiApngModule.LCAT, "URL error: " + e.getMessage());
                }
            }
        }
    }

    public void stop(){
        if (aniDrawable != null){
            aniDrawable.stop();
            isPlaying = false;
        }
    }

    public void start(){
        if (aniDrawable != null){
            aniDrawable.start();
            isPlaying = true;
        }
    }
}
