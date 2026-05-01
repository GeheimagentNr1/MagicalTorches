package de.geheimagentnr1.magical_torches;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MagicalTorchesTest {

    @Test
    void modIdIsValid() {

        String modId = "magical_torches";
        assertTrue( modId.matches( "[a-z][a-z0-9_]{1,63}" ) );
    }
}
