package org.Giru;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.javafaker.Faker;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.bson.types.ObjectId;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Example {
    public static void main(String[] args) throws IOException {
        Faker faker = new Faker();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // optional: enable pretty printing

        for (int i = 0; i < 10; i++) {
            // create a new user
            User user = new User();
            user.setName(faker.name().fullName());
            user.setPhone(faker.phoneNumber().cellPhone());
            user.setEmail(faker.internet().emailAddress());
            user.setPassword(faker.internet().password());
            user.setPhoto(faker.internet().image());
            user.setBooked(new String[] {});
            user.setRole(new ObjectId("60445d9f2c617841b8e02b00"));

            // convert the user to JSON
            String userJson = objectMapper.writeValueAsString(user);

            // post the user to the API
            String userUrl = "http://localhost:3000/api/signup";
            HttpClientUtils.postJson(userUrl, userJson);

            // create a new tour
            Tour tour = new Tour();
            tour.setName(faker.company().name());
            tour.setStart(faker.date().future(10, TimeUnit.DAYS));
            tour.setEnd(faker.date().future(30, TimeUnit.DAYS));
            tour.setPrice(faker.number().randomDouble(2, 50, 1000));
            tour.setLocation(faker.address().cityName());
            tour.setDescription(faker.lorem().paragraph());
            tour.setImages(new String[] { faker.internet().image(), faker.internet().image(), faker.internet().image() });
            tour.setOperator(new ObjectId("60445d9f2c617841b8e02b01"));

            // convert the tour to JSON
            String tourJson = objectMapper.writeValueAsString(tour);

            // post the tour to the API
            String tourUrl = "http://localhost:3000/api/tours";
            HttpClientUtils.postJson(tourUrl, tourJson);
        }
    }
}

class User {
    private String name;
    private String phone;
    private String email;
    private String password;
    private String photo;
    private String[] booked;
    private ObjectId role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String[] getBooked() {
        return booked;
    }

    public void setBooked(String[] booked) {
        this.booked = booked;
    }

    public ObjectId getRole() {
        return role;
    }

    public void setRole(ObjectId role) {
        this.role = role;
    }
}

class Tour {
    private String name;
    private Date start;
    private Date end;
    private double price;
    private String location;
    private String description;
    private String[] images;
    private ObjectId operator;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public ObjectId getOperator() {
        return operator;
    }

    public void setOperator(ObjectId operator) {
        this.operator = operator;
    }
}

class HttpClientUtils {
    public static void postJson(String url, String json) throws IOException {
        HttpPost request = new HttpPost(url);
        StringEntity entity = new StringEntity(json);
        entity.setContentType("application/json");
        request.setEntity(entity);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode < 200 || statusCode >= 300) {
            throw new RuntimeException("HTTP POST request failed with status code: " + statusCode);
        }
    }
}
