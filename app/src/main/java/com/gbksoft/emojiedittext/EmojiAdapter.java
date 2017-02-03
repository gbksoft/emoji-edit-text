package com.gbksoft.emojiedittext;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gbksoft.emojiedittext.databinding.EmojiItemBinding;
import com.gbksoft.emojiedittext.interfaces.OnEmojiClickListener;
import com.gbksoft.emojiedittext.interfaces.OnRecyclerItemClickListener;
import com.jakewharton.rxbinding.view.RxView;

import java.util.Collection;
import java.util.List;

public class EmojiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnEmojiClickListener emojiItemClickListener;
    protected OnRecyclerItemClickListener itemClickListener;
    protected static List<EmojiObject> emojiList;

    public EmojiAdapter() {

    }

    public static List<EmojiObject> getEmojiList() {
        return emojiList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EmojiItemHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.emoji_item,
                        parent,
                        false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        EmojiObject emoji = emojiList.get(position);
        EmojiItemBinding layout = ((EmojiItemHolder) holder).emojiItemBinding;
        layout.emojiBtn.setImageResource(emoji.getEmojiId());
        RxView.clicks(layout.emojiBtn)
                .subscribe(v -> {
                    if (emojiItemClickListener != null) {
                        emojiItemClickListener.onEmojiClick(emoji.getEmojiCode());
                    }
                });
    }

    public void setEmojiItemClickListener(OnEmojiClickListener emojiItemClickListener) {
        this.emojiItemClickListener = emojiItemClickListener;
    }

    public EmojiObject removeItem(Object model) {
        int index = emojiList.indexOf(model);
        if (index != -1) {
            return removeItem(index);
        } else {
            return null;
        }
    }

    public EmojiObject removeItem(int position) {
        final EmojiObject model = emojiList.remove(position);
        notifyItemRemoved(position);

        return model;
    }

    public void addItem(EmojiObject model) {
        emojiList.add(model);
        notifyItemInserted(emojiList.size() - 1);
    }

    public void addItem(int position, EmojiObject model) {
        emojiList.add(position, model);
        notifyItemInserted(position);
    }

    public void addItemRanged(Collection<EmojiObject> collection) {
        int size = emojiList.size();
        emojiList.addAll(collection);
        notifyItemRangeInserted(size, emojiList.size() - size);
    }

    public void addItemRangedWONotification(int position, Collection<EmojiObject> collection) {
        emojiList.addAll(position, collection);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final EmojiObject model = emojiList.remove(fromPosition);
        emojiList.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void clear() {
        if (emojiList != null) {
            emojiList.clear();
            notifyDataSetChanged();
        }
    }

    public void setItemClickListener(OnRecyclerItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void callItemClickListener(View v, int position) {
        if (itemClickListener != null && position != -1 && position >= 0) {
            itemClickListener.onRecyclerItemClick(v, position);
        }
    }

    public EmojiObject getItemByPos(int position) {
        if (emojiList == null) {
            throw new NullPointerException("emojiList is null");
        } else if (position >= emojiList.size() && position >= 0) {
            throw new ArrayIndexOutOfBoundsException(
                    "position " + position + " emojiList size " + emojiList.size());
        }

        return emojiList.get(position);
    }

    public static void setEmojiList(List<EmojiObject> list) {
        emojiList = list;
    }

    public int getItemIndex(Object object) {
        return emojiList != null ? emojiList.indexOf(object) : -1;
    }

    @Override
    public int getItemCount() {
        return emojiList != null ? emojiList.size() : 0;
    }

    private class EmojiItemHolder extends RecyclerView.ViewHolder {

        private EmojiItemBinding emojiItemBinding;

        public EmojiItemHolder(EmojiItemBinding emojiItemBinding) {
            super(emojiItemBinding.getRoot());
            this.emojiItemBinding = emojiItemBinding;
        }
    }
}