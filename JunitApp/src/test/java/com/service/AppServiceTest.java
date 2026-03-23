package com.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AppServiceTest {

    private AppService appService= new AppService();

    @Test
    public void add(){
        Assertions.assertEquals(7, appService.add(2,5));
        Assertions.assertEquals(0, appService.add(-1,1));
        Assertions.assertNotEquals(2, appService.add(0,0));

        try {
            Assertions.assertEquals(0, appService.add(0, 0));
        } catch (RuntimeException e) {
            Assertions.assertEquals("List cannot be null", e.getMessage());
        }
    }
}
