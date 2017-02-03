package com.gbksoft.emojiedittext;

public class EmojiObject {

    private int emojiId;
    private String emojiCode;
    private String baseEmojiCode;

    public EmojiObject(int emojiId, String emojiCode) {
        this.emojiId = emojiId;
        this.emojiCode = String.format("[ce=[%s]/]", emojiCode);
        this.baseEmojiCode = emojiCode;
    }

    public int getEmojiId() {
        return emojiId;
    }

    public String getEmojiCode() {
        return emojiCode;
    }

    public String getBaseEmojiCode() {
        return baseEmojiCode;
    }
}