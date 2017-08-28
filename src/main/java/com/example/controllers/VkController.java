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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * Created by Nikolay V. Petrov on 28.08.2017.
 */

public class VkController {

    private Integer USER_ID;
    private VkApiClient vk;
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
        this.vk = new VkApiClient(transportClient);
        this.actor = new UserActor(USER_ID, access_token);
    }

    public GetResponse getWall() throws ClientException, ApiException {
        return vk.wall().get(actor)
                .ownerId(USER_ID)
                .count(5)
                .offset(0)
                .execute();
    }

    public PostResponse publicPhotoOnTheWall() throws ClientException, ApiException {
        File file = new File(System.getProperty("user.dir") + "\\upload-dir\\photo_for_upload.jpg");
        PhotoUpload serverResponse = vk.photos().getWallUploadServer(actor).execute();
        WallUploadResponse uploadResponse = vk.upload().photoWall(serverResponse.getUploadUrl(), file).execute();
        List<Photo> photoList = vk.photos().saveWallPhoto(actor, uploadResponse.getPhoto())
                .server(uploadResponse.getServer())
                .hash(uploadResponse.getHash())
                .execute();

        Photo photo = photoList.get(0);
        String attachId = "photo" + photo.getOwnerId() + "_" + photo.getId();

        return vk.wall().post(actor)
                .attachments(attachId)
                .execute();
    }

    public SaveOwnerPhotoResponse publicAvatar() throws ClientException, ApiException {
        File file = new File(System.getProperty("user.dir") + "\\upload-dir\\photo_for_upload.jpg");
        GetOwnerPhotoUploadServerResponse serverResponse = vk.photos().getOwnerPhotoUploadServer(actor).execute();
        WallUploadResponse uploadResponse = vk.upload().photoWall(serverResponse.getUploadUrl(), file).execute();

        return  vk.photos().
                saveOwnerPhoto(actor)
                .photo(uploadResponse.getPhoto())
                .hash(uploadResponse.getHash())
                .server(String.valueOf(uploadResponse.getServer()))
                .execute();
    }
}