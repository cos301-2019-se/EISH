package com.monotoneid.eishms.services.filemanagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.monotoneid.eishms.datapersistence.models.HomeDetails;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import net.minidev.json.JSONObject;

@Service()
public class HomeDetailsService {

    private static final TypeToken<HomeDetails> tToken = new TypeToken<HomeDetails>() {};
    private static final Type REVIEW_TYPE = tToken.getType();

    private File filePath;
    
    //  File("HomeDetails.json");
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public ResponseEntity<Object> retrieveHomeDetails() {
        try {
            return new ResponseEntity<>(readFromFile(), HttpStatus.OK);
        } catch(Exception e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Error", "Failed to retrieve Home Details!");
            return new ResponseEntity<>(jsonObject, HttpStatus.NOT_FOUND);
        }
    }

    public void writeToFile(HomeDetails homeDetails) throws IOException {
        filePath  = new ClassPathResource("HomeDetails.json").getFile();
        FileWriter fw = new FileWriter(filePath);
        gson.toJson(homeDetails, fw);
        fw.close();
    }

    public HomeDetails readFromFile() throws FileNotFoundException {
        filePath  = new ClassPathResource("HomeDetails.json").getFile();
        FileReader fr = new FileReader(filePath);
        JsonReader reader = new JsonReader(fr);
        HomeDetails homeDetails = gson.fromJson(reader, REVIEW_TYPE);
        fr.close();
        return homeDetails;
    }
}