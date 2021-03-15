package com.sudrives.sudrivespartner.models;

import java.util.List;

public class DeclineRequestModel {


    /**
     * status : 1
     * error_code : 0
     * error_line : 229
     * message : Successfully
     * result : {"decline_request":[{"id":"3","name":"Capacity Is In High Volume","types":"decline_request"},{"id":"7","name":"Customer Asked Me To Cancel","types":"decline_request"},{"id":"4","name":"Customer Is Going Late","types":"decline_request"},{"id":"8","name":"My Reason Is Not Listed","types":"decline_request"},{"id":"6","name":"Not Interested","types":"decline_request"},{"id":"1","name":"Unable To Contact Customer","types":"decline_request"},{"id":"2","name":"Unable To Go Route","types":"decline_request"}],"vehicle_types":[{"id":"7","fare_charges":"30","vehicle_name":"32ft Container","vehicle_img":"http://52.33.111.26/api/media/vehicle/icon_4ton@3x.png","vehicle_size":"4*5*2","vehicle_payload":"7","vehicle_discription":"test 7"},{"id":"8","fare_charges":"40","vehicle_name":"Canter 24ft","vehicle_img":"http://52.33.111.26/api/media/vehicle/icon_5ton@3x.png","vehicle_size":"4*5*2","vehicle_payload":"8","vehicle_discription":"test 8"},{"id":"4","fare_charges":"130","vehicle_name":"Haulage Hire","vehicle_img":"http://52.33.111.26/api/media/vehicle/icon_4ton@3x.png","vehicle_size":"4*5*2","vehicle_payload":"4","vehicle_discription":"test 3"},{"id":"6","fare_charges":"20","vehicle_name":"Pickup / 8ft","vehicle_img":"http://52.33.111.26/api/media/vehicle/icon_6ton@3x.png","vehicle_size":"4*5*2","vehicle_payload":"6","vehicle_discription":"test 6"},{"id":"5","fare_charges":"10","vehicle_name":"Pickup 8ft Open","vehicle_img":"http://52.33.111.26/api/media/vehicle/icon_5ton@3x.png","vehicle_size":"4*5*2","vehicle_payload":"5","vehicle_discription":"test 5"},{"id":"9","fare_charges":"50","vehicle_name":"Tata 19ft","vehicle_img":"http://52.33.111.26/api/media/vehicle/icon_6ton@3x.png","vehicle_size":"4*5*2","vehicle_payload":"9","vehicle_discription":"test 9"},{"id":"3","fare_charges":"120","vehicle_name":"Tata 407","vehicle_img":"http://52.33.111.26/api/media/vehicle/icon_6ton@3x.png","vehicle_size":"4*5*2","vehicle_payload":"3","vehicle_discription":"test 4"},{"id":"1","fare_charges":"50","vehicle_name":"Tata Ace","vehicle_img":"http://52.33.111.26/api/media/vehicle/icon_4ton@3x.png","vehicle_size":"4*5*2","vehicle_payload":"1","vehicle_discription":"test 1"},{"id":"2","fare_charges":"100","vehicle_name":"Tata Ace Open","vehicle_img":"http://52.33.111.26/api/media/vehicle/icon_5ton@3x.png","vehicle_size":"4*5*2","vehicle_payload":"2","vehicle_discription":"test 2"},{"id":"10","fare_charges":"99","vehicle_name":"Tata Zip","vehicle_img":"http://52.33.111.26/api/media/vehicle/icon_4ton@3x.png","vehicle_size":"4*5*2","vehicle_payload":"10","vehicle_discription":"test 10"}]}
     */

    private int status;
    private int error_code;
    private int error_line;
    private String message;
    private ResultBean result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public int getError_line() {
        return error_line;
    }

    public void setError_line(int error_line) {
        this.error_line = error_line;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 3
         * name : Capacity Is In High Volume
         * types : decline_request
         */

        private List<DeclineRequestBean> decline_request;
        /**
         * id : 7
         * fare_charges : 30
         * vehicle_name : 32ft Container
         * vehicle_img : http://52.33.111.26/api/media/vehicle/icon_4ton@3x.png
         * vehicle_size : 4*5*2
         * vehicle_payload : 7
         * vehicle_discription : test 7
         */

        private List<VehicleTypesBean> vehicle_types;

        public List<DeclineRequestBean> getDecline_request() {
            return decline_request;
        }

        public void setDecline_request(List<DeclineRequestBean> decline_request) {
            this.decline_request = decline_request;
        }

        public List<VehicleTypesBean> getVehicle_types() {
            return vehicle_types;
        }

        public void setVehicle_types(List<VehicleTypesBean> vehicle_types) {
            this.vehicle_types = vehicle_types;
        }

        public static class DeclineRequestBean {
            private String id;
            private String name;
            private String types;
            private boolean isChecked;

            public DeclineRequestBean(String id, String name, String types, boolean isChecked) {
                this.id = id;
                this.name = name;
                this.types = types;
                this.isChecked = isChecked;
            }

            public boolean isChecked() {
                return isChecked;
            }

            public void setChecked(boolean checked) {
                isChecked = checked;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTypes() {
                return types;
            }

            public void setTypes(String types) {
                this.types = types;
            }
        }

        public static class VehicleTypesBean {
            private String id;
            private String fare_charges;
            private String vehicle_name;
            private String vehicle_img;
            private String vehicle_size;
            private String vehicle_payload;
            private String vehicle_discription;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getFare_charges() {
                return fare_charges;
            }

            public void setFare_charges(String fare_charges) {
                this.fare_charges = fare_charges;
            }

            public String getVehicle_name() {
                return vehicle_name;
            }

            public void setVehicle_name(String vehicle_name) {
                this.vehicle_name = vehicle_name;
            }

            public String getVehicle_img() {
                return vehicle_img;
            }

            public void setVehicle_img(String vehicle_img) {
                this.vehicle_img = vehicle_img;
            }

            public String getVehicle_size() {
                return vehicle_size;
            }

            public void setVehicle_size(String vehicle_size) {
                this.vehicle_size = vehicle_size;
            }

            public String getVehicle_payload() {
                return vehicle_payload;
            }

            public void setVehicle_payload(String vehicle_payload) {
                this.vehicle_payload = vehicle_payload;
            }

            public String getVehicle_discription() {
                return vehicle_discription;
            }

            public void setVehicle_discription(String vehicle_discription) {
                this.vehicle_discription = vehicle_discription;
            }
        }
    }
}
