package com.br.payment.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.br.payment.R;
import com.br.payment.model.Payment;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by GuilhermeAlexs on 10/10/2016.
 */
public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder>{
    private List<Payment> mDataset;

    public PaymentAdapter(List<Payment> dataset) {
        mDataset = dataset;
    }

    @Override
    public PaymentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payment_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mLblPaymentType.setText(mDataset.get(position).getPaymentType().getType());
        holder.mLblPaymentValue.setText(NumberFormat.getCurrencyInstance().format(mDataset.get(position).getValue()));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mLblPaymentType;
        public TextView mLblPaymentValue;
        public ViewHolder(View v) {
            super(v);
            mLblPaymentType = (TextView) v.findViewById(R.id.lblPaymentType);
            mLblPaymentValue = (TextView) v.findViewById(R.id.lblRemainingValue);
        }
    }
}