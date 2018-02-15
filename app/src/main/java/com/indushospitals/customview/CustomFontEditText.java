package com.indushospitals.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by think360 on 14/04/17.
 */

public class CustomFontEditText extends android.support.design.widget.TextInputEditText {

    public CustomFontEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

   /* @Override
    public void setBackgroundTintList(@Nullable ColorStateList tint) {


        super.setBackgroundTintList(tint);
    }*/


    public CustomFontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomFontEditText(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {

            setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/OpenSans-Regular.ttf"));
          //  setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorPrimary));
        }
    }
    @Override
    public void setTypeface(Typeface tf, int style) {
       // tf=Typeface.createFromAsset(getContext().getAssets(), "fonts/century_gothic.ttf");
        super.setTypeface(tf, style);
    }

    @Override
    public void setTypeface(Typeface tf) {
       // tf=Typeface.createFromAsset(getContext().getAssets(), "fonts/century_gothic.ttf");
        super.setTypeface(tf);
    }

}
