package com.gumtree.test.address_book.map_reduce;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class ReduceOldest extends Reducer<Text, PersonWritable, Text, Text> {

    private Text name = new Text();
    private LongWritable oldest = new LongWritable();

    @Override
    public void setup(Context context) throws IOException, InterruptedException {
        oldest.set(Long.MAX_VALUE);
    }

    @Override
    public void reduce(Text key, Iterable<PersonWritable> values, Context context) throws IOException, InterruptedException {
        for (PersonWritable person : values)
            if (person.getDob().get() < oldest.get()) {
                oldest.set(person.getDob().get());
                name.set(person.getName());
            }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        context.write(new Text("Oldest"), new Text(name));
    }
}
