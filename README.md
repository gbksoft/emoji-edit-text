# emoji-edit-text

## Library with SmartEditText and SmartTextView with emoji support

Usage

    //in your Application.onCreate setup emji list
    ArrayList<EmojiObject> emojiList = new ArrayList<>();
    emojiList.add(new EmojiObject(R.drawable.emoji_image_1, ":)"));
    emojiList.add(new EmojiObject(R.drawable.emoji_image_2, ":("));
    EmojiAdapter.setEmojiList(emojiList);

    //create some listener, on item click on adapter with emojis
    CharSequence text = smartEditText.getText();
    int textLenght = text.length();
    int cursorPosition = smartEditText.getSelectionStart();

    smartEditText.setText(
            text.subSequence(0, cursorPosition) +
            emojiCode +
            text.subSequence(cursorPosition, textLenght));

    smartEditText.setSelection(cursorPosition + 12);



Now you will be able to use this adapter to create emojis inside