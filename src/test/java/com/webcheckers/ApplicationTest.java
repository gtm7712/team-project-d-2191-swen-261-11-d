package com.webcheckers;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class ApplicationTest {

    /**
     * Tests the main function of the application
     */
    @Test
    public void demoMode() {
        Application.main(null);
        assertFalse(Application.isInDemoMode());
    }
    
}