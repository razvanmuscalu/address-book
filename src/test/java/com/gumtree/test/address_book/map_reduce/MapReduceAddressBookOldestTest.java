package com.gumtree.test.address_book.map_reduce;

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

public class MapReduceAddressBookOldestTest {

    MapReduceDriver<LongWritable, Text, Text, PersonWritable, Text, Text> mapReduceDriver;
    MapDriver<LongWritable, Text, Text, PersonWritable> mapDriver;
    ReduceDriver<Text, PersonWritable, Text, Text> reduceDriver;

    @Before
    public void setUp() {
        MapOldest mapper = new MapOldest();
        ReduceOldest reducer = new ReduceOldest();
        mapDriver = newMapDriver(mapper);
        reduceDriver = newReduceDriver(reducer);
        mapReduceDriver = newMapReduceDriver(mapper, reducer);
    }

    @Test
    public void shouldMapPerson() throws IOException {
        mapDriver.withInput(new LongWritable(1), new Text("Bill McKnight, Male, 16/03/77"));

        mapDriver.withOutput(
                new Text("BillMcKnight"),
                new PersonWritable(
                        new Text("BillMcKnight"),
                        new Text("Male"),
                        new LongWritable(227318400000L)
                )
        );

        mapDriver.runTest();
    }


    @Test
    public void shouldReducePerson() throws IOException {
        reduceDriver.withInput(
                new Text("BillMcKnight"),
                singletonList(new PersonWritable(
                        new Text("WesJackson"),
                        new Text("Male"),
                        new LongWritable(145666800000L))
                )
        );
        reduceDriver.withInput(
                new Text("WesJackson"),
                singletonList(new PersonWritable(
                        new Text("WesJackson"),
                        new Text("Male"),
                        new LongWritable(145666800000L))
                )
        );

        reduceDriver.withOutput(new Text("Oldest"), new Text("WesJackson"));

        reduceDriver.runTest();
    }

    @Test
    public void shouldMapAndReduceOldestPerson() throws IOException {
        mapReduceDriver.withInput(new LongWritable(), new Text("Bill McKnight, Male, 16/03/77"));
        mapReduceDriver.withInput(new LongWritable(), new Text("Wes Jackson, Male, 14/08/74"));

        mapReduceDriver.withOutput(new Text("Oldest"), new Text("WesJackson"));

        mapReduceDriver.runTest();
    }
}
