package com.car.repository;

import com.car.dao.CarDao;
import com.car.model.Car;
import com.car.model.CarDetails;
import com.car.model.Owner;
import com.car.util.DBConnectionUtil;
import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CarRepository implements CarDao {

    DBConnectionUtil dbConnectionUtil= DBConnectionUtil.getInstance();

    @Override
    public List<Car> getCarInfo() throws SQLException {

        Connection conn= dbConnectionUtil.dbConnect();

        List<Car> list= new ArrayList<>();

        String sql= "select c.id, c.registration_number, c.chasis_number, c.registration_state, c.brand, c.model, c.variant,"+
                " o.name as owner_name, cd.year_of_purchase as cd_year_of_purchase, cd.milage as cd_mileage from owner o "+
                " join car c on o.id= c.owner_id "+
                " join car_details cd on c.car_details_id= cd.id";

        PreparedStatement ps= conn.prepareStatement(sql);
        ResultSet rs= ps.executeQuery();

        while(rs.next()){
            Owner owner= new Owner();
            String ownerName= rs.getString("owner_name");
            owner.setName(ownerName);

            CarDetails carDetails= new CarDetails();
            int yearOfPurchase= rs.getInt("cd_year_of_purchase");
            int mileage= rs.getInt("cd_mileage");

            Car car= new Car(
                    rs.getInt("id"),
                    rs.getString("registration_number"),
                    rs.getString("chasis_number"),
                    rs.getString("registration_state"),
                    rs.getString("brand"),
                    rs.getString("model"),
                    rs.getString("variant"),
                    owner,
                    carDetails
            );
            list.add(car);
        }
        dbConnectionUtil.dbClose();
        return list;
    }

    @Override
    public Map<String, Integer> getCarBrandStat() {

        Map<String, Integer> map= new LinkedHashMap<>();

        Connection conn= dbConnectionUtil.dbConnect();

        try{
            String sql= "Select brand, count(id) as number_of_cars from car group by brand order by number_of_cars desc";

            PreparedStatement ps= conn.prepareStatement(sql);
            ResultSet rs= ps.executeQuery();

            while(rs.next()){
                map.put(rs.getString("brand"), rs.getInt("number_of_cars"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        dbConnectionUtil.dbClose();
        return map;
    }
}
