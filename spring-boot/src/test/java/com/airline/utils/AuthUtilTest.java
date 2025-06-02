package com.airline.utils;

import com.airline.entity.UserEntity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthUtilTest {
    @Test
    void testIsAdmin() {
        UserEntity admin = new UserEntity();
        admin.setRole("ADMIN");
        assertTrue(AuthUtil.isAdmin(admin));
    }

    @Test
    void testIsCustomer() {
        UserEntity customer = new UserEntity();
        customer.setRole("CUSTOMER");
        assertTrue(AuthUtil.isCustomer(customer));
    }

    @Test
    void testIsStaff() {
        UserEntity staff = new UserEntity();
        staff.setRole("STAFF");
        assertTrue(AuthUtil.isStaff(staff));
    }
} 