package com.gumtree.test.address_book.map_reduce;

import org.apache.hadoop.util.ToolRunner;

import static java.lang.System.exit;

public class MapReduceMain {

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new MapReduceAddressBook(), args);
        exit(exitCode);
    }
}
