package com.gbksoft.emojiedittext.view;

import android.content.Context;
import android.text.Spannable;
import android.util.AttributeSet;
import android.widget.EditText;

import com.gbksoft.emojiedittext.SmartWidgetUtils;

public class SmartEditText extends EditText {

    private boolean isReplacing = false;
    private int positionCursor;

    public SmartEditText(Context context) {
        super(context);
    }

    public SmartEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmartEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (text != "" && text.length() != 0) {
            if (!isReplacing) {
                final Spannable spannable = SmartWidgetUtils.getTextWithImages(getContext(), text);
                isReplacing = true;
                super.setText(spannable, BufferType.SPANNABLE);
            }
        } else {
            super.setText(text, type);
        }
    }

    @Override
    protected void onTextChanged(CharSequence s, int start, int before, int count) {
        if (count != 0) {
            if (!isReplacing) {
                positionCursor = start + count;
                if (s.length() >= 11 &&
                        s.subSequence(s.length() - 11, s.length() - 6).toString()
                                .equals(SmartWidgetUtils.EMOJI_PATTERN_IN)) {
                    setText(s.toString().subSequence(0, s.length() - 11));
                } else {
                    setText(s);
                }
            }
            setSelection(positionCursor);
            isReplacing = false;
        }
    }
}