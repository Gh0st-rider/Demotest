package com.sudrives.sudrivespartner.models;

import java.util.List;

public class RegistrationModel {


    /**
     * status : 1
     * error_code : 0
     * error_line : 201
     * message : Successfully
     * result : {"cancel_order":[{"id":"13","name":"Booked Wrong Vehicle","types":"cancel_order"},{"id":"10","name":"Change My Mind","types":"cancel_order"},{"id":"14","name":"Driver Asked Me To Cancel","types":"cancel_order"},{"id":"12","name":"Driver Is Going Late","types":"cancel_order"},{"id":"11","name":"Driver Is Unprofessional","types":"cancel_order"},{"id":"15","name":"My Reason Is Not Listed","types":"cancel_order"},{"id":"9","name":"Unable To Contact Driver","types":"cancel_order"}],"decline_request":[{"id":"3","name":"Capacity Is In High Volume","types":"decline_request"},{"id":"7","name":"Customer Asked Me To Cancel","types":"decline_request"},{"id":"4","name":"Customer Is Going Late","types":"decline_request"},{"id":"8","name":"My Reason Is Not Listed","types":"decline_request"},{"id":"6","name":"Not Interested","types":"decline_request"},{"id":"1","name":"Unable To Contact Customer","types":"decline_request"},{"id":"2","name":"Unable To Go Route","types":"decline_request"}],"report_issue":[{"id":"16","name":"Demo","types":"report_issue"},{"id":"17","name":"Test","types":"report_issue"}],"about_us":[{"id":"21","name":"FM","types":"about_us"},{"id":"20","name":"News Paper","types":"about_us"},{"id":"19","name":"Tv","types":"about_us"}],"vehicle_types":[{"id":"7","fare_charges":"0","vehicle_name":"32ft Container","vehicle_img":"http://haulage.com.move_cabs/media/vehicle/default.svg","vehicle_size":"","vehicle_payload":"","vehicle_discription":""},{"id":"8","fare_charges":"0","vehicle_name":"Canter 24ft","vehicle_img":"http://haulage.com.move_cabs/media/vehicle/default.svg","vehicle_size":"","vehicle_payload":"","vehicle_discription":""},{"id":"4","fare_charges":"0","vehicle_name":"Haulage Hire","vehicle_img":"http://haulage.com.move_cabs/media/vehicle/default.svg","vehicle_size":"","vehicle_payload":"","vehicle_discription":""},{"id":"6","fare_charges":"0","vehicle_name":"Pickup / 8ft","vehicle_img":"http://haulage.com.move_cabs/media/vehicle/default.svg","vehicle_size":"","vehicle_payload":"","vehicle_discription":""},{"id":"5","fare_charges":"0","vehicle_name":"Pickup 8ft Open","vehicle_img":"http://haulage.com.move_cabs/media/vehicle/default.svg","vehicle_size":"","vehicle_payload":"","vehicle_discription":""},{"id":"9","fare_charges":"0","vehicle_name":"Tata 19ft","vehicle_img":"http://haulage.com.move_cabs/media/vehicle/default.svg","vehicle_size":"","vehicle_payload":"","vehicle_discription":""},{"id":"3","fare_charges":"0","vehicle_name":"Tata 407","vehicle_img":"http://haulage.com.move_cabs/media/vehicle/default.svg","vehicle_size":"","vehicle_payload":"","vehicle_discription":""},{"id":"1","fare_charges":"0","vehicle_name":"Tata Ace","vehicle_img":"http://haulage.com.move_cabs/media/vehicle/default.svg","vehicle_size":"","vehicle_payload":"","vehicle_discription":""},{"id":"2","fare_charges":"0","vehicle_name":"Tata Ace Open","vehicle_img":"http://haulage.com.move_cabs/media/vehicle/default.svg","vehicle_size":"","vehicle_payload":"","vehicle_discription":""},{"id":"10","fare_charges":"0","vehicle_name":"Tata Zip","vehicle_img":"http://haulage.com.move_cabs/media/vehicle/default.svg","vehicle_size":"","vehicle_payload":"","vehicle_discription":""}]}
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
         * id : 13
         * name : Booked Wrong Vehicle
         * types : cancel_order
         */

        private List<CancelOrderBean> cancel_order;
        /**
         * id : 3
         * name : Capacity Is In High Volume
         * types : decline_request
         */

        private List<DeclineRequestBean> decline_request;
        /**
         * id : 16
         * name : Demo
         * types : report_issue
         */

        private List<ReportIssueBean> report_issue;
        /**
         * id : 21
         * name : FM
         * types : about_us
         */

        private List<AboutUsBean> about_us;
        /**
         * id : 7
         * fare_charges : 0
         * vehicle_name : 32ft Container
         * vehicle_size :
         * vehicle_payload :
         * vehicle_discription :
         */

        private List<VehicleTypesBean> vehicle_types;

        public List<CancelOrderBean> getCancel_order() {
            return cancel_order;
        }

        public void setCancel_order(List<CancelOrderBean> cancel_order) {
            this.cancel_order = cancel_order;
        }

        public List<DeclineRequestBean> getDecline_request() {
            return decline_request;
        }

        public void setDecline_request(List<DeclineRequestBean> decline_request) {
            this.decline_request = decline_request;
        }

        public List<ReportIssueBean> getReport_issue() {
            return report_issue;
        }

        public void setReport_issue(List<ReportIssueBean> report_issue) {
            this.report_issue = report_issue;
        }

        public List<AboutUsBean> getAbout_us() {
            return about_us;
        }

        public void setAbout_us(List<AboutUsBean> about_us) {
            this.about_us = about_us;
        }

        public List<VehicleTypesBean> getVehicle_types() {
            return vehicle_types;
        }

        public void setVehicle_types(List<VehicleTypesBean> vehicle_types) {
            this.vehicle_types = vehicle_types;
        }

        public static class CancelOrderBean {
            private String id;
            private String name;
            private String types;

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

        public static class DeclineRequestBean {
            private String id;
            private String name;
            private String types;

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

        public static class ReportIssueBean {
            private String id;
            private String name;
            private String types;

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

        public static class AboutUsBean {
            private String id;
            private String name;
            private String types;

            public AboutUsBean(String id, String name, String types) {
                this.id = id;
                this.name = name;
                this.types = types;
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

            public VehicleTypesBean(String id, String fare_charges, String vehicle_name, String vehicle_img, String vehicle_size, String vehicle_payload, String vehicle_discription) {
                this.id = id;
                this.fare_charges = fare_charges;
                this.vehicle_name = vehicle_name;
                this.vehicle_img = vehicle_img;
                this.vehicle_size = vehicle_size;
                this.vehicle_payload = vehicle_payload;
                this.vehicle_discription = vehicle_discription;
            }

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
