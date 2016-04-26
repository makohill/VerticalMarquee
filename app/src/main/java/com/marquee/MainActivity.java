package com.marquee;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import com.marquee.R;

public class MainActivity extends AppCompatActivity {
  private VerticalMarqueeView marqueeView;
  private VerticalMarqueeAdapter marqueeBaseAdapter;
  private List<String> list;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    marqueeView= (VerticalMarqueeView) this.findViewById(R.id.marquee);
    list=new ArrayList<>();
    list.add("定理描述素数的比较准确的分布情况。素数的出现规律一直困惑著数学家。");
    list.add("出现规律一直困惑著数学家。一个个地看，素数在正整数中的出");
    list.add("理，也可以称为素数正态分布定理猜想，有待数学家在数学上");
    list.add("理，学家哈达玛(JacquesHadamard)和比利时数学家普森(Char其是黎曼ζ函数。 因，有待数学家在数学上");
    list.add("定理描述素数的比较准确的分布情况。素数的出现规律一直困惑著数学家。一个个地看，素数在正整数中的出现没有什么规律。可是总体地看，素数的个数竟然有规可循");
    list.add("出现规律一直困惑著数学家。一个个地看，素数在正整数中的出");
    list.add("理，也可以称为素数正态分布定理猜想，有待数学家在数学上");
    list.add("理，学家哈达玛(JacquesHadamard)和比利时数学家普森(Char其是黎曼ζ函数。 因，有待数学家在数学上");
    marqueeBaseAdapter=new VerticalMarqueeAdapter(list);

    marqueeView.setAdapter(marqueeBaseAdapter);
    marqueeBaseAdapter.setMarqueeOnClickListener(new VerticalMarqueeAdapter.MarqueeOnClickListener() {
      @Override public void onClick(int position) {
        Toast.makeText(MainActivity.this, "点击", Toast.LENGTH_SHORT).show();

      }
    });

    marqueeView.start();

  }
}
