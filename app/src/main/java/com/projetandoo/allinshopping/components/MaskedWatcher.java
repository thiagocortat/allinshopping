package com.projetandoo.allinshopping.components;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Pietro Caselani
 */
public class MaskedWatcher implements TextWatcher {
    private String mMask;
    private boolean mIsUpdating, mAcceptOnlyNumbers;
    private EditText mEditText;

    public MaskedWatcher(String mask, EditText editText) {
        if (mask == null)
            throw new RuntimeException("mask parameter from constructor can't be null");
        if (editText == null)
            throw new RuntimeException("editText parameter from constructor can't be null");
        mMask = mask;
        mEditText = editText;
        mIsUpdating = false;
        mEditText.addTextChangedListener(this);
    }

    public boolean acceptOnlyNumbers() {
        return mAcceptOnlyNumbers;
    }

    public void setAcceptOnlyNumbers(boolean acceptOnlyNumbers) {
        mAcceptOnlyNumbers = acceptOnlyNumbers;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mIsUpdating) {
            mIsUpdating = false;
            return;
        }

        Editable editable = mEditText.getText();
        String string = s.toString();
        int end = count + start;
        String inserted = string.substring(start, end);

        if (mAcceptOnlyNumbers && !inserted.matches("[0-9]*") && !string.equalsIgnoreCase("")) {
            mIsUpdating = true;
            editable.delete(start, end);
            //TODO
            // I need a solution to not set text "again". Changes keyboard and selection
            mIsUpdating = true;
            mEditText.setText(mEditText.getText());
            mEditText.setSelection(end - 1);
            return;
        }

        int currentLength = s.length(), maxLength = mMask.length();
        if (currentLength > maxLength) {
            mIsUpdating = true;
            editable.delete(maxLength, currentLength);
            //TODO
            // I need a solution to not set text "again". Changes keyboard and selection
            mIsUpdating = true;
            mEditText.setText(mEditText.getText());
            mEditText.setSelection(maxLength);
            return;
        }

        for (int i = start; i < editable.length(); i++) {
            char c = mMask.charAt(i);
            char editableC = editable.charAt(i);
            if (c != '#' && c != editableC) {
                mIsUpdating = true;
                editable.insert(i, String.valueOf(c));
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
