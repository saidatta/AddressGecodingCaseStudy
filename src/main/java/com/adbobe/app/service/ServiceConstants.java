package com.adbobe.app.service;

/**
 * Created by venkatamunnangi on 10/5/16.
 */
public class ServiceConstants {
        public static final String GOOGLE_CLOUD_APP_NAME = "adobecasestudy";

        public static final String GEO_API_KEY = "AIzaSyDOPmdK4SiR1JyxxL-bc9FyBNQLadYWVow";

        public static final String GEO_CODING_URL_SECURE = "https://maps.googleapis.com/maps/api/geocode/json?key="+GEO_API_KEY+"&sensor=false&address=";

        public static final String GEO_CODING_URL = "http://maps.googleapis.com/maps/api/geocode/json?sensor=false&address=";

        public static final String CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

        public static final String BUCKET_NAME = "adobecasestudy1";

        public static final String STORAGE_SCOPE = "https://www.googleapis.com/auth/devstorage.read_write";

        public static final String P12FILE = "./AdobeCaseStudy-6f1c68eac6aa.p12";

        public static final String CLIENT_ID = "1011086681758-dpv7fk7jva7fd7r0vl972e3tevtrvos4.apps.googleusercontent.com";

        public static final String CLIENT_SECRET = "EZST1J_ySOSn4NOso9ZiDXSx";

        public static final String GEO_SERVICE_OVER_QUERY_LIMIT_STATUS = "OVER_QUERY_LIMIT";

        public static final String EXCEL_EXTENSION = "xlsx";

        public static final String HTTP_STATUS_OK = "OK";

        public static final String NON_EXISTENT_ADDRESS_STATUS = "ZERO_RESULTS";

        public static final String NON_EXISTENT_ADDRESS = "Non Existing Address";

        public static final long GEOCODING_REQUEST_PAUSE_TIME = 2000;

        public static final String CLOUD_STORAGE_PATH = "https://console.cloud.google.com/m/cloudstorage/b/adobecasestudy1/o/";
}
