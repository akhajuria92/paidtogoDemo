package com.aaronevans.paidtogo.data.entities;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class GymCheckIns {




        @SerializedName("latitude")
        @Expose
        private double lattitude;
        @SerializedName("longitude")
        @Expose
        private double longitude;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("gym_id")
        @Expose
        private int gymId;
        @SerializedName("imageUrl")
        @Expose
        private String imageUrl;

    public GymCheckIns() {
    }

    public GymCheckIns(double lattitude, double longitude, String name, int gymId, String imageUrl) {
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.name = name;
        this.gymId = gymId;
        this.imageUrl = imageUrl;
    }

    public double getLattitude() {
            return lattitude;
        }

        public void setLattitude(double lattitude) {
            this.lattitude = lattitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getGymId() {
            return gymId;
        }

        public void setGymId(int gymId) {
            this.gymId = gymId;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

    }

