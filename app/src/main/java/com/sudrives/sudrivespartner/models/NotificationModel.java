package com.sudrives.sudrivespartner.models;

import java.util.List;

public class NotificationModel {


    /**
     * status : 1
     * error_code : 0
     * error_line : 1006
     * message : Rating given successfully
     * total_records : 7
     * per_page : 5
     * notification_unread : 30
     * result : [{"id":"1","user_id":"15","title":"Your Booking is Successfull 1","message":"Lorem Ipsum is simply dummy text of the printing and typesetting industry. ","read_status":"0","create_dt":"1540893051"}]
     */

    private int status;
    private int error_code;
    private int error_line;
    private String message;
    private int total_records;
    private int per_page;
    private int notification_unread;
    private List<ResultBean> result;

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

    public int getTotal_records() {
        return total_records;
    }

    public void setTotal_records(int total_records) {
        this.total_records = total_records;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getNotification_unread() {
        return notification_unread;
    }

    public void setNotification_unread(int notification_unread) {
        this.notification_unread = notification_unread;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 1
         * user_id : 15
         * title : Your Booking is Successfull 1
         * message : Lorem Ipsum is simply dummy text of the printing and typesetting industry.
         * read_status : 0
         * create_dt : 1540893051
         */

        private String id;
        private String user_id;
        private String title;
        private String message;
        private String read_status;
        private String create_dt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getRead_status() {
            return read_status;
        }

        public void setRead_status(String read_status) {
            this.read_status = read_status;
        }

        public String getCreate_dt() {
            return create_dt;
        }

        public void setCreate_dt(String create_dt) {
            this.create_dt = create_dt;
        }
    }
}
