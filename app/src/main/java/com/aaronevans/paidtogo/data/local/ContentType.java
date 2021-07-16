package com.aaronevans.paidtogo.data.local;

public enum ContentType {
    JPEG("image/jpeg"), GIF("image/gif"), PNG("image/png"), WEBP("image/webp"), VIDEO("video/mp4");

    public String mimeType;

    ContentType(String mimeType) {
        this.mimeType = mimeType;
    }

    public boolean isPhoto() {
        return mimeType.equals(JPEG.mimeType) || mimeType.equals(GIF.mimeType) || mimeType.equals(PNG.mimeType);
    }

    public boolean isVideo() {
        return !isPhoto();
    }
}