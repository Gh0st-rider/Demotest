package com.sudrives.sudrivespartner.models;

import java.util.List;

public class GetTypeModelData {


    /**
     * status : 1
     * error_code : 0
     * error_line : 219
     * message : Successfully
     * result : {"report_issue":[{"id":"16","name":"Demo","types":"report_issue"},{"id":"17","name":"Test","types":"report_issue"}],"vehicle_types":[{"id":"7","fare_charges":"0","vehicle_name":"32ft Container","vehicle_img":"http://52.33.111.26/api/media/vehicle/icon_4ton@3x.png","vehicle_size":"4*5*2","vehicle_payload":"","vehicle_discription":""},{"id":"8","fare_charges":"0","vehicle_name":"Canter 24ft","vehicle_img":"http://52.33.111.26/api/media/vehicle/icon_5ton@3x.png","vehicle_size":"4*5*2","vehicle_payload":"","vehicle_discription":""},{"id":"4","fare_charges":"0","vehicle_name":"Haulage Hire","vehicle_img":"http://52.33.111.26/api/media/vehicle/icon_4ton@3x.png","vehicle_size":"4*5*2","vehicle_payload":"","vehicle_discription":""},{"id":"6","fare_charges":"0","vehicle_name":"Pickup / 8ft","vehicle_img":"http://52.33.111.26/api/media/vehicle/icon_6ton@3x.png","vehicle_size":"4*5*2","vehicle_payload":"","vehicle_discription":""},{"id":"5","fare_charges":"0","vehicle_name":"Pickup 8ft Open","vehicle_img":"http://52.33.111.26/api/media/vehicle/icon_5ton@3x.png","vehicle_size":"4*5*2","vehicle_payload":"","vehicle_discription":""},{"id":"9","fare_charges":"0","vehicle_name":"Tata 19ft","vehicle_img":"http://52.33.111.26/api/media/vehicle/icon_6ton@3x.png","vehicle_size":"4*5*2","vehicle_payload":"","vehicle_discription":""},{"id":"3","fare_charges":"0","vehicle_name":"Tata 407","vehicle_img":"http://52.33.111.26/api/media/vehicle/icon_6ton@3x.png","vehicle_size":"4*5*2","vehicle_payload":"","vehicle_discription":""},{"id":"1","fare_charges":"0","vehicle_name":"Tata Ace","vehicle_img":"http://52.33.111.26/api/media/vehicle/icon_4ton@3x.png","vehicle_size":"4*5*2","vehicle_payload":"","vehicle_discription":""},{"id":"2","fare_charges":"0","vehicle_name":"Tata Ace Open","vehicle_img":"http://52.33.111.26/api/media/vehicle/icon_5ton@3x.png","vehicle_size":"4*5*2","vehicle_payload":"","vehicle_discription":""},{"id":"10","fare_charges":"0","vehicle_name":"Tata Zip","vehicle_img":"http://52.33.111.26/api/media/vehicle/icon_4ton@3x.png","vehicle_size":"4*5*2","vehicle_payload":"","vehicle_discription":""}]}
     */

    public int status;
    public int error_code;
    public int error_line;
    public String message;
    public ResultBean result;

    public static class ResultBean {
        public List<ReportIssueBean> report_issue;
        public List<VehicleTypesBean> vehicle_types;

        public static class ReportIssueBean {
            /**
             * id : 16
             * name : Demo
             * types : report_issue
             */

            public String id;
            public String name;
            public String types;

            @Override
            public String toString() {
                return name;
            }
        }

        public static class VehicleTypesBean {
            /**
             * id : 7
             * fare_charges : 0
             * vehicle_name : 32ft Container
             * vehicle_img : http://52.33.111.26/api/media/vehicle/icon_4ton@3x.png
             * vehicle_size : 4*5*2
             * vehicle_payload :
             * vehicle_discription :
             */

            public String id;
            public String fare_charges;
            public String vehicle_name;
            public String vehicle_img;
            public String vehicle_size;
            public String vehicle_payload;
            public String vehicle_discription;
        }
    }
}
