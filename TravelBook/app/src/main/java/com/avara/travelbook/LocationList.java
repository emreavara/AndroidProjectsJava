 package com.avara.travelbook;

import android.location.Address;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class LocationList {

    private static LocationList locationList;
    //Address address;
    ArrayList<Address> locationArrayList;

    private LocationList(){}

    public LocationList LocationList(){
        if (locationList == null){
            locationList = new LocationList();
        }
        return locationList;
    }

    public void addNewLocation(Address address){
        locationArrayList.add(address);
    }
    public ArrayList<Address> getLocationArrayList(){
        return locationArrayList;
    }
}
