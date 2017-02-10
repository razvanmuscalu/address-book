package com.gumtree.test.address_book.map_reduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class ReduceCount extends Reducer<Text, IntWritable, Text, IntWritable> {

    private int maleCount = 0;
    private int femaleCount = 0;

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        for (IntWritable value : values) {
            if (key.toString().equals("Male"))
                maleCount += value.get();
            else if (key.toString().equals("Female"))
                femaleCount += value.get();
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        context.write(new Text("Male"), new IntWritable(maleCount));
        context.write(new Text("Female"), new IntWritable(femaleCount));
    }
}
