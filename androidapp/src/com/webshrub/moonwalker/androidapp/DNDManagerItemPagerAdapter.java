package com.webshrub.moonwalker.androidapp;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/20/13
 * Time: 9:50 PM
 */
class DNDManagerItemPagerAdapter extends PagerAdapter {
    private Context context;
    private List<DNDManagerItem> dndManagerItems;

    public DNDManagerItemPagerAdapter(Context context) {
        this.context = context;
        DNDManagerItemManager dndManagerItemManager = new DNDManagerItemManager(context);
        dndManagerItems = dndManagerItemManager.getListing(DNDManagerItemType.CALL);
        dndManagerItems.addAll(dndManagerItemManager.getListing(DNDManagerItemType.SMS));
        if (dndManagerItems.size() > 0) {
            Collections.sort(dndManagerItems);
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void finishUpdate(View container) {
    }

    @Override
    public int getCount() {
        return dndManagerItems.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        DNDManagerItem dndManagerItem = dndManagerItems.get(position);
        View layout = ((Activity) context).getLayoutInflater().inflate(R.layout.message_item_details, view, false);
        TextView from = (TextView) layout.findViewById(R.id.from);
        from.setText(dndManagerItem.getCachedName() + " (" + dndManagerItem.getNumber() + ")");
        EditText shortDescription = (EditText) layout.findViewById(R.id.shortDescription);
        shortDescription.setText(dndManagerItem.getText());
        EditText messageText = (EditText) layout.findViewById(R.id.messageText);
        messageText.setText(DNDManagerUtil.getMessageText(context, dndManagerItem.getNumber(), dndManagerItem.getDateTime(), dndManagerItem.getText()));
        messageText.setTag(dndManagerItem.getDateTime());
        shortDescription.addTextChangedListener(new DNDManagerMessageTextWatcher(context, dndManagerItem, shortDescription, messageText));
        if (dndManagerItem.getItemType().equals(DNDManagerItemType.CALL)) {
            shortDescription.setSelection(dndManagerItem.getText().length());
            shortDescription.extendSelection(0);
            shortDescription.setOnClickListener(new DNDManagerDescriptionOnClickListener(shortDescription));
        }
        view.addView(layout, 0);
        return layout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View container) {
    }

    public DNDManagerItem removeDNDManagerItem(int position) {
        return dndManagerItems.remove(position);
    }

    public DNDManagerItem getDNDManagerItem(int position) {
        return dndManagerItems.get(position);
    }

    private static class DNDManagerMessageTextWatcher implements TextWatcher {
        private Context context;
        private DNDManagerItem dndManagerItem;
        private EditText shortDescription;
        private EditText messageText;

        public DNDManagerMessageTextWatcher(Context context, DNDManagerItem dndManagerItem, EditText shortDescription, EditText messageText) {
            this.context = context;
            this.dndManagerItem = dndManagerItem;
            this.shortDescription = shortDescription;
            this.messageText = messageText;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String message = DNDManagerUtil.stripText(shortDescription.getText().toString());
            message = DNDManagerUtil.getMessageText(context, dndManagerItem.getNumber(), dndManagerItem.getDateTime(), message);
            messageText.setText(message);
        }
    }

    private static class DNDManagerDescriptionOnClickListener implements View.OnClickListener {
        private EditText shortDescription;
        private int textEraseCount;

        public DNDManagerDescriptionOnClickListener(EditText shortDescription) {
            this.shortDescription = shortDescription;
            this.textEraseCount = 0;
        }

        @Override
        public void onClick(View view) {
            if (textEraseCount == 0) {
                shortDescription.setText("");
                textEraseCount = 1;
            }
        }
    }
}
