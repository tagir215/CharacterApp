package com.tiger.CharacterPalace.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tiger.CharacterPalace.model.Path;

public class JsonMapperTest {

    private List<Path> curves;

    @BeforeEach
    public void setUp() {
        curves = new ArrayList<>();
    }

    @Test
    public void testToJson() {
        Path curve1 = new Path();
        curve1.setType("curve1");
        curve1.addCoordinate(new double[]{1.0, 2.0});
        curve1.addCoordinate(new double[]{3.0, 4.0});
        curves.add(curve1);

        Path curve2 = new Path();
        curve2.setType("curve2");
        curve2.addCoordinate(new double[]{5.0, 6.0});
        curve2.addCoordinate(new double[]{7.0, 8.0});
        curve2.addCoordinate(new double[]{9.0, 10.0});
        curves.add(curve2);

        String json = JsonMapper.curvesToJson(curves);

        String expectedJson = "[{\"type\":\"curve1\",\"coords\":[{\"x\":1.0,\"y\":2.0},{\"x\":3.0,\"y\":4.0}]},{\"type\":\"curve2\",\"coords\":[{\"x\":5.0,\"y\":6.0},{\"x\":7.0,\"y\":8.0},{\"x\":9.0,\"y\":10.0}]}]";
        assertEquals(expectedJson, json);
    }
}
