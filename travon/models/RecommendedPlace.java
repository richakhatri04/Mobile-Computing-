/*
 * Copyright 2015 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thealienobserver.nikhil.travon.models;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;


public class RecommendedPlace {
    public String name;
    public String description;
    public String formatted_address;
    public String formatted_phone_number;
    public String secondaryImageUrl;
    public String city;


    public Bitmap image;
    public String distance;
    public String image_ref;


    public void setCity(String city) {
        this.city = city;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setFormattedPhoneNumber(String formatted_phone_number) {
        this.formatted_phone_number = formatted_phone_number;
    }

    public void setImage_ref(String image_ref) {
        this.image_ref = image_ref;
    }

    public void setFormattedAddress(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getFormattedPhoneNumber() {
        return formatted_phone_number;
    }

    public String getImage_ref() {
        return image_ref;
    }

    public String getFormattedAddress() {
        return formatted_address;
    }

    public String getSecondaryImageUrl() {
        return secondaryImageUrl;
    }
}