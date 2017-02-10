package com.gumtree.test.address_book.map_reduce;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import static org.apache.commons.lang.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class PersonWritable implements Writable {

    private Text name;
    private Text gender;
    private LongWritable dobMillis;

    public PersonWritable() {
        name = new Text();
        gender = new Text();
        dobMillis = new LongWritable();
    }

    public PersonWritable(Text name, Text gender, LongWritable dobMillis) {
        this.name = name;
        this.gender = gender;
        this.dobMillis = dobMillis;
    }

    public void set(Text name, Text gender, LongWritable dobMillis) {
        this.name = name;
        this.gender = gender;
        this.dobMillis = dobMillis;
    }

    public Text getName() {
        return name;
    }

    public void setName(Text name) {
        this.name = name;
    }

    public Text getGender() {
        return gender;
    }

    public void setGender(Text gender) {
        this.gender = gender;
    }

    public LongWritable getDob() {
        return dobMillis;
    }

    public void setDob(LongWritable dobMillis) {
        this.dobMillis = dobMillis;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        name.write(out);
        gender.write(out);
        dobMillis.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        name.readFields(in);
        gender.readFields(in);
        dobMillis.readFields(in);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (o.getClass() != getClass()) return false;

        PersonWritable person = (PersonWritable) o;

        return new EqualsBuilder()
                .append(name, person.getName())
                .append(gender, person.getGender())
                .append(dobMillis, person.getDob())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(name)
                .append(gender)
                .append(dobMillis)
                .toHashCode();
    }

    @Override
    public String toString() {
        return reflectionToString(this, SHORT_PREFIX_STYLE);
    }
}
