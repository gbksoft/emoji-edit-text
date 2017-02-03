package com.gbksoft.emojiedittext.view;

import android.content.Context;
import android.text.Spannable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.gbksoft.emojiedittext.SmartWidgetUtils;

public class SmartTextView extends TextView {

    public SmartTextView(Context context) {
        super(context);
    }

    public SmartTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmartTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        final Spannable spannable = SmartWidgetUtils.getTextWithImages(getContext(), text);

        super.setText(spannable, BufferType.SPANNABLE);
    }
}