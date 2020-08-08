package com.jetec.edittextwithfunction;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatEditText;

public class PasswordEditText extends AppCompatEditText {
    public PasswordEditTextChange onEditTextRightButtonFunction;
    /**若xml有使用Icon，則優先使用之*/
    private Drawable rightIcon = getCompoundDrawables()[2];

    public PasswordEditText(Context context) {
        super(context);
        init();
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**將點擊事件傳至外部*/
    public void onItemClick(PasswordEditTextChange onEditTextRightButtonFunction) {
        this.onEditTextRightButtonFunction = onEditTextRightButtonFunction;
    }

    private void init() {
        /**設置Icon圖片，沒有的話則補圖*/
        if (rightIcon == null) {
            rightIcon = getResources().getDrawable(R.drawable.ic_cancel_black_24dp);
        }
        /**設置Icon有效範圍*/
        rightIcon.setBounds(0, 0, rightIcon.getIntrinsicWidth(), rightIcon.getIntrinsicHeight());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /**此處重點是設置EditText內點擊有效之範圍*/
        if (event.getAction() == MotionEvent.ACTION_UP) {
            /**有效範圍起算點*/
            int start = getWidth() - getTotalPaddingRight() + getPaddingRight() - 100;
            /**有效範圍終點*/
            int end = getWidth();
            /**如果使用者有點擊在有效範圍內，則Do something*/
            Boolean available = (event.getX() > start) && (event.getX() < end);
            if (available) {
                onEditTextRightButtonFunction.onItemClick(this);
            }
        }
        return super.onTouchEvent(event);
    }

    /**設置Interface，將類別內的元件使用權限傳至Activity*/
    interface PasswordEditTextChange {
        void onItemClick(PasswordEditText passwordEditText);
    }
}
