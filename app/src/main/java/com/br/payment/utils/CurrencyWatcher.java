package com.br.payment.utils;

import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.style.RelativeSizeSpan;
import android.widget.EditText;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * Created by GuilhermeAlexs on 10/10/2016.
 */
public class CurrencyWatcher implements TextWatcher {
    private final EditText editText;

    public CurrencyWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable editable) {

        if (editText == null)
            return;

        String s = editable.toString();
        editText.removeTextChangedListener(this);
        String cleanString = s.toString().replaceAll("[^0-9]", "");
        BigDecimal parsed = new BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR);
        final String formatted = NumberFormat.getCurrencyInstance().format(parsed);

        editText.setText(formatted);

        Spannable text=(Spannable)editText.getText();
        text.setSpan(new RelativeSizeSpan(0.6f), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setText(text);

        editText.setSelection(formatted.length());
        editText.addTextChangedListener(this);
    }
}
