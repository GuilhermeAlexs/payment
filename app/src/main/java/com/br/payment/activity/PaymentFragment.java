package com.br.payment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.br.payment.R;
import com.br.payment.utils.CurrencyWatcher;
import com.br.payment.adapters.PaymentAdapter;
import com.br.payment.model.Payment;
import com.br.payment.model.PaymentType;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GuilhermeAlexs on 10/10/2016.
 */
public class PaymentFragment extends AppCompatActivity implements View.OnClickListener{
    public final static String TOTAL_PAYMENT = "total_payment";

    private RecyclerView mListViewPayment;
    private RecyclerView.Adapter mAdapterPayment;
    private RecyclerView.LayoutManager mLayoutManagerPayment;
    private List<Payment> mPaymentList;

    private Button mBtnCash;
    private Button mBtnCard;
    private TextView mBtnCancel;
    private TextView mBtnOk;
    private TextView mLblTotalPayment;
    private TextView mLblRemainingPayment;
    private EditText mEditPayment;

    private double totalPayment = 1245;
    private double remainingPayment = totalPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.payment_fragment);

        Intent params = getIntent();
        if (params != null) {
            totalPayment = params.getDoubleExtra(TOTAL_PAYMENT,10);
            remainingPayment = totalPayment;
        }

        mListViewPayment = (RecyclerView) findViewById(R.id.listPayment);
        mBtnCash = (Button) findViewById(R.id.btnCash);
        mBtnCard = (Button) findViewById(R.id.btnCard);
        mBtnCancel = (TextView) findViewById(R.id.btnCancel);
        mBtnOk = (TextView) findViewById(R.id.btnOK);
        mLblTotalPayment = (TextView) findViewById(R.id.lblTotalPayment);
        mLblRemainingPayment = (TextView) findViewById(R.id.lblRemainingPayment);
        mEditPayment = (EditText) findViewById(R.id.editPayment);

        mLblTotalPayment.setText(NumberFormat.getCurrencyInstance().format(totalPayment));

        mBtnCash.setOnClickListener(this);
        mBtnCard.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
        mBtnOk.setOnClickListener(this);
        mEditPayment.addTextChangedListener(new CurrencyWatcher(mEditPayment));

        mBtnOk.setEnabled(false);

        mLayoutManagerPayment = new LinearLayoutManager(this);
        mListViewPayment.setLayoutManager(mLayoutManagerPayment);

        mPaymentList = new ArrayList<>();

        mAdapterPayment = new PaymentAdapter(mPaymentList);
        mListViewPayment.setAdapter(mAdapterPayment);

        updatePaymentView();
    }

    private boolean needToPay(){
        if(remainingPayment <= 0){
            Toast.makeText(this,"Ops! O valor total já foi pago!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void doPayment(PaymentType type) {
        if(!needToPay())
            return;

        try {
            String rawAmount = mEditPayment.getText().toString();
            double toPay = NumberFormat.getCurrencyInstance().parse(rawAmount).doubleValue();

            if(type == PaymentType.CARD) {
                if (toPay <= remainingPayment) {
                    remainingPayment = remainingPayment - toPay;
                }else {
                    Toast.makeText(this, "Cobre apenas o valor total no cartão!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }else{
                remainingPayment = remainingPayment - toPay;
            }

            Payment payment = new Payment(toPay, type);
            mPaymentList.add(payment);

            updatePaymentView();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void updatePaymentView(){
        mLblRemainingPayment.setText(NumberFormat.getCurrencyInstance().format(remainingPayment));

        if(remainingPayment <= 0) {
            remainingPayment = 0;
            mBtnOk.setEnabled(true);
        }

        SpannableString text = new SpannableString(mLblRemainingPayment.getText());

        if(text.charAt(0) == '-')
            text.setSpan(new RelativeSizeSpan(0.6f), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        else
            text.setSpan(new RelativeSizeSpan(0.6f), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        mLblRemainingPayment.setText(text);

        mEditPayment.setText(NumberFormat.getCurrencyInstance().format(remainingPayment));
        mAdapterPayment.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnCash) {
            doPayment(PaymentType.CASH);
        }else if (v.getId() == R.id.btnCard) {
            doPayment(PaymentType.CARD);
        }else if (v.getId() == R.id.btnOK) {
            setResult(RESULT_OK);
            finish();
        }else if (v.getId() == R.id.btnCancel) {
            setResult(RESULT_CANCELED);
            finish();
        }
    }
}