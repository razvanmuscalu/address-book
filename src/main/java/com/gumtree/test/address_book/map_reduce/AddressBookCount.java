package com.gumtree.test.address_book.map_reduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;

import static org.apache.hadoop.mapreduce.lib.input.FileInputFormat.addInputPath;
import static org.apache.hadoop.mapreduce.lib.output.FileOutputFormat.setOutputPath;

public class AddressBookCount extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {
        Configuration conf = this.getConf();

        Job job = Job.getInstance(conf);
        job.setJobName("address-book");
        job.setJarByClass(AddressBookCount.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.setMapperClass(MapCount.class);
        job.setReducerClass(ReduceCount.class);

        Path input = new Path("src/main/resources/input");
        Path output = new Path("target/output/count");
        addInputPath(job, input);
        setOutputPath(job, output);

        return job.waitForCompletion(true) ? 0 : 1;
    }
}
