package com.gumtree.test.address_book.map_reduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;

import static org.apache.hadoop.mapreduce.lib.input.FileInputFormat.addInputPath;
import static org.apache.hadoop.mapreduce.lib.output.FileOutputFormat.setOutputPath;

public class AddressBookOldest extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {
        Configuration conf = this.getConf();

        Job job = Job.getInstance(conf);
        job.setJobName("address-book");
        job.setJarByClass(AddressBookOldest.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(PersonWritable.class);

        job.setMapperClass(MapOldest.class);
        job.setReducerClass(ReduceOldest.class);

        Path input = new Path("src/main/resources/input");
        Path output = new Path("target/output/oldest");
        addInputPath(job, input);
        setOutputPath(job, output);

        return job.waitForCompletion(true) ? 0 : 1;
    }
}
