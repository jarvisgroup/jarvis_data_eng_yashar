package ca.jrvs.apps.twitter.dao.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Coordinates {
    private double[] coordinates;
    private String type;

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int size(){
        return coordinates.length;
    }

    // get the longitude or latitude
    public double get(int type){
        return coordinates[type];

    }

}
