package com.car.service;

import com.car.model.Car;
import com.car.repository.CarRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CarService {
    CarRepository carRepository= new CarRepository();

    public List<Car> getCarInfo() throws SQLException {
        return carRepository.getCarInfo();
    }

    public Map<String, Integer> getCarBrandStat() throws SQLException{

        return carRepository.getCarBrandStat();
    }
}
