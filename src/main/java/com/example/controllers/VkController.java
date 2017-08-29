package com.example.controllers;

import com.example.Application;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.photos.PhotoUpload;
import com.vk.api.sdk.objects.photos.responses.GetOwnerPhotoUploadServerResponse;
import com.vk.api.sdk.objects.photos.responses.SaveOwnerPhotoResponse;
import com.vk.api.sdk.objects.photos.responses.WallUploadResponse;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import com.vk.api.sdk.objects.wall.responses.PostResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * Created by Nikolay V. Petrov on 28.08.2017.
 */

@RestController
public class VkController {

    private final String NAMESPACE = "vk";

    private Integer USER_ID;
    private VkApiClient vkApiClient;
    private UserActor actor;

    public VkController() {
        Properties properties = new Properties();
        try (InputStream is = Application.class.getResourceAsStream("/vk-config.properties")) {
            properties.load(is);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        TransportClient transportClient = HttpTransportClient.getInstance();
        String access_token = String.valueOf(properties.getProperty("access.token"));
        this.USER_ID = Integer.valueOf(properties.getProperty("user.id"));
        this.vkApiClient = new VkApiClient(transportClient);
        this.actor = new UserActor(USER_ID, access_token);
    }

    @GetMapping(NAMESPACE + "/getWall")
    @ResponseBody
    public GetResponse getWall() throws ClientException, ApiException {
        return vkApiClient.wall().get(actor)
                .ownerId(USER_ID)
                .count(5)
                .offset(0)
                .execute();
    }

    @GetMapping(NAMESPACE + "/publicPhotoOnTheWall")
    @ResponseBody
    public PostResponse publicPhotoOnTheWall() throws ClientException, ApiException {
        File file = new File(System.getProperty("user.dir") + "\\upload-dir\\vk\\photo_for_upload.jpg");
        PhotoUpload serverResponse = vkApiClient.photos().getWallUploadServer(actor).execute();
        WallUploadResponse uploadResponse = vkApiClient.upload().photoWall(serverResponse.getUploadUrl(), file).execute();
        List<Photo> photoList = vkApiClient.photos().saveWallPhoto(actor, uploadResponse.getPhoto())
                .server(uploadResponse.getServer())
                .hash(uploadResponse.getHash())
                .execute();

        Photo photo = photoList.get(0);
        String attachId = "photo" + photo.getOwnerId() + "_" + photo.getId();

        return vkApiClient.wall().post(actor)
                .attachments(attachId)
                .execute();
    }

    @ResponseBody
    @GetMapping(NAMESPACE + "/publicAvatar")
    public SaveOwnerPhotoResponse publicAvatar() throws ClientException, ApiException {
        File file = new File(System.getProperty("user.dir") + "\\upload-dir\\vk\\photo_for_upload.jpg");
        GetOwnerPhotoUploadServerResponse serverResponse = vkApiClient.photos().getOwnerPhotoUploadServer(actor).execute();
        WallUploadResponse uploadResponse = vkApiClient.upload().photoWall(serverResponse.getUploadUrl(), file).execute();

        return  vkApiClient.photos().
                saveOwnerPhoto(actor)
                .photo(uploadResponse.getPhoto())
                .hash(uploadResponse.getHash())
                .server(String.valueOf(uploadResponse.getServer()))
                .execute();
    }
}