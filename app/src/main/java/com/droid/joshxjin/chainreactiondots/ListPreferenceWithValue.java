package com.droid.joshxjin.chainreactiondots;

import android.content.Context;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Joshua on 2/10/2016.
 */

public class ListPreferenceWithValue extends ListPreference {
    private TextView textValue;

    public ListPreferenceWithValue(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutResource(R.layout.preference_with_value);
    }

    public ListPreferenceWithValue(Context context) {
        super(context);
        setLayoutResource(R.layout.preference_with_value);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        textValue = (TextView) view.findViewById(R.id.preference_value);
        if (textValue != null) {
            textValue.setText(getValue());
        }
    }

}