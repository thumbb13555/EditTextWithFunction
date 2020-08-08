package com.jetec.edittextwithfunction;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**設置旁邊有X的EditText*/
        ClearEditText();

        /**設置旁邊有眼睛圖樣的EditText*/
        passwordIsHideEditText();

        /**設置錯誤提示之EditText*/
        setErrorEditText();
    }

    private void ClearEditText() {
        ClearEditText edCancelEdit = findViewById(R.id.editText_CancelEditText);
        /**設置點擊後右方Icon要做的事*/
        edCancelEdit.onItemClick(new ClearEditText.OnEditTextRightButtonFunction() {
            @Override
            public void onItemClick(ClearEditText clearEditText) {
                clearEditText.setText("");
            }
        });
    }

    private void passwordIsHideEditText() {
        PasswordEditText edPasswordSwitchEdit = findViewById(R.id.editText_PasswordDisplay);
        /**想在程式中設定ICON*/
        edPasswordSwitchEdit.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_baseline_account_circle_24,
                0,
                R.drawable.ic_baseline_remove_red_eye_24,
                0);
        /**設置點擊後右方Icon要做的事*/
        edPasswordSwitchEdit.onItemClick(new PasswordEditText.PasswordEditTextChange() {
            @Override
            public void onItemClick(PasswordEditText passwordEditText) {
                if (isOpen) {
                    isOpen = false;
                    /**設置為輸入顯示狀態*/
                    passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    isOpen = true;
                    /**設置為輸入隱藏狀態*/
                    passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }

    private void setErrorEditText() {
        EditText edError = findViewById(R.id.editText_Error);
        /**設置按鈕觸發*/
        findViewById(R.id.button_TriggerError).setOnClickListener(v -> {
            /**設置使背景色變為紅色*/
            edError.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            /**設置動畫使EditText晃動*/
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake);
            edError.startAnimation(animation);
            /**設置錯誤觸發時會出現的Icon*/
            Drawable drawable = getResources().getDrawable(R.drawable.ic_info_black_24dp);
            drawable.setBounds(0, 0, 70, 70);
            /**設置Error時出現的錯誤訊息提示與ICON*/
            edError.setError("莫須有", drawable);
            /**清除此EditText的焦點*/
            edError.clearFocus();
        });
        /**設置取得焦點後要做的事情*/
        edError.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    edError.getBackground()
                            .setColorFilter(
                                    getResources().getColor(R.color.colorAccent)
                                    , PorterDuff.Mode.SRC_ATOP);
                } else {
                    edError.getBackground()
                            .setColorFilter(
                                    Color.BLACK
                                    , PorterDuff.Mode.SRC_ATOP);
                }
            }
        });
    }
}