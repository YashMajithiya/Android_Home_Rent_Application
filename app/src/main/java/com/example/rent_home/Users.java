    package com.example.rent_home;
    //here We will Get all the Data
    public class Users {
        private String Name;
        private final String Username;
        private String id;
        private String Address;
        private final String image;
        private String Number;

        public Users(String username, String image) {
            Username = username;
            this.image = image;
        }

        //Here We will Get Name
    public String getName() {
        return Name;
    }
    //Here Will Set Name
    public void setName(String name) {
        Name = name;
    }
    public String getUsername() {
        return Username;
    }

        public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getAddress() {
        return Address;
    }
    public void setAddress(String address) {
        this.Address = address;
    }
    public String getImage() {
        return image;
    }

        public String getNumber() {
        return Number;
    }
    public void setNumber(String number) {
        Number = number;
    }

    }
