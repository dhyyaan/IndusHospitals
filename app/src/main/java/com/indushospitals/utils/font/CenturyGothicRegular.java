package com.indushospitals.utils.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by user on 12/9/2015.
 */
public class CenturyGothicRegular extends android.support.v7.widget.AppCompatTextView {

    public CenturyGothicRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CenturyGothicRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CenturyGothicRegular(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/OpenSans-Regular.ttf");
            setTypeface(tf);
        }
    }
}
