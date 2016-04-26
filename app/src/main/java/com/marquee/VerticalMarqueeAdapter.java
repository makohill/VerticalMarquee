package com.marquee;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import java.util.List;
import com.marquee.R;

public class VerticalMarqueeAdapter {
  protected List<String> mList;
  private MarqueeOnClickListener marqueeOnClickListener;


  private OnDataChangeListener mOnDataChangeListener;

  public VerticalMarqueeAdapter(List<String> list) {
    this.mList = list;
  }

  public VerticalMarqueeAdapter() {
  }


  public  void setItemView(View view, int position){
    TextView textView1= (TextView) view.findViewById(R.id.text1);
    TextView textView2= (TextView) view.findViewById(R.id.text2);
    String dateString="<font color=\"#FC6457\" ><big><strong> • </big></strong></font>"+mList.get(position);
    textView1.setText(Html.fromHtml(dateString));
    textView2.setText(Html.fromHtml(dateString));
    view.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int position = 0;
        if (marqueeOnClickListener != null) {
          marqueeOnClickListener.onClick(position);
        }
      }
    });

  }

  public void setOnDataChangeListener(OnDataChangeListener listener) {
    mOnDataChangeListener = listener;
  }

  public void setDataList(List list) {
    this.mList = list;
    notifyDataSetChanged();
  }
  public void setMarqueeOnClickListener(MarqueeOnClickListener marqueeOnClickListener) {
    this.marqueeOnClickListener = marqueeOnClickListener;
  }

  public int getCount() {
    return mList != null && mList.size() > 0 ? mList.size() : 0;
  }


  public View getView(ViewGroup parent) {
    return LayoutInflater.from(parent.getContext()).inflate(R.layout.marguee_view, null);
  }

  public void notifyDataSetChanged() {
    if (mOnDataChangeListener != null) mOnDataChangeListener.onDataChange();
  }

  public interface OnDataChangeListener {
    void onDataChange();
  }
  public interface MarqueeOnClickListener {
    void onClick(int position);
  }



}
