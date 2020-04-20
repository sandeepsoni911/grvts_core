package com.owners.gravitas.error.log;


import java.io.File;
import java.io.IOException;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * The Class ErrorLogExcelRequestUtil.
 */
public class ErrorLogExcelRequestUtil {

    /** The upload file. */
    private File uploadFile;

    /** The token. */
    private String token;

    /** The channels. */
    private String channels;

    /** The file type. */
    private String fileType;

    /** The media type. */
    private String mediaType;

    /** The title. */
    private String title;

    /** The initial comment. */
    private String initialComment;

    /**
     * Instantiates a new error log excel request builder.
     *
     * @param builder the builder
     */
    private ErrorLogExcelRequestUtil(Builder builder) {
        if (builder.token == null) {
            new RuntimeException("token is required.");
        }
        if (builder.uploadFile == null) {
            new RuntimeException("uploadFile is required.");
        }
        uploadFile = builder.uploadFile;
        token = builder.token;
        channels = builder.channels;
        fileType = builder.fileType;
        mediaType = builder.mediaType;
        title = builder.title;
        initialComment = builder.initialComment;
    }

    /**
     * Post.
     *
     * @return the response
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public Response post() throws IOException {
        RequestBody requestBody = createRequestBody();
        Request request = createRequest(requestBody);
        OkHttpClient client = new OkHttpClient();
        return client.newCall(request).execute();
    }

    /**
     * Post.
     *
     * @param callback the callback
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void post(Callback callback) throws IOException {
        RequestBody requestBody = createRequestBody();
        Request request = createRequest(requestBody);
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(callback);
    }

    /**
     * Creates the request body.
     *
     * @return the request body
     */
    private RequestBody createRequestBody() {
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder();
        multipartBuilder.setType(MultipartBody.FORM);
        multipartBuilder.addFormDataPart("file", uploadFile.getName(),
                RequestBody.create(MediaType.parse(mediaType != null ? mediaType : ""), uploadFile));
        multipartBuilder.addFormDataPart("token", token);
        if (channels != null) {
            multipartBuilder.addFormDataPart("channels", channels);
        }
        if (fileType != null) {
            multipartBuilder.addFormDataPart("filetype", fileType);
        }
        if (title != null) {
            multipartBuilder.addFormDataPart("title", title);
        }
        if (initialComment != null) {
            multipartBuilder.addFormDataPart("initial_comment", initialComment);
        }
        return multipartBuilder.build();
    }

    /**
     * Creates the request.
     *
     * @param requestBody the request body
     * @return the request
     */
    private Request createRequest(RequestBody requestBody) {
        return new Request.Builder()
                .url("https://slack.com/api/files.upload")
                .post(requestBody)
                .build();
    }

    /**
     * The Class Builder.
     */
    public static final class Builder {

        /** The upload file. */
        private File uploadFile;

        /** The token. */
        private String token;

        /** The channels. */
        private String channels;

        /** The file type. */
        private String fileType;

        /** The media type. */
        private String mediaType;

        /** The title. */
        private String title;

        /** The initial comment. */
        private String initialComment;

        /**
         * Instantiates a new builder.
         */
        public Builder() {
        }

        /**
         * Upload file.
         *
         * @param uploadFile the upload file
         * @return the builder
         */
        public Builder uploadFile(File uploadFile) {
            this.uploadFile = uploadFile;
            return this;
        }

        /**
         * Token.
         *
         * @param token the token
         * @return the builder
         */
        public Builder token(String token) {
            this.token = token;
            return this;
        }

        /**
         * Channels.
         *
         * @param channels the channels
         * @return the builder
         */
        public Builder channels(String channels) {
            this.channels = channels;
            return this;
        }

        /**
         * File type.
         *
         * @param fileType the file type
         * @return the builder
         */
        public Builder fileType(String fileType) {
            this.fileType = fileType;
            return this;
        }

        /**
         * Media type.
         *
         * @param mediaType the media type
         * @return the builder
         */
        public Builder mediaType(String mediaType) {
            this.mediaType = mediaType;
            return this;
        }

        /**
         * Title.
         *
         * @param title the title
         * @return the builder
         */
        public Builder title(String title) {
            this.title = title;
            return this;
        }

        /**
         * Initial comment.
         *
         * @param initialComment the initial comment
         * @return the builder
         */
        public Builder initialComment(String initialComment) {
            this.initialComment = initialComment;
            return this;
        }

        /**
         * Builds the.
         *
         * @return the error log excel request builder
         */
        public ErrorLogExcelRequestUtil build() {
            return new ErrorLogExcelRequestUtil(this);
        }
    }
}