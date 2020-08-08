package com.jetec.edittextwithfunction;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;

public class ClearEditText extends AppCompatEditText implements TextWatcher, View.OnFocusChangeListener {
    public OnEditTextRightButtonFunction onEditTextRightButtonFunction;
    private Drawable rightIcon = getCompoundDrawables()[2];

    public ClearEditText(Context context) {
        super(context);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void onItemClick(OnEditTextRightButtonFunction onEditTextRightButtonFunction) {
        this.onEditTextRightButtonFunction = onEditTextRightButtonFunction;
    }

    private void init() {
        if (rightIcon == null) {
            rightIcon = getResources().getDrawable(R.drawable.ic_cancel_black_24dp);
        }
        rightIcon.setBounds(0, 0, rightIcon.getIntrinsicWidth(), rightIcon.getIntrinsicHeight());
        /**初始不顯示ICON*/
        setDrawVisible(false);
        /**設置取得焦點事件*/
        setOnFocusChangeListener(this);
        /**設置監測輸入事件*/
        addTextChangedListener(this);
    }
    /**設置ICON顯示與消失*/
    private void setDrawVisible(Boolean visible) {
        Drawable right = null;
        if (visible) right = rightIcon;
        setCompoundDrawables(
                getCompoundDrawables()[0],
                getCompoundDrawables()[1],
                right,
                getCompoundDrawables()[3]
        );
    }
    /**如果使用者有輸入的話則顯示ICON*/
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (hasFocus()) setDrawVisible(s.length() != 0);
    }
    /**如果使用者有輸入且取得焦點，則顯示；否則不顯示*/
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus && getText().length() != 0) setDrawVisible(true);
        else setDrawVisible(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            int start = getWidth() - getTotalPaddingRight() + getPaddingRight() - 100;
            int end = getWidth();
            Boolean available = (event.getX() > start) && (event.getX() < end);
            if (available) {
                onEditTextRightButtonFunction.onItemClick(this);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    interface OnEditTextRightButtonFunction{
        void onItemClick(ClearEditText clearEditText);
    }
}
