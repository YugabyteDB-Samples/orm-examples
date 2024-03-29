/*
 * This file is generated by jOOQ.
 */
package com.yugabyte.jooqdemo.db.tables.records;


import com.yugabyte.jooqdemo.db.tables.Users;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.16.2",
        "schema version:jooq_1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UsersRecord extends UpdatableRecordImpl<UsersRecord> implements Record4<Long, String, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>jooq.users.user_id</code>.
     */
    public void setUserId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>jooq.users.user_id</code>.
     */
    public Long getUserId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>jooq.users.first_name</code>.
     */
    public void setFirstName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>jooq.users.first_name</code>.
     */
    public String getFirstName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>jooq.users.last_name</code>.
     */
    public void setLastName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>jooq.users.last_name</code>.
     */
    public String getLastName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>jooq.users.email</code>.
     */
    public void setEmail(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>jooq.users.email</code>.
     */
    public String getEmail() {
        return (String) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<Long, String, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<Long, String, String, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return Users.USERS.USER_ID;
    }

    @Override
    public Field<String> field2() {
        return Users.USERS.FIRST_NAME;
    }

    @Override
    public Field<String> field3() {
        return Users.USERS.LAST_NAME;
    }

    @Override
    public Field<String> field4() {
        return Users.USERS.EMAIL;
    }

    @Override
    public Long component1() {
        return getUserId();
    }

    @Override
    public String component2() {
        return getFirstName();
    }

    @Override
    public String component3() {
        return getLastName();
    }

    @Override
    public String component4() {
        return getEmail();
    }

    @Override
    public Long value1() {
        return getUserId();
    }

    @Override
    public String value2() {
        return getFirstName();
    }

    @Override
    public String value3() {
        return getLastName();
    }

    @Override
    public String value4() {
        return getEmail();
    }

    @Override
    public UsersRecord value1(Long value) {
        setUserId(value);
        return this;
    }

    @Override
    public UsersRecord value2(String value) {
        setFirstName(value);
        return this;
    }

    @Override
    public UsersRecord value3(String value) {
        setLastName(value);
        return this;
    }

    @Override
    public UsersRecord value4(String value) {
        setEmail(value);
        return this;
    }

    @Override
    public UsersRecord values(Long value1, String value2, String value3, String value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UsersRecord
     */
    public UsersRecord() {
        super(Users.USERS);
    }

    /**
     * Create a detached, initialised UsersRecord
     */
    public UsersRecord(Long userId, String firstName, String lastName, String email) {
        super(Users.USERS);

        setUserId(userId);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
    }
}
