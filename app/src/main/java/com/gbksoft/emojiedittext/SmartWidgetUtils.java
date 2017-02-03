package com.gbksoft.emojiedittext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.style.ImageSpan;

import com.gbksoft.emojiedittext.EmojiObject;
import com.gbksoft.emojiedittext.EmojiAdapter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmartWidgetUtils {

    public static final String EMOJI_PATH = "drawable";
    public static final String EMOJI_PATTERN_IN = "[ce=[";
    public static final String EMOJI_PATTERN = "\\Q" + EMOJI_PATTERN_IN + "\\E([a-zA-Z0-9_]+?)\\Q]/]\\E";

    public static Spannable getTextWithImages(Context context, CharSequence text) {
        final Spannable spannable = Spannable.Factory.getInstance().newSpannable(text);
        addImages(context, spannable);

        return spannable;
    }

    private static boolean addImages(Context context, Spannable spannable) {
        final Pattern refImg = Pattern.compile(EMOJI_PATTERN);
        boolean hasChanges = false;

        final Matcher matcher = refImg.matcher(spannable);

        while (matcher.find()) {
            boolean set = true;

            for (ImageSpan span : spannable.getSpans(matcher.start(), matcher.end(), ImageSpan.class)) {
                if (spannable.getSpanStart(span) >= matcher.start()
                        && spannable.getSpanEnd(span) <= matcher.end()) {
                    spannable.removeSpan(span);
                } else {
                    set = false;
                    break;
                }
            }

            final String resName =
                    spannable.subSequence(matcher.start(1), matcher.end(1)).toString().trim();
            List<EmojiObject> emojis = EmojiAdapter.getEmojiList();
            int emojiId = -1;
            for (int i = 0; i < emojis.size(); i++) {
                if(emojis.get(i).getBaseEmojiCode().equals(resName)) {
                    emojiId = emojis.get(i).getEmojiId();
                }
            }
            if(emojiId == -1) {
                return false;
            }

            int emojiHeight = ContextCompat.getDrawable(context, emojiId).getIntrinsicHeight();
            int emojiWidth = ContextCompat.getDrawable(context, emojiId).getIntrinsicWidth();

            if (set) {
                hasChanges = true;
                spannable.setSpan(makeImageSpan(context, emojiId, emojiHeight, emojiWidth),
                        matcher.start(),
                        matcher.end(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }
        }

        return hasChanges;
    }

    private static ImageSpan makeImageSpan(Context context, int emojiId,
                                           int emojiHeight, int emojiWidth) {
        final Drawable drawable = ContextCompat.getDrawable(context, emojiId);
        drawable.setBounds(0, 0, emojiWidth, emojiHeight);

        return new ImageSpan(drawable, null);
    }

}
