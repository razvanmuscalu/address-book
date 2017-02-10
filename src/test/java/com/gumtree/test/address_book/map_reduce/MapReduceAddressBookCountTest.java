package com.gumtree.test.address_book.map_reduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static java.util.Collections.singletonList;
import static org.apache.hadoop.mrunit.mapreduce.MapDriver.newMapDriver;
import static org.apache.hadoop.mrunit.mapreduce.MapReduceDriver.newMapReduceDriver;
import static org.apache.hadoop.mrunit.mapreduce.ReduceDriver.newReduceDriver;

public class MapReduceAddressBookCountTest {

    MapReduceDriver<LongWritable, Text, Text, IntWritable, Text, IntWritable> mapReduceDriver;
    MapDriver<LongWritable, Text, Text, IntWritable> mapDriver;
    ReduceDriver<Text, IntWritable, Text, IntWritable> reduceDriver;

    @Before
    public void setUp() {
        MapCount mapper = new MapCount();
        ReduceCount reducer = new ReduceCount();
        mapDriver = newMapDriver(mapper);
        reduceDriver = newReduceDriver(reducer);
        mapReduceDriver =  newMapReduceDriver(mapper, reducer);
    }

    @Test
    public void shouldMapMale() throws IOException {
        mapDriver.withInput(new LongWritable(1), new Text("Bill McKnight, Male, 16/03/77"));

        mapDriver.withOutput(new Text("Male"), new IntWritable(1));

        mapDriver.runTest();
    }

    @Test
    public void shouldMapFemale() throws IOException {
        mapDriver.withInput(new LongWritable(1), new Text("Gemma Lane, Female, 20/11/91"));

        mapDriver.withOutput(new Text("Female"), new IntWritable(1));

        mapDriver.runTest();
    }

    @Test
    public void shouldReduceMale() throws IOException {
        reduceDriver.withInput(new Text("Male"), singletonList(new IntWritable(1)));
        reduceDriver.withInput(new Text("Male"), singletonList(new IntWritable(1)));

        reduceDriver.withOutput(new Text("Male"), new IntWritable(2));
        reduceDriver.withOutput(new Text("Female"), new IntWritable(0));

        reduceDriver.runTest();
    }

    @Test
    public void shouldReduceFemale() throws IOException {
        reduceDriver.withInput(new Text("Female"), singletonList(new IntWritable(1)));
        reduceDriver.withInput(new Text("Female"), singletonList(new IntWritable(1)));

        reduceDriver.withOutput(new Text("Male"), new IntWritable(0));
        reduceDriver.withOutput(new Text("Female"), new IntWritable(2));

        reduceDriver.runTest();
    }

    @Test
    public void shouldMapAndReduceMaleAndFemale() throws IOException {
        mapReduceDriver.withInput(new LongWritable(), new Text("Bill McKnight, Male, 16/03/77"));
        mapReduceDriver.withInput(new LongWritable(), new Text("Gemma Lane, Female, 20/11/91"));

        mapReduceDriver.withOutput(new Text("Male"), new IntWritable(1));
        mapReduceDriver.withOutput(new Text("Female"), new IntWritable(1));

        mapReduceDriver.runTest();
    }
}
