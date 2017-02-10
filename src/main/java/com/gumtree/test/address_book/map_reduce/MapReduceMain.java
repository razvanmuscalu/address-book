package com.gumtree.test.address_book.map_reduce;

import org.apache.hadoop.util.ToolRunner;

import java.io.File;

import static org.apache.commons.io.FileUtils.deleteDirectory;

public class MapReduceMain {

    public static void main(String[] args) throws Exception {
        deleteDirectory(new File("target/output"));

        ToolRunner.run(new AddressBookCount(), args);
        ToolRunner.run(new AddressBookOldest(), args);
    }
}
