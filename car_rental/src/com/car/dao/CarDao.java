package com.car.dao;

import com.car.model.Car;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface CarDao {
    List<Car> getCarInfo() throws SQLException;

    Map<String, Integer> getCarBrandStat();
}
