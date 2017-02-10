package com.gumtree.test.address_book.map_reduce;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MapOldest extends Mapper<LongWritable, Text, Text, PersonWritable> {

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] row = value.toString().replaceAll("\\s+", "").split(",");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

        try {
            Date date = sdf.parse(row[2]);

            PersonWritable person = new PersonWritable(new Text(row[0]), new Text(row[1]), new LongWritable(date.getTime()));

            context.write(new Text(row[0]), person);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
